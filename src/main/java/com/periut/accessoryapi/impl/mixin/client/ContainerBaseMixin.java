package com.periut.accessoryapi.impl.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.periut.accessoryapi.AccessoryAPI;
import com.periut.accessoryapi.impl.slot.VanillaArmorSlot;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.periut.accessoryapi.impl.slot.AccessoryInventoryPlacement.resetPlayerInv;

import net.minecraft.client.gui.screen.ingame.HandledScreen;

@Mixin(HandledScreen.class)
public class ContainerBaseMixin {
    // top-left coords of the empty armour icons in /assets/accessoryapi/inventory.png
    @Unique
    private static final int[] ARMOR_ICON_U = {96, 112, 128, 144};
    @Unique
    private static final int ARMOR_ICON_V = 72;

    @Shadow
    public net.minecraft.screen.ScreenHandler handler;

    @Inject(
            method = "keyPressed",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/ClientPlayerEntity;closeHandledScreen()V"
            )
    )
    protected void keyPressed(char i, int par2, CallbackInfo ci) {
        resetPlayerInv(handler);
    }

    @Inject(
            method = "removed",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/InteractionManager;onScreenRemoved(ILnet/minecraft/entity/player/PlayerEntity;)V"
            )
    )
    public void onClose(CallbackInfo ci) {
        resetPlayerInv(handler);
    }

    // the native empty-slot icon path hardcodes /gui/items.png; use accessoryapi's inventory.png for armour slots
    @WrapOperation(method = "drawSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/texture/TextureManager;getTextureId(Ljava/lang/String;)I"))
    public int armorSlotIconTexture(TextureManager textureManager, String path, Operation<Integer> original, Slot slot) {
        if (slot instanceof VanillaArmorSlot && !AccessoryAPI.noSlotsAdded)
            return textureManager.getTextureId("/assets/accessoryapi/inventory.png");
        return original.call(textureManager, path);
    }

    // the native path computes uv from a 16x16 sprite grid; use the armour icon coords instead
    @WrapOperation(method = "drawSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ingame/HandledScreen;drawTexture(IIIIII)V"))
    public void armorSlotIconUV(HandledScreen instance, int x, int y, int u, int v, int w, int h, Operation<Void> original, Slot slot) {
        if (slot instanceof VanillaArmorSlot armorSlot && !AccessoryAPI.noSlotsAdded) {
            original.call(instance, x, y, ARMOR_ICON_U[armorSlot.getArmorType()], ARMOR_ICON_V, w, h);
        } else {
            original.call(instance, x, y, u, v, w, h);
        }
    }
}
