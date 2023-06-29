package com.matthewperiut.accessoryapi.impl;

import com.matthewperiut.accessoryapi.api.Accessory;
import net.minecraft.container.slot.Slot;
import net.minecraft.inventory.InventoryBase;
import net.minecraft.item.ItemInstance;

public class AccessorySlot extends Slot
{
    private final int slot;
    private final InventoryBase inv;
    int type;
    public AccessorySlot(InventoryBase arg, int i, int j, int k, int type)
    {
        super(arg, i, j, k);
        inv = arg;
        slot = i;
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
            return ((Accessory)item.getType()).getType() == Accessory.Type.values()[type];
        }
        else
        {
            return false;
        }
    }
}
