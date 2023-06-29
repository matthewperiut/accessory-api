package com.matthewperiut.accessoryapi.impl.mixin;

import com.matthewperiut.accessoryapi.api.Accessory;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerBase.class)
public abstract class PlayerBaseMixin
{
    int updateAccessoryTick = 0;
    ItemInstance[] hasItem = new ItemInstance[8];

    @Inject(method = "tick", at=@At("TAIL"))
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

        updateAccessoryTick++;
        if (updateAccessoryTick > 10)
        {
            for (int i = 0; i < 8; i++)
            {
                if (player.inventory.armour[i+3] != hasItem[i])
                {
                    if (player.inventory.armour[i+3] == null)
                    {
                        if (hasItem[i] != null)
                        {
                            // doesn't have anymore
                            ((Accessory) hasItem[i].getType()).onAccessoryRemoved(player, hasItem[i]);
                            hasItem[i] = null;
                        }
                    }
                    else if (hasItem[i] == null)
                    {
                        // now has
                        ((Accessory)player.inventory.armour[i+3].getType()).onAccessoryAdded(player, player.inventory.armour[i+3]);
                        hasItem[i] = player.inventory.armour[i+3];
                    }
                    else
                    {
                        ((Accessory) hasItem[i].getType()).onAccessoryRemoved(player, hasItem[i]);
                        ((Accessory)player.inventory.armour[i+3].getType()).onAccessoryAdded(player, player.inventory.armour[i+3]);
                        hasItem[i] = hasItem[i] = player.inventory.armour[i+3];
                    }
                }
            }
            updateAccessoryTick = 0;
        }
    }
}
