package com.matthewperiut.testmod.accessory;

import com.matthewperiut.accessoryapi.api.normal.AccessoryType;
import net.minecraft.item.ItemInstance;
import net.modificationstation.stationapi.api.registry.Identifier;

public class TestRing extends TestAccessory
{
    public TestRing(Identifier identifier)
    {
        super(identifier);
    }

    @Override
    public AccessoryType[] getAccessoryTypes(ItemInstance item)
    {
        return new AccessoryType[]{AccessoryType.ring};
    }
}
