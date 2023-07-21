package com.matthewperiut.testmod.accessory;

import com.matthewperiut.accessoryapi.api.Accessory;
import net.minecraft.item.ItemInstance;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.item.TemplateItemBase;

public class TestAccessory extends TemplateItemBase implements Accessory
{
    private final String[] types;
    public TestAccessory(Identifier identifier, String... types)
    {
        super(identifier);
        setTranslationKey(identifier);
        setMaxStackSize(1);
        setDurability(100);
        this.types = types;
    }

    @Override
    public String[] getAccessoryTypes(ItemInstance item) {
        return types;
    }
}
