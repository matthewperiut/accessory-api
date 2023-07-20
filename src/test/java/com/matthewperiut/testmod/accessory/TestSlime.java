package com.matthewperiut.testmod.accessory;

import com.matthewperiut.accessoryapi.api.AccessoryType;
import net.minecraft.item.ItemInstance;
import net.modificationstation.stationapi.api.registry.Identifier;

public class TestSlime extends TestGloves
{
    public TestSlime(Identifier identifier)
    {
        super(identifier);
    }

    @Override
    public AccessoryType[] getAccessoryTypes(ItemInstance item)
    {
        return new AccessoryType[0];
    }

    @Override
    public String[] getCustomAccessoryTypes(ItemInstance item)
    {
        return new String[]{"blob"};
    }
}
