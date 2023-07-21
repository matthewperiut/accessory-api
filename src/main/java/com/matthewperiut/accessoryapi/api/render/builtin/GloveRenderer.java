package com.matthewperiut.accessoryapi.api.render.builtin;

import com.matthewperiut.accessoryapi.api.render.RenderingHelper;
import net.minecraft.client.render.entity.PlayerRenderer;
import net.minecraft.client.render.entity.model.Biped;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;

public class GloveRenderer extends ConfigurableRenderer {
    private static final Biped model = new Biped(0.6f);
    public GloveRenderer(String texture) {
        super(texture);
    }

    @Override
    public void renderThirdPerson(PlayerBase player, PlayerRenderer renderer, ItemInstance itemInstance, double x, double y, double z, float h, float v) {
        RenderingHelper.beforeBiped(player, renderer, model, x, y, z, h, v);
        bindTextureAndApplyColors(player.getBrightnessAtEyes(h));
        model.field_623.method_1815(0.0625f);
        model.field_622.method_1815(0.0625f);
        RenderingHelper.afterBiped(model);
    }

    @Override
    public void renderFirstPerson(PlayerBase player, PlayerRenderer renderer, ItemInstance itemInstance) {
        bindTextureAndApplyColors(player.getBrightnessAtEyes(1.0f));

        model.handSwingProgress = 0.0f;
        model.setAngles(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        model.field_622.method_1815(0.0625f);
    }
}
