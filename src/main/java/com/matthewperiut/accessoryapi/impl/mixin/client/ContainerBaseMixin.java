package com.matthewperiut.accessoryapi.impl.mixin.client;

import net.minecraft.client.gui.screen.container.ContainerBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.matthewperiut.accessoryapi.impl.slot.AccessoryInventoryPlacement.resetPlayerInv;

@Mixin(ContainerBase.class)
public class ContainerBaseMixin {
    @Shadow
    public net.minecraft.container.ContainerBase container;

    @Inject(method = "keyPressed", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/AbstractClientPlayer;closeContainer()V"))
    protected void keyPressed(char i, int par2, CallbackInfo ci) {
        resetPlayerInv(container);
    }

    @Inject(method = "onClose", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/BaseClientInteractionManager;closeContainer(ILnet/minecraft/entity/player/PlayerBase;)V"))
    public void onClose(CallbackInfo ci) {
        resetPlayerInv(container);
    }
}
