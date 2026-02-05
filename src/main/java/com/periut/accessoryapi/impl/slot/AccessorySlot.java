package com.periut.accessoryapi.impl.slot;

import com.periut.accessoryapi.api.Accessory;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class AccessorySlot extends Slot {
    // delay while pressing accessory slot button to prevent taking / inserting item
    private static final long MILLISECONDS_DELAY = 50;
    String type;

    public AccessorySlot(Inventory arg, int i, int j, int k, String type) {
        super(arg, i, j, k);
        this.type = type;
    }

    public int getMaxItemCount() {
        return 1;
    }

    public boolean canInsert(ItemStack item) {
        if (cancelSlotButtonInterference())
            return false;

        if (item.getItem() instanceof Accessory accessory) {
            for (String type : accessory.getAccessoryTypes(item)) {
                if (type.equals(this.type)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public ItemStack takeStack(int i) {
        if (cancelSlotButtonInterference())
            return null;
        return super.takeStack(i);
    }

    private boolean cancelSlotButtonInterference() {
        EnvType side = FabricLoader.getInstance().getEnvironmentType();
        if (side == EnvType.CLIENT) {
            Minecraft mc = (Minecraft) FabricLoader.getInstance().getGameInstance();
            if (mc.getNetworkHandler() == null)
                return !clientCanInsert();
            else
                return false;
            // revert to default behavior if on server,
            // because I don't want to make a custom packet to sync pressing the button atm
            // todo: button/slot interference multiplayer fix
        } else {
            return false;
        }
    }

    @Environment(EnvType.CLIENT)
    private boolean clientCanInsert() {
        return AccessoryButton.time_clicked + MILLISECONDS_DELAY <= System.currentTimeMillis();
    }
}
