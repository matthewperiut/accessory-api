package com.matthewperiut.accessoryapi.config;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.glasslauncher.mods.api.gcapi.api.CharacterUtils;
import net.glasslauncher.mods.api.gcapi.api.HasDrawable;
import net.glasslauncher.mods.api.gcapi.api.MaxLength;
import net.glasslauncher.mods.api.gcapi.impl.config.ConfigEntry;
import net.glasslauncher.mods.api.gcapi.screen.widget.FancyButton;
import net.glasslauncher.mods.api.gcapi.screen.widget.Icon;
import net.minecraft.client.gui.screen.ScreenBase;
import net.minecraft.client.render.TextRenderer;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class StyleConfigEntry extends ConfigEntry<Style> {
    private FancyButton button;
    private List<HasDrawable> drawableList;

    public StyleConfigEntry(String id, String name, String description, Field parentField, Object parentObject, boolean multiplayerSynced, Style value, MaxLength maxLength) {
        super(id, name, description, parentField, parentObject, multiplayerSynced, value, maxLength);
    }

    @Override
    public void init(ScreenBase screenBase, TextRenderer textRenderer) {
        button = new FancyButton(10, 0, 0, 0, 0, this.value.toString(), CharacterUtils.getIntFromColour(new Color(255, 202, 0, 255)));
        drawableList = new ArrayList<>() {
            {
                add(button);
            }
        };
        if (multiplayerSynced) {
            drawableList.add(new Icon(10, 0, 0, 0, "/assets/gcapi/server_synced.png"));
        }

        button.active = !multiplayerLoaded;
    }

    public Style getDrawableValue() {
        return value;
    }

    public void setDrawableValue(Style value) {
        this.value = value;
        button.text = value.name();
    }

    public boolean isValueValid() {
        return true;
    }

    public @NotNull List<HasDrawable> getDrawables() {
        return drawableList;
    }

    @Environment(EnvType.CLIENT)
    public void onClick() {
        System.out.println("HIII");
        value = value.nextStyle();
        button.text = value.name();
    }
}
