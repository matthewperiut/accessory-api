package com.matthewperiut.accessoryapi.impl.mixin.client;

import com.matthewperiut.accessoryapi.AccessoryAPIClient;
import com.matthewperiut.accessoryapi.api.PlayerVisibility;
import com.matthewperiut.accessoryapi.api.helper.AccessoryAccess;
import com.matthewperiut.accessoryapi.api.render.HasCustomRenderer;
import com.matthewperiut.accessoryapi.impl.slot.AccessorySlotStorage;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerRendererMixin extends EntityRenderer {
    @Unique
    PlayerEntity player;

    @Inject(method = "bindTexture", at = @At("HEAD"))
    protected void method_344(PlayerEntity player, int f, float par3, CallbackInfoReturnable<Boolean> cir) {
        this.player = player;
    }

    @Inject(method = "render(Lnet/minecraft/entity/Entity;DDDFF)V", at = @At(value = "HEAD"), cancellable = true)
    private void thirdPersonRenderHEAD(Entity d, double x, double y, double z, float h, float v, CallbackInfo ci) {
        if (((PlayerVisibility) d).isInvisible())
            ci.cancel();
    }

    @Inject(method = "render(Lnet/minecraft/entity/Entity;DDDFF)V", at = @At(value = "TAIL"))
    private void thirdPersonRender(Entity d, double x, double y, double z, float h, float v, CallbackInfo ci) {
        if (((PlayerVisibility) d).isInvisible())
            return;

        AccessoryAPIClient.capeEnabled = true;
        if (EntityRenderDispatcher.INSTANCE.textureManager == null) return;
        try {
            if (player == null)
                return;

            final PlayerEntityRenderer renderer = (PlayerEntityRenderer) (Object) this;

            for (int i = 0; i < AccessorySlotStorage.slotOrder.size() + 4; i++) {
                ItemStack item = player.inventory.getArmorStack(i);
                if (item == null) continue;
                if (item.getItem() instanceof HasCustomRenderer itemWithRenderer) {
                    if (itemWithRenderer.getRenderer() == null) {
                        itemWithRenderer.constructRenderer();
                    } else {
                        itemWithRenderer.getRenderer().renderThirdPerson(player, renderer, item, x, y, z, h, v);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Redirect(
            method = "renderMore(Lnet/minecraft/entity/player/PlayerEntity;F)V",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;playerCapeUrl:Ljava/lang/String;"
            )
    )
    private String toggleCapeRendering(PlayerEntity instance) {
        if (!AccessoryAPIClient.capeEnabled) {
            return null;
        } else {
            return instance.playerCapeUrl;
        }
    }

    @Inject(method = "renderHand", at = @At("TAIL"))
    private void firstPersonRender(CallbackInfo ci) {
        if (EntityRenderDispatcher.INSTANCE.textureManager == null) return;

        PlayerEntity player = ((Minecraft) FabricLoader.getInstance().getGameInstance()).player;
        for (ItemStack item : AccessoryAccess.getAccessories(player)) {
            if (item == null) continue;
            if (item.getItem() instanceof HasCustomRenderer itemWithRenderer) {
                if (itemWithRenderer.getRenderer() == null) {
                    itemWithRenderer.constructRenderer();
                } else {
                    itemWithRenderer.getRenderer().renderFirstPerson(player, (PlayerEntityRenderer) (Object) this, item);
                }
            }
        }
    }
}
