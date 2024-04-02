package com.matthewperiut.accessoryapi.command;

import com.matthewperiut.spc.api.CommandRegistry;

public class AccessoryAPICommands {
    public static void addCommands() {
        CommandRegistry.add(new HeartsCommand());
        CommandRegistry.add(new InvisibleCommand());
        CommandRegistry.add(new BossCommand());
    }
}
