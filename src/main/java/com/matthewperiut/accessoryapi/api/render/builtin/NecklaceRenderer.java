package com.matthewperiut.accessoryapi.api.render.builtin;

import com.matthewperiut.accessoryapi.api.render.RenderingHelper;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class NecklaceRenderer extends ConfigurableRenderer {
    private static final BipedEntityModel model = new BipedEntityModel(0.6f);

    public NecklaceRenderer(String texture) {
        super(texture);
    }

    @Override
    public void renderThirdPerson(PlayerEntity player, PlayerEntityRenderer renderer, ItemStack itemStack, double x, double y, double z, float h, float v) {
        RenderingHelper.beforeBiped(player, renderer, model, x, y, z, h, v);
        bindTextureAndApplyColors(player.getBrightnessAtEyes(h));
        model.body.render(0.0625f);
        RenderingHelper.afterBiped(model);
    }
}
