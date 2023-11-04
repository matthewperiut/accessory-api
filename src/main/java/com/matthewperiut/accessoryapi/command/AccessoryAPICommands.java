package com.matthewperiut.accessoryapi.command;

import com.matthewperiut.spc.api.CommandRegistry;
import net.fabricmc.loader.api.FabricLoader;

public class AccessoryAPICommands {
    public static void addCommands() {
        if (FabricLoader.getInstance().isModLoaded("spc"))
        {
            CommandRegistry.add(new HeartsCommand());
            CommandRegistry.add(new InvisibleCommand());
            CommandRegistry.add(new BossCommand());
        }
    }
}
