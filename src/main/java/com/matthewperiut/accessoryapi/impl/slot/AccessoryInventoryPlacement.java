package com.matthewperiut.accessoryapi.impl.slot;

import net.minecraft.entity.player.PlayerContainer;

import static com.matthewperiut.accessoryapi.impl.slot.AccessorySlotStorage.hideOverflowSlots;
import static com.matthewperiut.accessoryapi.impl.slot.AccessorySlotStorage.slotInfo;

public class AccessoryInventoryPlacement
{
    public static int getCraftingOffset()
    {
        int craft_centering_shift = 0;
        if (slotInfo.size() < 1)
            craft_centering_shift -= 18;
        else if (slotInfo.size() < 5)
            craft_centering_shift -= 9;

        return craft_centering_shift;
    }

    public static void resetPlayerInv(net.minecraft.container.ContainerBase container)
    {
        if (container instanceof PlayerContainer pc)
        {
            int crafting_offset_x = getCraftingOffset();

            // crafting result pos ( for aether )
            pc.getSlot(0).x = 134 + crafting_offset_x;
            pc.getSlot(0).y = 62;

            int slot = 1; // skip crafting result
            for (int y = 0; y < 2; ++y)
            {
                for (int x = 0; x < 2; ++x)
                {
                    pc.getSlot(slot).x = 125 + (18 * x) + crafting_offset_x;
                    pc.getSlot(slot).y = 8 + (18 * y);
                    slot++;
                }
            }

            hideOverflowSlots(pc);
        }
    }
}
