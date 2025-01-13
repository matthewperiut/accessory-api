package com.matthewperiut.testmod.accessory;

import com.matthewperiut.testmod.client.RainbowCapeRenderer;
import com.matthewperiut.testmod.client.RainbowGloveRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.util.Identifier;

public class RainbowAccessory extends TestAccessoryWithRenderer {
    public RainbowAccessory(Identifier identifier, String... types) {
        super(identifier, types);
    }

    @Override
    public ItemStack tickWhileWorn(PlayerEntity player, ItemStack itemStack) {
        var hue = itemStack.getStationNbt().getFloat("hue");
        if (hue >= 1) {
            hue = 0;
        } else {
            hue += 1f / 360;
        }
        itemStack.getStationNbt().putFloat("hue", (Float) hue);
        return super.tickWhileWorn(player, itemStack);
    }

    @Override
    public void constructRenderer() {
        if (types.length > 0) {
            if (types[0].equals("cape")) {
                renderer = new RainbowCapeRenderer("assets/testmod/textures/capes/cape.png");
            } else if (types[0].equals("gloves")) {
                renderer = new RainbowGloveRenderer("assets/testmod/textures/armour/test.png");
            }
        }
    }
}
