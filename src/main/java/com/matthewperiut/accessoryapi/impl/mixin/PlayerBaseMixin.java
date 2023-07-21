package com.matthewperiut.accessoryapi.impl.mixin;

import com.matthewperiut.accessoryapi.api.Accessory;
import com.matthewperiut.accessoryapi.api.TickableInArmorSlot;
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
        for (int i = 0; i < player.inventory.armour.length; i++)
        {
            ItemInstance item = player.inventory.armour[i];
            if (item != null)
            {
                var newItem = ((TickableInArmorSlot) item.getType()).tickWhileWorn(player, item);
                if (newItem != item) {
                    player.inventory.armour[i] = newItem;
                }
            }
        }
    }
}
