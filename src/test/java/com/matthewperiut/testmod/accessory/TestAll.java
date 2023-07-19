package com.matthewperiut.testmod.accessory;

import com.matthewperiut.accessoryapi.api.normal.AccessoryType;
import net.minecraft.item.ItemInstance;
import net.modificationstation.stationapi.api.registry.Identifier;

public class TestAll extends TestAccessory
{
    public TestAll(Identifier identifier)
    {
        super(identifier);
    }

    @Override
    public AccessoryType[] getAccessoryTypes(ItemInstance item)
    {
        return new AccessoryType[]{ AccessoryType.pendant, AccessoryType.cape, AccessoryType.shield, AccessoryType.glove, AccessoryType.ring, AccessoryType.misc };
    }
}
