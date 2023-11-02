package com.matthewperiut.testmod;

import com.matthewperiut.accessoryapi.api.AccessoryRegister;
import net.fabricmc.api.ModInitializer;

public class TestMod implements ModInitializer {
    @Override
    public void onInitialize() {
        AccessoryRegister.add("pendant");
        AccessoryRegister.add("cape");
        AccessoryRegister.add("shield");
        AccessoryRegister.add("misc", 0, 3);
        AccessoryRegister.add("misc", 1, 3);
        AccessoryRegister.add("ring");
        AccessoryRegister.add("ring");
        AccessoryRegister.add("gloves");

        for (int i = 0; i < 4; i++)
            AccessoryRegister.add("slime", "assets/testmod/textures/slot/extra.png", 0, 0);

        for (int i = 0; i < 8; i++)
            AccessoryRegister.add("nope");
    }
}
