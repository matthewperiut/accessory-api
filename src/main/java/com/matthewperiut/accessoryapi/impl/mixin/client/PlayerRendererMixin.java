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
import net.minecraft.client.render.entity.PlayerRenderer;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin extends EntityRenderer {
    @Unique
    PlayerBase player;

    @Inject(method = "method_344", at = @At("HEAD"))
    protected void method_344(PlayerBase player, int f, float par3, CallbackInfoReturnable<Boolean> cir) {
        this.player = player;
    }

    @Inject(method = "render(Lnet/minecraft/entity/EntityBase;DDDFF)V", at = @At(value = "HEAD"), cancellable = true)
    private void thirdPersonRenderHEAD(EntityBase d, double x, double y, double z, float h, float v, CallbackInfo ci) {
        if (((PlayerVisibility) d).isInvisible())
            ci.cancel();
    }

    @Inject(method = "render(Lnet/minecraft/entity/EntityBase;DDDFF)V", at = @At(value = "TAIL"))
    private void thirdPersonRender(EntityBase d, double x, double y, double z, float h, float v, CallbackInfo ci) {
        if (((PlayerVisibility) d).isInvisible())
            return;

        AccessoryAPIClient.capeEnabled = true;
        if (EntityRenderDispatcher.INSTANCE.textureManager == null) return;
        try {
            if (player == null)
                return;

            final PlayerRenderer renderer = (PlayerRenderer) (Object) this;

            for (int i = 0; i < AccessorySlotStorage.slotOrder.size() + 4; i++) {
                ItemInstance item = player.inventory.getArmourItem(i);
                if (item == null) continue;
                if (item.getType() instanceof HasCustomRenderer itemWithRenderer) {
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
            method = "method_342(Lnet/minecraft/entity/player/PlayerBase;F)V",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/entity/player/PlayerBase;playerCloakUrl:Ljava/lang/String;"
            )
    )
    private String toggleCapeRendering(PlayerBase instance) {
        if (!AccessoryAPIClient.capeEnabled) {
            return null;
        } else {
            return instance.playerCloakUrl;
        }
    }

    @Inject(method = "method_345", at = @At("TAIL"))
    private void firstPersonRender(CallbackInfo ci) {
        if (EntityRenderDispatcher.INSTANCE.textureManager == null) return;

        PlayerBase player = ((Minecraft) FabricLoader.getInstance().getGameInstance()).player;
        for (ItemInstance item : AccessoryAccess.getAccessories(player)) {
            if (item == null) continue;
            if (item.getType() instanceof HasCustomRenderer itemWithRenderer) {
                if (itemWithRenderer.getRenderer() == null) {
                    itemWithRenderer.constructRenderer();
                } else {
                    itemWithRenderer.getRenderer().renderFirstPerson(player, (PlayerRenderer) (Object) this, item);
                }
            }
        }
    }
}
