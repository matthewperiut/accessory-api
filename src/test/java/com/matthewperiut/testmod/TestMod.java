package com.matthewperiut.testmod;

import com.matthewperiut.accessoryapi.api.AccessoryRegister;
import net.fabricmc.api.ModInitializer;

public class TestMod implements ModInitializer {
    @Override
    public void onInitialize() {
        AccessoryRegister.requestSlot("pendant", 1);
        AccessoryRegister.requestSlot("cape", 1);
        AccessoryRegister.requestSlot("shield", 1);
        AccessoryRegister.add("misc", 0, 3);
        AccessoryRegister.add("misc", 1, 3);
        AccessoryRegister.requestSlot("ring", 2);
        AccessoryRegister.requestSlot("gloves", 1);

        for (int i = 0; i < 4; i++)
            AccessoryRegister.add("slime", "assets/testmod/textures/slot/extra.png", 0, 0);

        AccessoryRegister.requestSlot("none", 8);
    }
}
