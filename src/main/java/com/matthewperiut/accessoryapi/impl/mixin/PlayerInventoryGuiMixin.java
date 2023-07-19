package com.matthewperiut.accessoryapi.impl.mixin;

import net.minecraft.client.gui.screen.container.ContainerBase;
import net.minecraft.client.gui.screen.container.PlayerInventory;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.entity.player.AbstractClientPlayer;
import org.lwjgl.opengl.GL11;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = PlayerInventory.class)
public abstract class PlayerInventoryGuiMixin extends ContainerBase
{
    @Shadow
    private float mouseX;

    public PlayerInventoryGuiMixin(net.minecraft.container.ContainerBase arg)
    {
        super(arg);
    }

    @Inject(method = "renderContainerBackground", at = @At("TAIL"))
    public void bindAetherPlayerGuiTexture(float par1, CallbackInfo ci)
    {
        PlayerInventory pi = (PlayerInventory) (Object) this;

        int var2 = this.minecraft.textureManager.getTextureId("/assets/accessoryapi/inventory.png");
        this.minecraft.textureManager.bindTexture(var2);

        int texOffsetX = 7, texOffsetY = 7;
        int topSizeX = 154, topSizeY = 72;

        int startX = (this.width - this.containerWidth) / 2;
        int startY = (this.height - this.containerHeight) / 2;
        this.blit(startX + texOffsetX, startY + texOffsetY, texOffsetX, texOffsetY, topSizeX, topSizeY);

        // todo: maybe make this performant?
        //this.minecraft.player.inventory.armour[]

        // blank tile (first inventory slot, bottom left)
        int blankX = 8, blankY = 142;

        for (int i = 0; i < 3; i++)
        {
            if (!(this.minecraft.player.inventory.armour[4 + i] == null))
            {
                this.blit(startX + 80, startY + 8 + 18 * i, blankX, blankY, 16, 16);
            }
        }

        if (!(this.minecraft.player.inventory.armour[7] == null))
            this.blit(startX + 98, startY + 44, blankX, blankY, 16, 16);


        for (int i = 0; i < 2; i++)
        {
            if (!(this.minecraft.player.inventory.armour[8 + i] == null))
            {
                this.blit(startX + 98, startY + 8 + 18 * i, blankX, blankY, 16, 16);
            }
        }

        for (int i = 0; i < 2; i++)
        {
            if (!(this.minecraft.player.inventory.armour[10 + i] == null))
            {
                this.blit(startX + 80 + 18 * i, startY + 62, blankX, blankY, 16, 16);
            }
        }
    }

    int wpx = 0;

    @Redirect(method = "renderContainerBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/container/PlayerInventory;blit(IIIIII)V"))
    public void blitButCaptureWindowPos(PlayerInventory instance, int i, int j, int k, int l, int m, int n)
    {
        instance.blit(i, j, k, l, m, n);
        wpx = i; // window pos x
    }

    @Redirect(method = "renderContainerBackground", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glTranslatef(FFF)V"))
    public void translateAetherPlayerModel(float x, float y, float z)
    {
        if (x != 0.f)
        {
            GL11.glTranslatef(x - 18, y - 50, z);
        }
    }

    @Redirect(method = "renderContainerBackground", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/AbstractClientPlayer;yaw:F", opcode = Opcodes.PUTFIELD, ordinal = 0))
    private void injected(AbstractClientPlayer instance, float value)
    {
        float var9 = (float) (wpx + 33) - this.mouseX;
        instance.field_1012 = (float) Math.atan((double) (var9 / 40.0F)) * 20.0F;
        instance.yaw = (float) Math.atan((double) (var9 / 40.0F)) * 40.0F;
    }

    @Redirect(method = "renderForeground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/TextRenderer;drawText(Ljava/lang/String;III)V", ordinal = 0))
    // net.minecraft.client.gui.screen.container.ContainerBase
    public void renderForeground(TextRenderer instance, String i, int j, int k, int color)
    {

    }
}