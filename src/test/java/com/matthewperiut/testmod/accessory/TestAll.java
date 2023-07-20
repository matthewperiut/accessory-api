package com.matthewperiut.testmod.accessory;

import com.matthewperiut.accessoryapi.api.Accessory;
import com.matthewperiut.accessoryapi.api.AccessoryType;
import net.minecraft.item.ItemInstance;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.item.TemplateItemBase;

public class TestAll extends TemplateItemBase implements Accessory
{
    public TestAll(Identifier identifier)
    {
        super(identifier);
        setMaxStackSize(1);
        setDurability(100);
    }

    @Override
    public AccessoryType[] getAccessoryTypes(ItemInstance item)
    {
        return new AccessoryType[]{AccessoryType.pendant, AccessoryType.cape, AccessoryType.shield, AccessoryType.glove, AccessoryType.ring, AccessoryType.misc};
    }

    @Override
    public String[] getCustomAccessoryTypes(ItemInstance item)
    {
        return new String[]{"blob", "extra", "blank"};
    }
}
