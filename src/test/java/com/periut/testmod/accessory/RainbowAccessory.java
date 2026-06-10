package com.periut.testmod.accessory;

import com.periut.retroapi.component.RetroComponents;
import com.periut.testmod.TestMod;
import com.periut.testmod.client.RainbowCapeRenderer;
import com.periut.testmod.client.RainbowGloveRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class RainbowAccessory extends TestAccessoryWithRenderer {
    public RainbowAccessory(int id, String... types) {
        super(id, types);
    }

    @Override
    public ItemStack tickWhileWorn(PlayerEntity player, ItemStack itemStack) {
        float hue = RetroComponents.getOrDefault(itemStack, TestMod.HUE, 0f);
        if (hue >= 1) {
            hue = 0;
        } else {
            hue += 1f / 360;
        }
        RetroComponents.set(itemStack, TestMod.HUE, hue);
        return super.tickWhileWorn(player, itemStack);
    }

    @Override
    public void constructRenderer() {
        if (types.length > 0) {
            if (types[0].equals("cape")) {
                renderer = new RainbowCapeRenderer("/assets/testmod/textures/capes/cape.png");
            } else if (types[0].equals("gloves")) {
                renderer = new RainbowGloveRenderer("/assets/testmod/textures/armour/test.png");
            }
        }
    }
}
