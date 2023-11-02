package com.matthewperiut.accessoryapi.api.render;

import com.matthewperiut.accessoryapi.impl.mixin.client.LivingEntityRendererAccessor;
import net.minecraft.client.render.entity.PlayerRenderer;
import net.minecraft.client.render.entity.model.Biped;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import org.lwjgl.opengl.GL11;

public class RenderingHelper {
    public static void beforeBiped(PlayerBase player, PlayerRenderer playerRenderer, Biped model, double x, double y, double z, float h, float v) {
        final ItemInstance itemInstance = player.getHeldItem();
        model.field_629 = (itemInstance != null);
        model.field_630 = player.method_1373();
        double d3 = y - player.standingEyeHeight;

        GL11.glPushMatrix();
        GL11.glEnable(2884);
        model.handSwingProgress = ((LivingEntityRendererAccessor) playerRenderer).invoke820(player, v);
        model.isRiding = player.method_1360();
        final float f2 = player.field_1013 + (player.field_1012 - player.field_1013) * v;
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
        float f7 = Math.min(player.field_1048 + (player.limbDistance - player.field_1048) * v, 1.0f);
        final float f8 = player.field_1050 - player.limbDistance * (1.0f - v);
        GL11.glEnable(3008);
        model.setAngles(f8, f7, f5, f3 - f2, f4, f6);
    }

    public static void afterBiped(Biped model) {
        GL11.glDisable(3042);
        GL11.glDisable(32826);

        GL11.glPopMatrix();
        model.field_630 = false;
        model.field_629 = false;
    }
}
