package com.matthewperiut.accessoryapi.impl.mixin.client;

import com.matthewperiut.accessoryapi.api.helper.AccessoryAccess;
import com.matthewperiut.accessoryapi.api.render.HasCustomRenderer;
import com.matthewperiut.accessoryapi.impl.AccessoryEntry;
import com.matthewperiut.accessoryapi.impl.PlayerInfo;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.entity.PlayerRenderer;
import net.minecraft.client.render.entity.model.Biped;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.Living;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public class PlayerRendererMixin
{
    @Shadow
    private Biped field_294; // cape

    @Inject(method = "method_827", at = @At(value = "TAIL")) // cape
    private void capeHandle(Living f, float par2, CallbackInfo ci)
    {
        PlayerBase player = (PlayerBase) f;

        if (player.inventory.armour[5] != null)
        {
            ((Accessory) player.inventory.armour[5].getType()).renderWhileWorn(player, (PlayerRenderer) (Object) this, player.inventory.armour[5], field_294, new Object[]{par2});
        }
    }

    @Inject(method = "render(Lnet/minecraft/entity/EntityBase;DDDFF)V", at = @At(value = "TAIL"))
    private void renderEntityCustom(EntityBase d, double x, double y, double z, float h, float v, CallbackInfo ci)
    {
        try
        {
            final PlayerRenderer renderer = (PlayerRenderer) (Object) this;
            PlayerBase player = (PlayerBase) d;

            for (ItemInstance item : AccessoryAccess.getAccessories(player)) {
                if (item == null) continue;
                if (item.getType() instanceof HasCustomRenderer itemWithRenderer) {
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
    private void firstPersonGloveRender(CallbackInfo ci)
    {
        PlayerBase player = ((Minecraft) FabricLoader.getInstance().getGameInstance()).player;
        for (ItemInstance item : AccessoryAccess.getAccessories(player)) {
            if (item == null) continue;
            if (item.getType() instanceof HasCustomRenderer itemWithRenderer) {
                itemWithRenderer.getRenderer().renderFirstPerson(player, (PlayerRenderer) (Object) this, item);
            }
        }
    }
}
