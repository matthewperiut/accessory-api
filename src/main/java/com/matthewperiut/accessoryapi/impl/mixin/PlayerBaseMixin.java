package com.matthewperiut.accessoryapi.impl.mixin;

import com.matthewperiut.accessoryapi.api.normal.Accessory;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerBase.class)
public abstract class PlayerBaseMixin
{
    @Inject(method = "tick", at = @At("TAIL"))
    public void tick(CallbackInfo ci)
    {
        PlayerBase player = (PlayerBase) ((Object) this);
        for (int i = 4; i < player.inventory.armour.length; i++)
        {
            ItemInstance item = player.inventory.armour[i];
            if (item != null)
            {
                ((Accessory) item.getType()).tickWhileWorn(player, item);
            }
        }
    }
}
