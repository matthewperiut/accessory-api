package com.matthewperiut.testmod.accessory;

import com.matthewperiut.accessoryapi.api.AccessoryType;
import net.minecraft.item.ItemInstance;
import net.modificationstation.stationapi.api.registry.Identifier;

public class TestShield extends TestAccessory
{
    public TestShield(Identifier identifier)
    {
        super(identifier);
    }

    @Override
    public AccessoryType[] getAccessoryTypes(ItemInstance item)
    {
        return new AccessoryType[]{AccessoryType.shield};
    }

}
