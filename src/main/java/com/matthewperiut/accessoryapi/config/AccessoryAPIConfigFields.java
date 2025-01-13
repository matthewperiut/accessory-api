package com.matthewperiut.accessoryapi.config;

import net.glasslauncher.mods.gcapi3.api.ConfigEntry;

public class AccessoryAPIConfigFields {
    @ConfigEntry(
            name = "Aether-Style Armor Slots",
            description = "Essentially whether you want armor slots to the right of the player doll."
    )
    public Boolean aetherStyleArmor = true;

    @ConfigEntry(
            name = "Armor offset",
            description = "If you don't know what this does, don't touch it!",
            multiplayerSynced = true
    )
    public Integer armorOffset = 4;
}
