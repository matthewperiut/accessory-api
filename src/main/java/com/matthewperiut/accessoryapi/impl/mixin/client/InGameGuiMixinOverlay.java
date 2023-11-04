package com.matthewperiut.accessoryapi.impl.mixin.client;

import com.matthewperiut.accessoryapi.api.render.HasCustomRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.InGame;
import net.minecraft.client.util.ScreenScaler;
import net.minecraft.item.ItemInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGame.class)
public class InGameGuiMixinOverlay extends DrawableHelper {
    @Shadow private Minecraft minecraft;

    @Unique boolean pumpkin = false;

    @Inject(method = "renderHud", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerInventory;getArmourItem(I)Lnet/minecraft/item/ItemInstance;"))
    public void renderHud(float bl, boolean i, int j, int par4, CallbackInfo ci) {
        if (pumpkin)
        {
            pumpkin = false;
            return;
        }

        ScreenScaler var5 = new ScreenScaler(this.minecraft.options, this.minecraft.actualWidth, this.minecraft.actualHeight);
        int var6 = var5.getScaledWidth();
        int var7 = var5.getScaledHeight();

        for (ItemInstance item : minecraft.player.inventory.armour) {
            if (item == null)
                continue;
            if (item.getType() instanceof HasCustomRenderer customRenderer) {
                if (customRenderer.getRenderer().isPresent())
                {
                    customRenderer.getRenderer().get().renderHUD(minecraft.player, item, minecraft, var5, var6, var7);
                }
            }
        }

    }

    @Inject(method = "renderHud", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/InGame;renderPumpkinOverlay(II)V"))
    public void setPumpkin(float bl, boolean i, int j, int par4, CallbackInfo ci)
    {
        pumpkin = true;
    }
}
