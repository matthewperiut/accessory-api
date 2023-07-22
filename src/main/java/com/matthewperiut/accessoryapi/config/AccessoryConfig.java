package com.matthewperiut.accessoryapi.config;

import blue.endless.jankson.Comment;
import net.glasslauncher.mods.api.gcapi.api.ConfigName;
import net.glasslauncher.mods.api.gcapi.api.GConfig;
import net.glasslauncher.mods.api.gcapi.api.MultiplayerSynced;

public class AccessoryConfig {
    @GConfig(value = "config", visibleName = "Accessory API Config")
    public static ConfigFields config = new ConfigFields();

    public static class ConfigFields
    {
        @ConfigName("Aether-Style Armor Slots")
        @Comment("Essentially whether you want armor slots to the right of the player doll.")
        public Boolean aetherStyleArmor = true;

        @MultiplayerSynced
        @ConfigName("Armor offset")
        @Comment("If you don't know what this does, don't touch it!")
        public Integer armorOffset = 4;
    }
}
