package com.matthewperiut.accessoryapi.api.render.builtin;

import com.matthewperiut.accessoryapi.AccessoryAPIClient;
import com.matthewperiut.accessoryapi.api.render.RenderingHelper;
import net.minecraft.client.render.entity.PlayerRenderer;
import net.minecraft.client.render.entity.model.Biped;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.util.maths.MathHelper;
import org.lwjgl.opengl.GL11;

public class CapeRenderer extends ConfigurableRenderer
{
    private static final Biped model = new Biped(0.0f);

    public CapeRenderer(String texture)
    {
        super(texture);
    }

    @Override
    public void renderThirdPerson(PlayerBase player, PlayerRenderer renderer, ItemInstance itemInstance, double x, double y, double z, float h, float v)
    {
        AccessoryAPIClient.capeEnabled = false;

        RenderingHelper.beforeBiped(player, renderer, model, x, y, z, h, v);
        var xAsFloat = (float) x;

        bindTextureAndApplyColors(player.getBrightnessAtEyes(h));

        GL11.glPushMatrix();
        GL11.glTranslatef(0.0f, 0.0f, 0.125f);
        final double d = player.field_530 + (player.field_533 - player.field_530) * xAsFloat - (player.prevX + (player.x - player.prevX) * xAsFloat);
        final double d2 = player.field_531 + (player.field_534 - player.field_531) * xAsFloat - (player.prevY + (player.y - player.prevY) * xAsFloat);
        final double d3 = player.field_532 + (player.field_535 - player.field_532) * xAsFloat - (player.prevZ + (player.z - player.prevZ) * xAsFloat);
        final float f2 = player.field_1013 + (player.field_1012 - player.field_1013) * xAsFloat;
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
        final float f6 = player.field_524 + (player.field_525 - player.field_524) * xAsFloat;
        f3 += MathHelper.sin((player.field_1634 + (player.field_1635 - player.field_1634) * xAsFloat) * 6.0f) * 32.0f * f6;
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
        RenderingHelper.afterBiped(model);
    }
}
