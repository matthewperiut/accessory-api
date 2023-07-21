package com.matthewperiut.accessoryapi.impl.mixin.client;

import com.matthewperiut.accessoryapi.api.Accessory;
import com.matthewperiut.accessoryapi.api.helper.BipedHelper;
import com.matthewperiut.accessoryapi.impl.AccessoryEntry;
import com.matthewperiut.accessoryapi.impl.PlayerInfo;
import com.matthewperiut.accessoryapi.impl.extended.CustomAccessoryStorage;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.PlayerRenderer;
import net.minecraft.client.render.entity.model.Biped;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.Living;
import net.minecraft.entity.player.PlayerBase;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.matthewperiut.accessoryapi.api.helper.AccessoryRenderHelper.*;

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
            final Object[] pkgedData = new Object[]{x, y, z, h, v};
            PlayerBase player = (PlayerBase) d;
            PlayerInfo models = AccessoryEntry.PlayersAccessoriesModels.computeIfAbsent(player.name, k -> new PlayerInfo());

            boolean pendant = player.inventory.armour[4] != null;
            boolean glove = player.inventory.armour[7] != null;
            if (pendant || glove)
            {
                BipedHelper.before(player, renderer, models.gloveAndPendant, pkgedData);
                if (pendant)
                {
                    // pendant
                    ((Accessory) player.inventory.armour[4].getType()).renderWhileWorn(player, renderer, player.inventory.armour[4], models.gloveAndPendant, pkgedData);
                }
                if (glove)
                {
                    // glove
                    ((Accessory) player.inventory.armour[7].getType()).renderWhileWorn(player, renderer, player.inventory.armour[7], models.gloveAndPendant, pkgedData);
                }
                BipedHelper.after(models.gloveAndPendant);
            }

            if (player.inventory.armour[6] != null)
            {
                // shield
                ((Accessory) player.inventory.armour[6].getType()).renderWhileWorn(player, renderer, player.inventory.armour[6], models.shield, pkgedData);
            }
            // 8 and 9 are rings
            if (player.inventory.armour[10] != null)
            {
                // misc 1
                ((Accessory) player.inventory.armour[10].getType()).renderWhileWorn(player, renderer, player.inventory.armour[10], models.misc1, pkgedData);
            }
            if (player.inventory.armour[11] != null)
            {
                // misc 2
                ((Accessory) player.inventory.armour[11].getType()).renderWhileWorn(player, renderer, player.inventory.armour[11], models.misc2, pkgedData);
            }
            for (int i = 4; i < player.inventory.armour.length; i++)
            {
                if (player.inventory.armour[i] != null)
                {

                    String type = CustomAccessoryStorage.slotOrder.get(i - 12).slotType;
                    Biped custom_model = models.custom_models.get(PlayerInfo.custom_accessory_types.indexOf(type));

                    BipedHelper.before(player, renderer, custom_model, pkgedData);

                    ((Accessory) player.inventory.armour[i].getType()).renderWhileWorn(player, renderer, player.inventory.armour[i], custom_model, pkgedData);

                    BipedHelper.after(custom_model);

                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Unique
    private Biped gloveModel = new Biped(0.6f);

    @Inject(method = "method_345", at = @At(value = "TAIL"))
    private void firstPersonGloveRender(CallbackInfo ci)
    {
        TextureManager var2 = EntityRenderDispatcher.INSTANCE.textureManager;
        if (var2 != null && ClientFirstPersonArmOverlay)
        {
            // todo: do this better
            float brightness = ((Minecraft) FabricLoader.getInstance().getGameInstance()).player.getBrightnessAtEyes(1.0f);

            var2.bindTexture(var2.getTextureId(ClientArmTexture));

            this.gloveModel.handSwingProgress = 0.0f;
            this.gloveModel.setAngles(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
            final int colour = ClientArmColour;
            final float red = (colour >> 16 & 0xFF) / 255.0f;
            final float green = (colour >> 8 & 0xFF) / 255.0f;
            final float blue = (colour & 0xFF) / 255.0f;
            GL11.glColor3f(red * brightness, green * brightness, blue * brightness);
            this.gloveModel.field_622.method_1815(0.0625f);

        }
    }
}
