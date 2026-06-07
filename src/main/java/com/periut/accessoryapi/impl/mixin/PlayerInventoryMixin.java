package com.periut.accessoryapi.impl.mixin;

import com.periut.accessoryapi.impl.AccessoryPersistence;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * The armor array is no longer resized — accessories live in their own
 * {@link com.periut.accessoryapi.api.AccessoryInventory}. This mixin only
 * migrates items that older versions of the mod stored as extra armor slots
 * (Slot bytes >= 100 + armorOffset) into the new storage.
 */
@Mixin(PlayerInventory.class)
public class PlayerInventoryMixin {
    @Shadow
    public PlayerEntity player;

    @Inject(method = "readNbt", at = @At("TAIL"))
    public void accessoryapi$migrateLegacyAccessories(NbtList nbt, CallbackInfo ci) {
        AccessoryPersistence.migrateLegacyInventory(player, nbt);
    }
}
