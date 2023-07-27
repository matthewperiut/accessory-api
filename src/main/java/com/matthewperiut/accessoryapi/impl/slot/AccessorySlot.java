package com.matthewperiut.accessoryapi.impl.slot;

import com.matthewperiut.accessoryapi.api.Accessory;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
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
        if (cancelSlotButtonInterference())
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
        if (cancelSlotButtonInterference())
            return null;
        return super.takeItem(i);
    }

    private boolean cancelSlotButtonInterference()
    {
        EnvType side = FabricLoader.getInstance().getEnvironmentType();
        if (side == EnvType.CLIENT)
        {
            Minecraft mc = (Minecraft) FabricLoader.getInstance().getGameInstance();
            if (mc.getNetworkHandler() == null)
                return !clientCanInsert();
            else
                return false;
            // revert to default behavior if on server,
            // because I don't want to make a custom packet to sync pressing the button atm
            // todo: button/slot interference multiplayer fix
        }
        else
        {
            return false;
        }
    }

    @Environment(EnvType.CLIENT)
    private boolean clientCanInsert()
    {
        return AccessoryButton.time_clicked + MILLISECONDS_DELAY <= System.currentTimeMillis();
    }
}
