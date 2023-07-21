package com.matthewperiut.testmod.client;

import com.matthewperiut.accessoryapi.api.render.builtin.CapeRenderer;
import net.minecraft.client.render.entity.PlayerRenderer;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;

import java.awt.*;

public class RainbowCapeRenderer extends CapeRenderer {

    public RainbowCapeRenderer(String texture) {
        super(texture);
    }

    @Override
    public void renderThirdPerson(PlayerBase player, PlayerRenderer renderer, ItemInstance itemInstance, double x, double y, double z, float h, float v) {
        var hue = itemInstance.getStationNBT().getFloat("hue");
        color = Color.getHSBColor(hue, 1, 1);
        super.renderThirdPerson(player, renderer, itemInstance, x, y, z, h, v);
    }
}
