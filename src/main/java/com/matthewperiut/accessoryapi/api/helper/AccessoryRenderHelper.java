package com.matthewperiut.accessoryapi.api.helper;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.model.Biped;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.util.maths.MathHelper;
import org.lwjgl.opengl.GL11;

public class AccessoryRenderHelper
{
    public static void Cape(PlayerBase player, String texture, Biped model, Object[] objects)
    {
        float f = (float) objects[0];

        TextureManager var2 = EntityRenderDispatcher.INSTANCE.textureManager;
        var2.bindTexture(var2.getTextureId(texture));
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0f, 0.0f, 0.125f);
        final double d = player.field_530 + (player.field_533 - player.field_530) * f - (player.prevX + (player.x - player.prevX) * f);
        final double d2 = player.field_531 + (player.field_534 - player.field_531) * f - (player.prevY + (player.y - player.prevY) * f);
        final double d3 = player.field_532 + (player.field_535 - player.field_532) * f - (player.prevZ + (player.z - player.prevZ) * f);
        final float f2 = player.field_1013 + (player.field_1012 - player.field_1013) * f;
        final double d4 = MathHelper.sin(f2 * 3.141593f / 180.0f);
        final double d5 = -MathHelper.cos(f2 * 3.141593f / 180.0f);
        float f3 = (float) d2 * 10.0f;
        if (f3 < -6.0f)
        {
            f3 = -6.0f;
        }
        if (f3 > 32.0f)
        {
            f3 = 32.0f;
        }
        float f4 = (float) (d * d4 + d3 * d5) * 100.0f;
        final float f5 = (float) (d * d5 - d3 * d4) * 100.0f;
        if (f4 < 0.0f)
        {
            f4 = 0.0f;
        }
        final float f6 = player.field_524 + (player.field_525 - player.field_524) * f;
        f3 += MathHelper.sin((player.field_1634 + (player.field_1635 - player.field_1634) * f) * 6.0f) * 32.0f * f6;
        if (player.method_1373())
        {
            f3 += 25.0f;
        }
        GL11.glRotatef(6.0f + f4 / 2.0f + f3, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(f5 / 2.0f, 0.0f, 0.0f, 1.0f);
        GL11.glRotatef(-f5 / 2.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
        model.method_606(0.0625f);
        GL11.glPopMatrix();
    }

    public static void ArmOverlay(PlayerBase player, String texture, int colour, Biped model, Object[] objects)
    {
        float f = (float) objects[3];
        final float brightness = player.getBrightnessAtEyes(f);
        final float f6 = 0.0625f;

        TextureManager var2 = EntityRenderDispatcher.INSTANCE.textureManager;
        var2.bindTexture(var2.getTextureId(texture));
        final float red = (colour >> 16 & 0xFF) / 255.0f;
        final float green = (colour >> 8 & 0xFF) / 255.0f;
        final float blue = (colour & 0xFF) / 255.0f;
        GL11.glColor3f(red * brightness, green * brightness, blue * brightness);
        model.field_623.method_1815(f6);
        model.field_622.method_1815(f6);
    }

    public static String ClientArmTexture;
    public static int ClientArmColour;
    public static boolean ClientFirstPersonArmOverlay;

    public static void enableFirstPersonArmOverlayRender(String texture, int colour)
    {
        ClientArmTexture = texture;
        ClientArmColour = colour;
        ClientFirstPersonArmOverlay = true;
    }

    public static void disableFirstPersonArmOverlay()
    {
        ClientFirstPersonArmOverlay = false;
    }

    public static void TorsoOverlay(PlayerBase player, String texture, int colour, Biped model, Object[] objects)
    {
        float f = (float) objects[3];
        final float brightness = player.getBrightnessAtEyes(f);
        final float f6 = 0.0625f;

        TextureManager var2 = EntityRenderDispatcher.INSTANCE.textureManager;
        var2.bindTexture(var2.getTextureId(texture));
        final float red = (colour >> 16 & 0xFF) / 255.0f;
        final float green = (colour >> 8 & 0xFF) / 255.0f;
        final float blue = (colour & 0xFF) / 255.0f;
        GL11.glColor3f(red * brightness, green * brightness, blue * brightness);
        model.field_621.method_1815(f6);
    }
}
