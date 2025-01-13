package com.matthewperiut.accessoryapi.config;

import net.minecraft.client.resource.language.TranslationStorage;

public enum Style {
    AETHER("style.accessoryapi:aether"),
    BAUBLES("style.accessoryapi:baubles"),
    TRINKETS("style.accessoryapi:trinkets");

    final String translationKey;

    Style(String translationKey) {
        this.translationKey = translationKey;
    }

    public String getTranslationKey() {
        return translationKey;
    }

    public String getTranslatedName() {
        return TranslationStorage.getInstance().get(translationKey);
    }

    public Style nextStyle() {
        if (ordinal() >= values().length) {
            return values()[0];
        } else {
            return values()[ordinal() + 1];
        }
    }
}
