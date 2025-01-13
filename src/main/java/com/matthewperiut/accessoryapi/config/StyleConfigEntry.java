//package com.matthewperiut.accessoryapi.config;
//
//import net.fabricmc.api.EnvType;
//import net.fabricmc.api.Environment;
//import net.glasslauncher.mods.api.gcapi.api.CharacterUtils;
//import net.glasslauncher.mods.api.gcapi.api.HasDrawable;
//import net.glasslauncher.mods.api.gcapi.api.MaxLength;
//import net.glasslauncher.mods.api.gcapi.impl.config.ConfigEntry;
//import net.glasslauncher.mods.api.gcapi.screen.widget.FancyButtonWidget;
//import net.glasslauncher.mods.api.gcapi.screen.widget.IconWidget;
//import net.minecraft.client.font.TextRenderer;
//import net.minecraft.client.gui.screen.Screen;
//import org.jetbrains.annotations.NotNull;
//
//import java.awt.*;
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.List;
//
//public class StyleConfigEntry extends ConfigEntry<Style> {
//    private FancyButtonWidget button;
//    private List<HasDrawable> drawableList;
//
//    public StyleConfigEntry(String id, String name, String description, Field parentField, Object parentObject, boolean multiplayerSynced, Style value, MaxLength maxLength) {
//        super(id, name, description, parentField, parentObject, multiplayerSynced, value, maxLength);
//    }
//
//    @Override
//    public void init(Screen screenBase, TextRenderer textRenderer) {
//        button = new FancyButtonWidget(10, 0, 0, 0, 0, this.value.toString(), CharacterUtils.getIntFromColour(new Color(255, 202, 0, 255)));
//        drawableList = new ArrayList<>() {
//            {
//                add(button);
//            }
//        };
//        if (multiplayerSynced) {
//            drawableList.add(new IconWidget(10, 0, 0, 0, "/assets/gcapi/server_synced.png"));
//        }
//
//        button.active = !multiplayerLoaded;
//    }
//
//    public Style getDrawableValue() {
//        return value;
//    }
//
//    public void setDrawableValue(Style value) {
//        this.value = value;
//        button.text = value.name();
//    }
//
//    public boolean isValueValid() {
//        return true;
//    }
//
//    public @NotNull List<HasDrawable> getDrawables() {
//        return drawableList;
//    }
//
//    @Environment(EnvType.CLIENT)
//    public void onClick() {
//        value = value.nextStyle();
//        button.text = value.name();
//    }
//}
