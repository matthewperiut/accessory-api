package com.matthewperiut.testmod.accessory;

import com.matthewperiut.testmod.client.RainbowCapeRenderer;
import com.matthewperiut.testmod.client.RainbowGloveRenderer;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import net.modificationstation.stationapi.api.registry.Identifier;

public class RainbowAccessory extends TestAccessoryWithRenderer
{
    public RainbowAccessory(Identifier identifier, String... types)
    {
        super(identifier, types);
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

    @Override
    public void constructRenderer()
    {
        if (types.length > 0)
        {
            if (types[0].equals("cape"))
            {
                renderer = new RainbowCapeRenderer("assets/testmod/textures/capes/cape.png");
            }
            else if (types[0].equals("gloves"))
            {
                renderer = new RainbowGloveRenderer("assets/testmod/textures/armour/test.png");
            }
        }
    }
}
