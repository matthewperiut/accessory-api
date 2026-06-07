package com.periut.accessoryapi.impl.mixin.server;

import com.periut.accessoryapi.api.AccessoryHolder;
import com.periut.accessoryapi.impl.AccessoryPersistence;
import com.periut.accessoryapi.impl.slot.AccessorySlotStorage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Syncs accessories to other clients by extending the vanilla equipment system:
 * equipment slot 0 is the held item, 1-4 are armor, and 5+ are accessory slots.
 * <p>
 * The {@code equipment} array is the "last shown" cache that tick() diffs against
 * to decide when to send EntityEquipmentUpdateS2CPackets; resizing it (and answering
 * getEquipment for slots >= 5 from the accessory inventory) makes both the per-tick
 * diff and EntityTrackerEntry's initial sync (which iterates getEquipment()) cover
 * accessories with no custom packets.
 */
@Mixin(ServerPlayerEntity.class)
public class ServerPlayerMixin {
    @Shadow
    private ItemStack[] equipment;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void accessoryapi$resizeEquipmentCache(CallbackInfo ci) {
        // 1 held item + 4 armor + accessory slots
        equipment = new ItemStack[5 + AccessorySlotStorage.getSlotCount()];
    }

    @ModifyConstant(method = "tick()V", constant = @Constant(intValue = 5), require = 1)
    private int accessoryapi$syncAccessoriesInTick(int original) {
        return equipment.length;
    }

    @Inject(method = "getEquipment(I)Lnet/minecraft/item/ItemStack;", at = @At("HEAD"), cancellable = true)
    private void accessoryapi$getAccessoryEquipment(int slot, CallbackInfoReturnable<ItemStack> cir) {
        if (slot >= 5) {
            var accessories = ((AccessoryHolder) this).getAccessoryInventory();
            cir.setReturnValue(slot - 5 < accessories.size() ? accessories.getStack(slot - 5) : null);
        }
    }

    // ServerPlayerEntity.onKilledBy does not call super, so PlayerBaseMixin's death
    // hook never runs on the server player — drop and persist here instead.
    @Inject(method = "onKilledBy", at = @At("TAIL"))
    private void accessoryapi$dropAccessoriesOnDeath(Entity adversary, CallbackInfo ci) {
        ((AccessoryHolder) this).getAccessoryInventory().dropAll();
        AccessoryPersistence.save((PlayerEntity) (Object) this);
    }
}
