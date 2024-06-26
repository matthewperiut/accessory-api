package com.matthewperiut.accessoryapi;

import com.matthewperiut.accessoryapi.command.AccessoryAPICommands;
import com.matthewperiut.accessoryapi.config.AccessoryAPIConfigFields;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.glasslauncher.mods.api.gcapi.api.GConfig;

public class AccessoryAPI implements ModInitializer {
    public static String MOD_ID = "accessoryapi";

    @GConfig(value = "config", visibleName = "Accessory API Config")
    public static AccessoryAPIConfigFields config = new AccessoryAPIConfigFields();
    public static boolean noSlotsAdded = false;

    @Override
    public void onInitialize() {
        if (FabricLoader.getInstance().isModLoaded("spc")) {
            AccessoryAPICommands.addCommands();
        }
    }
}
