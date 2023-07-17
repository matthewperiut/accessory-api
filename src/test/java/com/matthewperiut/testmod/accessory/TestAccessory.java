package com.matthewperiut.testmod.accessory;

import com.matthewperiut.accessoryapi.api.normal.Accessory;
import com.matthewperiut.accessoryapi.api.normal.AccessoryType;
import net.minecraft.item.ItemInstance;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.item.TemplateItemBase;

public class TestAccessory extends TemplateItemBase implements Accessory
{
    public TestAccessory(Identifier identifier)
    {
        super(identifier);
        setMaxStackSize(1);
        setDurability(100);
    }

    @Override
    public AccessoryType[] getAccessoryTypes(ItemInstance item)
    {
        return new AccessoryType[]{AccessoryType.none};
    }
}
