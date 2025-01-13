package com.matthewperiut.accessoryapi.impl.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.matthewperiut.accessoryapi.impl.slot.AccessoryInventoryPlacement.resetPlayerInv;

import net.minecraft.client.gui.screen.ingame.HandledScreen;

@Mixin(HandledScreen.class)
public class ContainerBaseMixin {
    @Shadow
    public net.minecraft.screen.ScreenHandler container;

    @Inject(
            method = "keyPressed",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/ClientPlayerEntity;closeHandledScreen()V"
            )
    )
    protected void keyPressed(char i, int par2, CallbackInfo ci) {
        resetPlayerInv(container);
    }

    @Inject(
            method = "removed",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/InteractionManager;onScreenRemoved(ILnet/minecraft/entity/player/PlayerEntity;)V"
            )
    )
    public void onClose(CallbackInfo ci) {
        resetPlayerInv(container);
    }
}
