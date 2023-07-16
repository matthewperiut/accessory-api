package com.matthewperiut.accessoryapi.impl;

import com.matthewperiut.accessoryapi.api.Accessory;
import net.minecraft.container.slot.Slot;
import net.minecraft.inventory.InventoryBase;
import net.minecraft.item.ItemInstance;

public class AccessorySlot extends Slot
{
    Accessory.Type type;
    public AccessorySlot(InventoryBase arg, int i, int j, int k, Accessory.Type type)
    {
        super(arg, i, j, k);
        this.type = type;
    }

    public int getMaxStackCount()
    {
        return 1;
    }

    public boolean canInsert(ItemInstance item)
    {
        if (item.getType() instanceof Accessory)
        {
            return ((Accessory)item.getType()).getType() == type;
        }
        else
        {
            return false;
        }
    }
}
