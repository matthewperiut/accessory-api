package com.matthewperiut.accessoryapi.impl.mixin;

import com.matthewperiut.accessoryapi.AccessoryAPI;
import com.matthewperiut.accessoryapi.api.Accessory;
import com.matthewperiut.accessoryapi.impl.slot.AccessorySlotStorage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerInventory.class)
public class PlayerInventoryMixin {
    @Shadow
    public ItemStack[] main;

    @Shadow
    public ItemStack[] armor;

    @Shadow
    public PlayerEntity player;

    @Inject(method = "<init>", at = @At("TAIL"))
    public void setNewSize(PlayerEntity par1, CallbackInfo ci) {
        armor = new ItemStack[AccessoryAPI.config.armorOffset + AccessorySlotStorage.slotInfo.size()];
    }

    @ModifyConstant(method = "readNbt", constant = @Constant(intValue = 4), require = 1)
    private int modifyArmourSize(int original) {
        return AccessoryAPI.config.armorOffset + AccessorySlotStorage.slotInfo.size();
    }

    @Inject(method = "readNbt", at = @At("TAIL"))
    public void fromTag(NbtList par1, CallbackInfo ci) {
        int reg_inv_size = main.length;
        for (int slot = reg_inv_size; slot < armor.length + reg_inv_size; slot++) {
            int armour_pos = slot - reg_inv_size;
            ItemStack item = armor[armour_pos];

            if (item != null) {
                if (item.getItem() instanceof Accessory accessory) {
                    accessory.onAccessoryAdded(player, item);
                }
            }
        }
    }

    @Inject(method = "setStack", at = @At("HEAD"))
    public void onSetInventoryItem(int i, ItemStack newItem, CallbackInfo ci) {
        if (i < 36) {
            return;
        }
        if (i >= armor.length) {

            int pos = i - main.length;
            ItemStack oldItem = armor[pos];

            if (oldItem != null) {
                if (oldItem.getItem() instanceof Accessory accessory) {
                    accessory.onAccessoryRemoved(player, oldItem);
                }
            }

            if (newItem != null) {
                // For example, you could set the item count:
                if (newItem.getItem() instanceof Accessory accessory) {
                    accessory.onAccessoryAdded(player, newItem);
                }
            }
        }
    }

    @Inject(method = "removeStack", at = @At("HEAD"))
    public void onTakeInventoryItem(int i, int par2, CallbackInfoReturnable<ItemStack> cir) {
        if (i < 36) {
            return;
        }
        if (i >= main.length) {
            int pos = i - main.length;
            ItemStack oldItem = armor[pos];
            if (oldItem != null) {
                if (oldItem.getItem() instanceof Accessory accessory) {
                    accessory.onAccessoryRemoved(player, oldItem);
                }
            }
        }
    }
}
