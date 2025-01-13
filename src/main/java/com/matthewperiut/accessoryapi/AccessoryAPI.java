package com.matthewperiut.accessoryapi;

import com.matthewperiut.accessoryapi.command.AccessoryAPICommands;
import com.matthewperiut.accessoryapi.config.AccessoryAPIConfigFields;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.glasslauncher.mods.gcapi3.api.ConfigRoot;

public class AccessoryAPI implements ModInitializer {
    public static String MOD_ID = "accessoryapi";

    @ConfigRoot(value = "config", visibleName = "Accessory API Config")
    public static AccessoryAPIConfigFields config = new AccessoryAPIConfigFields();
    public static boolean noSlotsAdded = false;

    @Override
    public void onInitialize() {
        if (FabricLoader.getInstance().isModLoaded("retrocommands")) {
            AccessoryAPICommands.addCommands();
        }
    }
}
