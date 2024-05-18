package com.matthewperiut.accessoryapi.config;

import blue.endless.jankson.Comment;
import net.fabricmc.loader.api.FabricLoader;
import net.glasslauncher.mods.api.gcapi.api.ConfigName;
import net.glasslauncher.mods.api.gcapi.api.MultiplayerSynced;

public class AccessoryAPIConfigFields {
    @ConfigName("Aether-Style Armor Slots")
    @Comment("Essentially whether you want armor slots to the right of the player doll.")
    public Boolean aetherStyleArmor = false;

    @MultiplayerSynced
    @ConfigName("Armor offset")
    @Comment("If you don't know what this does, don't touch it!")
    public Integer armorOffset = 4;
}
