#!/usr/bin/env python3
"""
CI helper: push a freshly-published mod version into the matthewperiut.github.io pages repo.

Generic version of RetroAPI's update_pages.py - works for any mod. Given the pages repo
checked out at --pages and the mod's resolved coordinates, this:

  1. locates the maven artifact: preferred source is ~/.m2/repository/<group>/<artifact>/<version>
     (written by `gradlew publishToMavenLocal`); if the project has no maven-publish plugin it
     falls back to build/libs/<artifact>-<version>{.jar,-sources.jar} plus a generated minimal pom,
  2. copies jar / sources jar / pom / module metadata into the pages maven repo,
  3. rewrites repository/<group>/<artifact>/maven-metadata.xml (latest/release = newest version),
  4. updates the (group, artifact, branch) entry in manifest.json (version / path / commit),
     appending a new entry when none matches,
  5. regenerates repository-tree.json from the repository tree,
  6. optionally (--docs-property NAME, repeatable) bumps every `NAME=<version>` mention in the
     docs (retroapi/*.html) and inside the two template zips to the new version - the "fancy
     docs rewrite", used e.g. by starac for starac_version.

The artifact id is auto-detected: the directory under ~/.m2/repository/<group>/ that contains a
<version> subdirectory (preferring --artifact-hint when several match), falling back to the hint.

Non-interactive and idempotent: if the version's jar is already in the pages repo it exits
without touching anything, so re-running for the same version produces no commit.
"""
import argparse
import io
import json
import os
import re
import shutil
import zipfile
from datetime import datetime, timezone
from pathlib import Path

M2 = Path.home() / ".m2" / "repository"
TEMPLATE_ZIPS = ("bare-retroapi-template.zip", "feature-showcase-retroapi-template.zip")
DOCS_DIR = "retroapi"


def detect_artifact(group_path: Path, version: str, hint: str) -> str:
    """Find the artifactId dir under the group's m2 path that holds this version."""
    if not group_path.is_dir():
        return hint
    candidates = [p.name for p in group_path.iterdir() if (p / version).is_dir()]
    if hint in candidates or not candidates:
        return hint
    if len(candidates) > 1:
        print(f"warning: multiple artifacts published for {version}: {candidates}; using {candidates[0]}")
    return candidates[0]


MINIMAL_POM = """<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0">
  <modelVersion>4.0.0</modelVersion>
  <groupId>{group}</groupId>
  <artifactId>{artifact}</artifactId>
  <version>{version}</version>
</project>
"""


def copy_artifact(pages: Path, group: str, artifact: str, version: str) -> None:
    dst = pages / "repository" / group.replace(".", "/") / artifact / version
    src = M2 / group.replace(".", "/") / artifact / version
    dst.mkdir(parents=True, exist_ok=True)
    if src.is_dir():
        # Maven-local artifacts: main jar, sources jar, pom, gradle module metadata. The -babric
        # jar is a GitHub/Modrinth artifact, not part of the Gradle-consumable maven repo; -dev
        # and -childshim jars are build-internal. All deliberately excluded.
        for f in src.iterdir():
            if f.name.startswith("maven-metadata"):
                continue
            if any(tag in f.name for tag in ("-babric", "-dev", "-childshim")):
                continue
            if f.suffix in (".jar", ".pom", ".module") or f.name.endswith("-sources.jar"):
                shutil.copy2(f, dst / f.name)
        print(f"copied {artifact} {version} artifacts from maven local -> {dst}")
        return
    # Fallback for projects without maven-publish: jars straight from build/libs + a minimal pom.
    libs = Path("build/libs")
    main = libs / f"{artifact}-{version}.jar"
    if not main.is_file():
        raise SystemExit(f"artifact not found in maven local ({src}) nor build/libs ({main})")
    shutil.copy2(main, dst / main.name)
    sources = libs / f"{artifact}-{version}-sources.jar"
    if sources.is_file():
        shutil.copy2(sources, dst / sources.name)
    (dst / f"{artifact}-{version}.pom").write_text(
        MINIMAL_POM.format(group=group, artifact=artifact, version=version))
    print(f"copied {artifact} {version} from build/libs (generated minimal pom) -> {dst}")


def write_maven_metadata(pages: Path, group: str, artifact: str, version: str) -> None:
    base = pages / "repository" / group.replace(".", "/") / artifact
    versions = sorted(
        (p.name for p in base.iterdir() if p.is_dir()),
        key=lambda v: [int(x) if x.isdigit() else x for x in re.split(r"[.+_-]", v)],
    )
    if version not in versions:
        versions.append(version)
    stamp = datetime.now(timezone.utc).strftime("%Y%m%d%H%M%S")
    ver_xml = "\n".join(f"      <version>{v}</version>" for v in versions)
    xml = (
        '<?xml version="1.0" encoding="UTF-8"?>\n'
        "<metadata>\n"
        f"  <groupId>{group}</groupId>\n"
        f"  <artifactId>{artifact}</artifactId>\n"
        "  <versioning>\n"
        f"    <latest>{version}</latest>\n"
        f"    <release>{version}</release>\n"
        "    <versions>\n"
        f"{ver_xml}\n"
        "    </versions>\n"
        f"    <lastUpdated>{stamp}</lastUpdated>\n"
        "  </versioning>\n"
        "</metadata>\n"
    )
    (base / "maven-metadata.xml").write_text(xml)
    print(f"wrote maven-metadata.xml (latest={version}, {len(versions)} versions)")


def update_manifest(pages: Path, group: str, artifact: str, version: str,
                    commit: str, branch: str, repo: str) -> None:
    short = commit[:7]  # match the existing manifest's short-hash convention
    jar_path = f"repository/{group.replace('.', '/')}/{artifact}/{version}/{artifact}-{version}.jar"
    path = pages / "manifest.json"
    data = json.loads(path.read_text())
    found = False
    # Entries are keyed by (group, artifact, branch): the manifest deliberately keeps one
    # entry per published branch of the same mod (see accessory-api / starac).
    for jar in data.get("jars", []):
        if jar.get("group") == group and jar.get("artifact") == artifact and jar.get("branch") == branch:
            jar["version"] = version
            jar["path"] = jar_path
            jar["commit"] = short
            found = True
            break
    if not found:
        data.setdefault("jars", []).append({
            "group": group, "artifact": artifact, "version": version,
            "path": jar_path, "repo": repo, "branch": branch, "commit": short,
        })
    path.write_text(json.dumps(data, indent=2))
    print(f"updated manifest.json {artifact} -> {version} @ {short} ({branch})")


def build_tree(path: Path) -> dict:
    tree = {"children": {}}
    for entry in sorted(os.listdir(path)):
        full = path / entry
        tree["children"][entry] = build_tree(full) if full.is_dir() else {}
    return tree


def regenerate_tree(pages: Path) -> None:
    repo = pages / "repository"
    if repo.is_dir():
        (pages / "repository-tree.json").write_text(json.dumps(build_tree(repo)))
        print("regenerated repository-tree.json")


def bump_text(text: str, props: list, version: str) -> str:
    # Each property is bumped wherever it appears as `<prop> = <value>` (gradle.properties
    # style, also matching the docs' copies of those snippets).
    for prop in props:
        pattern = re.compile(r'(' + re.escape(prop) + r'\s*=\s*)[0-9A-Za-z.+_-]+')
        text = pattern.sub(lambda m: m.group(1) + version, text)
    return text


def bump_docs(pages: Path, props: list, version: str) -> None:
    for html in (pages / DOCS_DIR).glob("*.html"):
        text = html.read_text()
        new = bump_text(text, props, version)
        if new != text:
            html.write_text(new)
            print(f"bumped {html.relative_to(pages)}")


def bump_template_zip(zip_path: Path, props: list, version: str) -> None:
    if not zip_path.is_file():
        print(f"template zip missing, skipping: {zip_path}")
        return
    with zipfile.ZipFile(zip_path) as zin:
        items = [(i, zin.read(i.filename)) for i in zin.infolist()]
    changed = False
    buf = io.BytesIO()
    with zipfile.ZipFile(buf, "w", zipfile.ZIP_DEFLATED) as zout:
        for info, data in items:
            if info.filename.endswith(("gradle.properties", "fabric.mod.json")):
                new = bump_text(data.decode("utf-8"), props, version).encode("utf-8")
                if new != data:
                    changed = True
                    data = new
            zout.writestr(info, data)
    if changed:
        zip_path.write_bytes(buf.getvalue())
        print(f"bumped {zip_path.name}")
    else:
        print(f"no version refs changed in {zip_path.name}")


def main() -> None:
    ap = argparse.ArgumentParser()
    ap.add_argument("--pages", required=True, type=Path, help="path to the checked-out pages repo")
    ap.add_argument("--group", required=True, help="maven group (resolved gradle `group`)")
    ap.add_argument("--artifact-hint", required=True, help="likely artifactId (archives_base_name)")
    ap.add_argument("--version", required=True, help="new version (resolved gradle `version`)")
    ap.add_argument("--commit", required=True, help="source commit sha")
    ap.add_argument("--branch", required=True, help="source branch")
    ap.add_argument("--repo", required=True, help="owner/name of the source GitHub repo")
    ap.add_argument("--docs-property", action="append", default=[],
                    help="gradle.properties key to bump to the new version across the docs "
                         "and template zips (repeatable); omit for no docs rewriting")
    args = ap.parse_args()

    pages = args.pages
    group_path = M2 / args.group.replace(".", "/")
    artifact = detect_artifact(group_path, args.version, args.artifact_hint)

    # Idempotency guard: a prior run may have pushed pages and then failed later (e.g. on a
    # Modrinth duplicate-version rejection). maven-metadata.xml gets a fresh <lastUpdated>
    # every run, so without this guard a re-run would always produce a redundant commit.
    published_jar = (pages / "repository" / args.group.replace(".", "/")
                     / artifact / args.version / f"{artifact}-{args.version}.jar")
    if published_jar.is_file():
        print(f"{artifact} {args.version} already in the pages maven repo; nothing to do")
        return

    copy_artifact(pages, args.group, artifact, args.version)
    write_maven_metadata(pages, args.group, artifact, args.version)
    update_manifest(pages, args.group, artifact, args.version, args.commit, args.branch, args.repo)
    regenerate_tree(pages)
    if args.docs_property:
        bump_docs(pages, args.docs_property, args.version)
        for name in TEMPLATE_ZIPS:
            bump_template_zip(pages / DOCS_DIR / name, args.docs_property, args.version)


if __name__ == "__main__":
    main()
