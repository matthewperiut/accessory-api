package com.matthewperiut.accessoryapi.api.render;

import com.matthewperiut.accessoryapi.impl.mixin.client.LivingEntityRendererAccessor;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class RenderingHelper {
    public static void beforeBiped(PlayerEntity player, PlayerEntityRenderer playerRenderer, BipedEntityModel model, double x, double y, double z, float h, float v) {
        final ItemStack itemStack = player.getHand();
        model.rightArmPose = (itemStack != null);
        model.sneaking = player.isSneaking();
        double d3 = y - player.standingEyeHeight;

        GL11.glPushMatrix();
        GL11.glEnable(2884);
        model.handSwingProgress = ((LivingEntityRendererAccessor) playerRenderer).invoke820(player, v);
        model.riding = player.hasVehicle();
        final float f2 = player.lastBodyYaw + (player.bodyYaw - player.lastBodyYaw) * v;
        final float f3 = player.prevYaw + (player.yaw - player.prevYaw) * v;
        final float f4 = player.prevPitch + (player.pitch - player.prevPitch) * v;
        ((LivingEntityRendererAccessor) playerRenderer).invoke826(player, x, d3, z);
        final float f5 = ((LivingEntityRendererAccessor) playerRenderer).invoke828(player, v);
        ((LivingEntityRendererAccessor) playerRenderer).invoke824(player, f5, f2, v);
        final float f6 = 0.0625f;
        GL11.glEnable(32826);
        GL11.glScalef(-1.0f, -1.0f, 1.0f);
        ((LivingEntityRendererAccessor) playerRenderer).invoke823(player, v);
        GL11.glTranslatef(0.0f, -24.0f * f6 - 0.0078125f, 0.0f);
        float f7 = Math.min(player.lastWalkAnimationSpeed + (player.walkAnimationSpeed - player.lastWalkAnimationSpeed) * v, 1.0f);
        final float f8 = player.walkAnimationProgress - player.walkAnimationSpeed * (1.0f - v);
        GL11.glEnable(3008);
        model.setAngles(f8, f7, f5, f3 - f2, f4, f6);
    }

    public static void afterBiped(BipedEntityModel model) {
        GL11.glDisable(3042);
        GL11.glDisable(32826);

        GL11.glPopMatrix();
        model.sneaking = false;
        model.rightArmPose = false;
    }
}
