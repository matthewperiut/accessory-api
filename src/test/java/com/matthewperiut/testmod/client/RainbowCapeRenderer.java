package com.matthewperiut.testmod.client;

import com.matthewperiut.accessoryapi.api.render.builtin.CapeRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.awt.*;

public class RainbowCapeRenderer extends CapeRenderer {

    public RainbowCapeRenderer(String texture) {
        super(texture);
    }

    @Override
    public void renderThirdPerson(PlayerEntity player, PlayerEntityRenderer renderer, ItemStack itemStack, double x, double y, double z, float h, float v) {
        float hue = (player.age % 365) / 365.f;
        color = Color.getHSBColor(hue, 1, 1);
        super.renderThirdPerson(player, renderer, itemStack, x, y, z, h, v);
    }
}
