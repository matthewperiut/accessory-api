package com.matthewperiut.accessoryapi.impl.mixin.client;

import com.matthewperiut.accessoryapi.api.helper.AccessoryAccess;
import com.matthewperiut.accessoryapi.api.render.HasCustomRenderer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.PlayerRenderer;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public class PlayerRendererMixin
{

    @Inject(method = "render(Lnet/minecraft/entity/EntityBase;DDDFF)V", at = @At(value = "TAIL"))
    private void thirdPersonRender(EntityBase d, double x, double y, double z, float h, float v, CallbackInfo ci)
    {
        if (EntityRenderDispatcher.INSTANCE.textureManager == null) return;
        try
        {
            final PlayerRenderer renderer = (PlayerRenderer) (Object) this;
            PlayerBase player = (PlayerBase) d;

            for (ItemInstance item : AccessoryAccess.getAccessories(player))
            {
                if (item == null) continue;
                if (item.getType() instanceof HasCustomRenderer itemWithRenderer)
                {
                    itemWithRenderer.getRenderer().renderThirdPerson(player, renderer, item, x, y, z, h, v);
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Inject(method = "method_345", at = @At(value = "TAIL"))
    private void firstPersonRender(CallbackInfo ci)
    {
        if (EntityRenderDispatcher.INSTANCE.textureManager == null) return;

        PlayerBase player = ((Minecraft) FabricLoader.getInstance().getGameInstance()).player;
        for (ItemInstance item : AccessoryAccess.getAccessories(player))
        {
            if (item == null) continue;
            if (item.getType() instanceof HasCustomRenderer itemWithRenderer)
            {
                itemWithRenderer.getRenderer().renderFirstPerson(player, (PlayerRenderer) (Object) this, item);
            }
        }
    }
}
