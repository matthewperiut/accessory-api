package com.matthewperiut.testmod.client;

import com.matthewperiut.accessoryapi.api.render.builtin.GloveRenderer;
import net.minecraft.client.render.entity.PlayerRenderer;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;

import java.awt.*;

public class RainbowGloveRenderer extends GloveRenderer {
    public RainbowGloveRenderer(String texture) {
        super(texture);
    }

    @Override
    public void renderFirstPerson(PlayerBase player, PlayerRenderer renderer, ItemInstance itemInstance) {
        float hue = (player.field_1645 % 365) / 365.f;
        color = Color.getHSBColor(hue, 1, 1);
        super.renderFirstPerson(player, renderer, itemInstance);
    }

    @Override
    public void renderThirdPerson(PlayerBase player, PlayerRenderer renderer, ItemInstance itemInstance, double x, double y, double z, float h, float v) {
        float hue = (player.field_1645 % 365) / 365.f;
        color = Color.getHSBColor(hue, 1, 1);
        super.renderThirdPerson(player, renderer, itemInstance, x, y, z, h, v);
    }
}
