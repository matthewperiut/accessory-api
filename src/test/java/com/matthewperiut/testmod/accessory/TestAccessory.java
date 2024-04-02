package com.matthewperiut.testmod.accessory;

import com.matthewperiut.accessoryapi.api.Accessory;
import net.minecraft.item.ItemInstance;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class TestAccessory extends TemplateItem implements Accessory {
    protected final String[] types;

    public TestAccessory(Identifier identifier, String... types) {
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
