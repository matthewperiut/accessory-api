package com.matthewperiut.accessoryapi.impl.mixin;

import com.matthewperiut.accessoryapi.api.Accessory;
import com.matthewperiut.accessoryapi.impl.AccessoryEntry;
import com.matthewperiut.accessoryapi.impl.PlayerInfo;
import net.minecraft.client.render.entity.PlayerRenderer;
import net.minecraft.client.render.entity.model.Biped;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.Living;
import net.minecraft.entity.player.PlayerBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public class PlayerRendererMixin
{
    @Shadow private Biped field_294; // cape

    public Biped getCape()
    {
        return field_294;
    }

    @Inject(method = "method_827", at = @At(value = "TAIL")) // cape
    private void capeHandle(Living f, float par2, CallbackInfo ci)
    {
        PlayerBase player = (PlayerBase) f;

        if (player.inventory.armour[5] != null)
        {
            ((Accessory) player.inventory.armour[5].getType()).renderWhileWorn(player, (PlayerRenderer) (Object)this, player.inventory.armour[5], field_294, new Object[]{ par2 });
        }
    }

    private Biped modelShield = new Biped(1.25f);

    @Inject(method = "render(Lnet/minecraft/entity/EntityBase;DDDFF)V", at = @At(value = "TAIL"))
    private void renderEntityCustom(EntityBase d, double x, double y, double z, float h, float v, CallbackInfo ci) {
        try {
            final PlayerRenderer renderer = (PlayerRenderer) (Object)this;
            final Object[] pkgedData = new Object[]{ x, y, z, h, v };
            PlayerBase player = (PlayerBase) d;
            PlayerInfo models = AccessoryEntry.PlayersAccessoriesModels.computeIfAbsent(player.name, k -> new PlayerInfo());
            if (player.inventory.armour[4] != null)
            {
                // pendant
                ((Accessory) player.inventory.armour[4].getType()).renderWhileWorn(player, renderer, player.inventory.armour[4], models.gloveAndPendant, pkgedData);
            }
            if (player.inventory.armour[6] != null)
            {
                // shield
                ((Accessory) player.inventory.armour[6].getType()).renderWhileWorn(player, renderer, player.inventory.armour[6], models.shield, pkgedData);
            }
            // 7 and 8 are rings
            if (player.inventory.armour[9] != null)
            {
                // glove
                ((Accessory) player.inventory.armour[9].getType()).renderWhileWorn(player, renderer, player.inventory.armour[9], models.gloveAndPendant, pkgedData);
            }
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
        }catch(Exception ex) {ex.printStackTrace();}
    }
}
