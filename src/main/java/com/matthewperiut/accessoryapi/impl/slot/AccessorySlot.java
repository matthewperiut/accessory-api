package com.matthewperiut.accessoryapi.impl.slot;

import com.matthewperiut.accessoryapi.api.Accessory;
import net.minecraft.container.slot.Slot;
import net.minecraft.inventory.InventoryBase;
import net.minecraft.item.ItemInstance;

public class AccessorySlot extends Slot
{
    String type;

    public AccessorySlot(InventoryBase arg, int i, int j, int k, String type)
    {
        super(arg, i, j, k);
        this.type = type;
    }

    public int getMaxStackCount()
    {
        return 1;
    }

    // delay while pressing accessory slot button to prevent taking / inserting item
    private static final long MILLISECONDS_DELAY = 50;

    public boolean canInsert(ItemInstance item)
    {
        if (AccessoryButton.time_clicked + MILLISECONDS_DELAY > System.currentTimeMillis())
            return false;

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

    @Override
    public ItemInstance takeItem(int i)
    {
        if (AccessoryButton.time_clicked + MILLISECONDS_DELAY > System.currentTimeMillis())
            return null;

        return super.takeItem(i);
    }
}
