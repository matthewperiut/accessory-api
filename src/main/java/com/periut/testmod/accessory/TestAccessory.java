package com.periut.testmod.accessory;

import com.periut.accessoryapi.api.Accessory;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class TestAccessory extends TemplateItem implements Accessory {
    protected final String[] types;

    public TestAccessory(Identifier identifier, String... types) {
        super(identifier);
        setTranslationKey(identifier);
        setMaxCount(1);
        setMaxDamage(100);
        this.types = types;
    }

    @Override
    public String[] getAccessoryTypes(ItemStack item) {
        return types;
    }
}
