package com.matthewperiut.accessoryapi.impl;

import com.matthewperiut.accessoryapi.api.Accessory;
import com.matthewperiut.accessoryapi.api.AccessoryType;
import net.minecraft.container.slot.Slot;
import net.minecraft.inventory.InventoryBase;
import net.minecraft.item.ItemInstance;

public class AccessorySlot extends Slot
{
    AccessoryType type;
    String customType = "";
    boolean custom = false;

    public AccessorySlot(InventoryBase arg, int i, int j, int k, AccessoryType type)
    {
        super(arg, i, j, k);
        this.type = type;
    }

    public AccessorySlot(InventoryBase arg, int i, int j, int k, String customType)
    {
        super(arg, i, j, k);
        this.customType = customType;
        custom = true;
    }

    public int getMaxStackCount()
    {
        return 1;
    }

    public boolean canInsert(ItemInstance item)
    {
        if (item.getType() instanceof Accessory accessory)
        {
            if (custom)
            {
                for (String type : accessory.getCustomAccessoryTypes(item))
                {
                    if (type.equals(this.customType))
                    {
                        return true;
                    }
                }
            }
            else
            {
                for (AccessoryType accessoryType : accessory.getAccessoryTypes(item))
                {
                    if (accessoryType == type)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
