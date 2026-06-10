package com.periut.testmod.accessory;

import com.periut.accessoryapi.api.Accessory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TestAccessory extends Item implements Accessory {
    protected final String[] types;

    public TestAccessory(int id, String... types) {
        super(id);
        setMaxCount(1);
        setMaxDamage(100);
        this.types = types;
    }

    @Override
    public String[] getAccessoryTypes(ItemStack item) {
        return types;
    }
}
