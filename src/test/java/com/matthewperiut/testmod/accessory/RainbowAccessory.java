package com.matthewperiut.testmod.accessory;

import com.matthewperiut.accessoryapi.api.render.builtin.ConfigurableRenderer;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import net.modificationstation.stationapi.api.registry.Identifier;

public class RainbowAccessory extends TestAccessoryWithRenderer
{


    public RainbowAccessory(Identifier identifier, ConfigurableRenderer renderer, String... types)
    {
        super(identifier, renderer, types);
    }

    @Override
    public ItemInstance tickWhileWorn(PlayerBase player, ItemInstance itemInstance)
    {
        var hue = itemInstance.getStationNBT().getFloat("hue");
        if (hue >= 1)
        {
            hue = 0;
        }
        else
        {
            hue += 1f / 360;
        }
        itemInstance.getStationNBT().put("hue", hue);
        return super.tickWhileWorn(player, itemInstance);
    }
}
