package com.periut.accessoryapi.impl.mixin.client;

import com.periut.accessoryapi.api.AccessoryHolder;
import com.periut.accessoryapi.api.AccessoryInventory;
import net.minecraft.client.network.OtherPlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Receives the accessory half of the extended equipment sync (see ServerPlayerMixin):
 * EntityEquipmentUpdateS2CPackets with slot >= 5 carry accessories for remote players.
 * Vanilla would index armor[slot - 1] and crash, so route them into the accessory
 * inventory instead — that's what the renderer reads for other players.
 */
@Mixin(OtherPlayerEntity.class)
public abstract class OtherPlayerEntityMixin {
    @Inject(method = "setEquipmentStack", at = @At("HEAD"), cancellable = true)
    private void accessoryapi$setAccessoryStack(int armorSlot, int itemId, int meta, CallbackInfo ci) {
        if (armorSlot >= 5) {
            AccessoryInventory accessories = ((AccessoryHolder) this).getAccessoryInventory();
            int slot = armorSlot - 5;
            if (slot < accessories.size()) {
                ItemStack stack = itemId >= 0 ? new ItemStack(itemId, 1, meta) : null;
                accessories.replaceStack(slot, stack);
            }
            ci.cancel();
        }
    }
}
