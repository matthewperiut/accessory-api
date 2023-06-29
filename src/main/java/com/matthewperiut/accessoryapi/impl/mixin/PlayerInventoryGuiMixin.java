package com.matthewperiut.accessoryapi.impl.mixin;

import net.minecraft.client.gui.screen.container.PlayerInventory;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.entity.player.AbstractClientPlayer;
import org.lwjgl.opengl.GL11;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = PlayerInventory.class)
public class
PlayerInventoryGuiMixin
{
    @Shadow private float mouseX;

    @Redirect(method = "renderContainerBackground", at=@At(value = "INVOKE", target = "Lnet/minecraft/client/texture/TextureManager;bindTexture(I)V"))
    protected void bindAetherPlayerGuiTexture(TextureManager instance, int i)
    {
        instance.bindTexture(instance.getTextureId("/assets/accessoryapi/inventory.png"));
    }

    int wpx = 0;
    @Redirect(method = "renderContainerBackground", at=@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/container/PlayerInventory;blit(IIIIII)V"))
    public void blitButCaptureWindowPos(PlayerInventory instance, int i, int j, int k, int l, int m, int n)
    {
        instance.blit(i,j,k,l,m,n);
        wpx = i; // window pos x
    }

    @Redirect(method = "renderContainerBackground", at=@At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glTranslatef(FFF)V"))
    protected void translateAetherPlayerModel(float x, float y, float z)
    {
        if (x != 0.f)
        {
            GL11.glTranslatef(x - 18,y-50,z);
        }
    }

    @Redirect(method = "renderContainerBackground", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/AbstractClientPlayer;yaw:F", opcode = Opcodes.PUTFIELD, ordinal = 0))
    private void injected(AbstractClientPlayer instance, float value)
    {
        float var9 = (float)(wpx + 33) - this.mouseX;
        instance.field_1012 = (float)Math.atan((double)(var9 / 40.0F)) * 20.0F;
        instance.yaw = (float)Math.atan((double)(var9 / 40.0F)) * 40.0F;
    }

    @Redirect(method = "renderForeground", at=@At(value = "INVOKE", target = "Lnet/minecraft/client/render/TextRenderer;drawText(Ljava/lang/String;III)V", ordinal = 0)) // net.minecraft.client.gui.screen.container.ContainerBase
    public void renderForeground(TextRenderer instance, String i, int j, int k, int color)
    {

    }
}