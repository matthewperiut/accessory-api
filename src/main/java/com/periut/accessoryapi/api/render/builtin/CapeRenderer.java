package com.periut.accessoryapi.api.render.builtin;

import com.periut.accessoryapi.AccessoryAPIClient;
import com.periut.accessoryapi.api.render.RenderingHelper;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class CapeRenderer extends ConfigurableRenderer {
    private static final BipedEntityModel model = new BipedEntityModel(0.0f);

    public CapeRenderer(String texture) {
        super(texture);
    }

    @Override
    public void renderThirdPerson(PlayerEntity player, PlayerEntityRenderer renderer, ItemStack itemStack, double x, double y, double z, float h, float v) {
        AccessoryAPIClient.capeEnabled = false;

        RenderingHelper.beforeBiped(player, renderer, model, x, y, z, h, v);
        var xAsFloat = (float) x;

        bindTextureAndApplyColors(player.getBrightnessAtEyes(h));

        GL11.glPushMatrix();
        GL11.glTranslatef(0.0f, 0.0f, 0.125f);
        final double d = player.prevCapeX + (player.capeX - player.prevCapeX) * xAsFloat - (player.prevX + (player.x - player.prevX) * xAsFloat);
        final double d2 = player.prevCapeY + (player.capeY - player.prevCapeY) * xAsFloat - (player.prevY + (player.y - player.prevY) * xAsFloat);
        final double d3 = player.prevCapeZ + (player.capeZ - player.prevCapeZ) * xAsFloat - (player.prevZ + (player.z - player.prevZ) * xAsFloat);
        final float f2 = player.lastBodyYaw + (player.bodyYaw - player.lastBodyYaw) * xAsFloat;
        final double d4 = MathHelper.sin(f2 * 3.141593f / 180.0f);
        final double d5 = -MathHelper.cos(f2 * 3.141593f / 180.0f);
        float f3 = (float) d2 * 10.0f;
        if (f3 < -6.0f) {
            f3 = -6.0f;
        }
        if (f3 > 32.0f) {
            f3 = 32.0f;
        }
        float f4 = (float) (d * d4 + d3 * d5) * 100.0f;
        final float f5 = (float) (d * d5 - d3 * d4) * 100.0f;
        if (f4 < 0.0f) {
            f4 = 0.0f;
        }
        final float f6 = player.prevStepBobbingAmount + (player.stepBobbingAmount - player.prevStepBobbingAmount) * xAsFloat;
        f3 += MathHelper.sin((player.prevHorizontalSpeed + (player.horizontalSpeed - player.prevHorizontalSpeed) * xAsFloat) * 6.0f) * 32.0f * f6;
        if (player.isSneaking()) {
            f3 += 25.0f;
        }
        GL11.glRotatef(6.0f + f4 / 2.0f + f3, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(f5 / 2.0f, 0.0f, 0.0f, 1.0f);
        GL11.glRotatef(-f5 / 2.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
        model.renderCape(0.0625f);
        GL11.glPopMatrix();
        RenderingHelper.afterBiped(model);
    }
}
