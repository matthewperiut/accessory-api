package com.matthewperiut.testmod;

import com.matthewperiut.accessoryapi.api.CustomAccessoryRegister;
import net.fabricmc.api.ModInitializer;

public class TestMod implements ModInitializer
{
    @Override
    public void onInitialize()
    {


        for (int i = 0; i < 3; i++)
        {
            CustomAccessoryRegister.add("extra" + i, "extra", "assets/testmod/textures/slot/extra.png", 16, 0);
        }
        CustomAccessoryRegister.add("blob", "cape", "assets/testmod/textures/slot/extra.png", 0, 0);

        for (int i = 0; i < 2; i++)
        {
            CustomAccessoryRegister.add("blank" + i, "blank");
        }
    }
}
