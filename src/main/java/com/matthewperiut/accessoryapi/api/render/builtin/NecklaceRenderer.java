package com.matthewperiut.accessoryapi.api.render.builtin;

import com.matthewperiut.accessoryapi.api.render.RenderingHelper;
import net.minecraft.client.render.entity.PlayerRenderer;
import net.minecraft.client.render.entity.model.Biped;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;

public class NecklaceRenderer extends ConfigurableRenderer {
    private static final Biped model = new Biped(0.6f);

    public NecklaceRenderer(String texture) {
        super(texture);
    }

    @Override
    public void renderThirdPerson(PlayerBase player, PlayerRenderer renderer, ItemInstance itemInstance, double x, double y, double z, float h, float v) {
        RenderingHelper.beforeBiped(player, renderer, model, x, y, z, h, v);
        bindTextureAndApplyColors(player.getBrightnessAtEyes(h));
        model.field_621.method_1815(0.0625f);
        RenderingHelper.afterBiped(model);
    }
}
