package com.matthewperiut.accessoryapi.api.helper;

import com.matthewperiut.accessoryapi.impl.mixin.LivingEntityRendererAccessor;
import net.minecraft.client.render.entity.PlayerRenderer;
import net.minecraft.client.render.entity.model.Biped;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import org.lwjgl.opengl.GL11;

public class BipedHelper
{
    public static void before(PlayerBase player, PlayerRenderer playerRenderer, Biped model, Object[] objects)
    {
        double d = (double) objects[0];
        double d1 = (double) objects[1];
        double d2 = (double) objects[2];
        float f = (float) objects[3];
        float f1 = (float) objects[4];

        final ItemInstance itemstack = player.getHeldItem();
        model.field_629 = (itemstack != null);
        model.field_630 = player.method_1373();
        double d3 = d1 - player.standingEyeHeight;
        if (player.method_1373() && !(player instanceof PlayerBase))
        {
            d3 -= 0.125;
        }

        GL11.glPushMatrix();
        GL11.glEnable(2884);
        model.handSwingProgress = ((LivingEntityRendererAccessor) playerRenderer).invoke820(player, f1);
        model.isRiding = player.method_1360();
        final float f2 = player.field_1013 + (player.field_1012 - player.field_1013) * f1;
        final float f3 = player.prevYaw + (player.yaw - player.prevYaw) * f1;
        final float f4 = player.prevPitch + (player.pitch - player.prevPitch) * f1;
        ((LivingEntityRendererAccessor) playerRenderer).invoke826(player, d, d3, d2);
        final float f5 = ((LivingEntityRendererAccessor) playerRenderer).invoke828(player, f1);
        ((LivingEntityRendererAccessor) playerRenderer).invoke824(player, f5, f2, f1);
        final float f6 = 0.0625f;
        GL11.glEnable(32826);
        GL11.glScalef(-1.0f, -1.0f, 1.0f);
        ((LivingEntityRendererAccessor) playerRenderer).invoke823(player, f1);
        GL11.glTranslatef(0.0f, -24.0f * f6 - 0.0078125f, 0.0f);
        float f7 = player.field_1048 + (player.limbDistance - player.field_1048) * f1;
        final float f8 = player.field_1050 - player.limbDistance * (1.0f - f1);
        if (f7 > 1.0f)
        {
            f7 = 1.0f;
        }
        GL11.glEnable(3008);
        model.setAngles(f8, f7, f5, f3 - f2, f4, f6);
    }

    public static void after(Biped model)
    {
        GL11.glDisable(3042);
        GL11.glDisable(32826);

        GL11.glPopMatrix();
        model.field_630 = false;
        model.field_629 = false;
    }
}
