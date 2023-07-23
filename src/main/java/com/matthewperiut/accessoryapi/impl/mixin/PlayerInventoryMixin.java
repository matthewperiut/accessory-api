package com.matthewperiut.accessoryapi.impl.mixin;

import com.matthewperiut.accessoryapi.AccessoryAPI;
import com.matthewperiut.accessoryapi.api.Accessory;
import com.matthewperiut.accessoryapi.impl.slot.AccessorySlotStorage;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemInstance;
import net.minecraft.util.io.ListTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerInventory.class)
public class PlayerInventoryMixin
{
    @Shadow
    public ItemInstance[] main;

    @Shadow
    public ItemInstance[] armour;

    @Shadow
    public PlayerBase player;

    @Inject(method = "<init>", at = @At("TAIL"))
    public void setNewSize(PlayerBase par1, CallbackInfo ci)
    {
        armour = new ItemInstance[AccessoryAPI.config.armorOffset + AccessorySlotStorage.slotInfo.size()];
    }

    @ModifyConstant(method = "fromTag", constant = @Constant(intValue = 4), require = 1)
    private int modifyArmourSize(int original)
    {
        return AccessoryAPI.config.armorOffset + AccessorySlotStorage.slotInfo.size();
    }

    @Inject(method = "fromTag", at = @At("TAIL"))
    public void fromTag(ListTag par1, CallbackInfo ci)
    {
        int reg_inv_size = main.length;
        for (int slot = reg_inv_size; slot < armour.length + reg_inv_size; slot++)
        {
            int armour_pos = slot - reg_inv_size;
            ItemInstance item = armour[armour_pos];

            if (item != null)
            {
                if (item.getType() instanceof Accessory accessory)
                {
                    accessory.onAccessoryAdded(player, item);
                }
            }
        }
    }

    @Inject(method = "setInventoryItem", at = @At("HEAD"))
    public void onSetInventoryItem(int i, ItemInstance newItem, CallbackInfo ci)
    {
        if (i < 36)
        {
            return;
        }
        if (i >= armour.length)
        {

            int pos = i - main.length;
            ItemInstance oldItem = armour[pos];

            if (oldItem != null)
            {
                if (oldItem.getType() instanceof Accessory accessory)
                {
                    accessory.onAccessoryRemoved(player, oldItem);
                }
            }

            if (newItem != null)
            {
                // For example, you could set the item count:
                if (newItem.getType() instanceof Accessory accessory)
                {
                    accessory.onAccessoryAdded(player, newItem);
                }
            }
        }
    }

    @Inject(method = "takeInventoryItem", at = @At("HEAD"))
    public void onTakeInventoryItem(int i, int par2, CallbackInfoReturnable<ItemInstance> cir)
    {
        if (i < 36)
        {
            return;
        }
        if (i >= main.length)
        {
            int pos = i - main.length;
            ItemInstance oldItem = armour[pos];
            if (oldItem != null)
            {
                if (oldItem.getType() instanceof Accessory accessory)
                {
                    accessory.onAccessoryRemoved(player, oldItem);
                }
            }
        }
    }
}
