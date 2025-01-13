package com.matthewperiut.accessoryapi.api.render.builtin;

import com.matthewperiut.accessoryapi.api.render.RenderingHelper;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class GloveRenderer extends ConfigurableRenderer {
    private static final BipedEntityModel model = new BipedEntityModel(0.6f);

    public GloveRenderer(String texture) {
        super(texture);
    }

    @Override
    public void renderThirdPerson(PlayerEntity player, PlayerEntityRenderer renderer, ItemStack itemStack, double x, double y, double z, float h, float v) {
        RenderingHelper.beforeBiped(player, renderer, model, x, y, z, h, v);
        bindTextureAndApplyColors(player.getBrightnessAtEyes(h));
        model.leftArm.render(0.0625f);
        model.rightArm.render(0.0625f);
        RenderingHelper.afterBiped(model);
    }

    @Override
    public void renderFirstPerson(PlayerEntity player, PlayerEntityRenderer renderer, ItemStack itemStack) {
        bindTextureAndApplyColors(player.getBrightnessAtEyes(1.0f));

        model.handSwingProgress = 0.0f;
        model.setAngles(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        model.rightArm.render(0.0625f);
    }
}
