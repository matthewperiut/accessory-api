package com.matthewperiut.accessoryapi.impl;

import com.matthewperiut.accessoryapi.api.Accessory;
import net.minecraft.container.slot.Slot;
import net.minecraft.inventory.InventoryBase;
import net.minecraft.item.ItemInstance;

public class AccessorySlot extends Slot
{
    String type = "";

    public AccessorySlot(InventoryBase arg, int i, int j, int k, String type)
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
        if (item.getType() instanceof Accessory accessory)
        {
            for (String type : accessory.getAccessoryTypes(item))
            {
                if (type.equals(this.type))
                {
                    return true;
                }
            }
        }
        return false;
    }
}
