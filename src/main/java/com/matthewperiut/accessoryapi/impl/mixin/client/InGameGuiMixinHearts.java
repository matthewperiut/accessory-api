package com.matthewperiut.accessoryapi.impl.mixin.client;

import com.matthewperiut.accessoryapi.api.PlayerExtraHP;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.ScreenScaler;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(InGameHud.class)
public class InGameGuiMixinHearts {
    @Shadow
    private Minecraft minecraft;

    @Shadow
    private Random random;

    @Unique
    private void renderHearts(InGameHud instance) {
        int maxHealth = 20 + ((PlayerExtraHP) (minecraft).player).getExtraHP();
        if (!(maxHealth > 20))
            return;
        final ScreenScaler scaledresolution = new ScreenScaler(minecraft.options, minecraft.displayWidth, minecraft.displayHeight);
        final int width = scaledresolution.getScaledWidth();
        final int height = scaledresolution.getScaledHeight();
        GL11.glBindTexture(3553, minecraft.textureManager.getTextureId("/gui/icons.png"));
        GL11.glEnable(3042);
        GL11.glBlendFunc(775, 769);
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        GL11.glDisable(3042);
        boolean flag1 = minecraft.player.hearts / 3 % 2 == 1;
        if (minecraft.player.hearts < 10) {
            flag1 = false;
        }
        final int halfHearts = minecraft.player.health - 20;
        final int prevHalfHearts = minecraft.player.lastHealth - 20;
        if (minecraft.interactionManager.canBeRendered()) {
            for (int heart = 0; heart < maxHealth / 2 - 10; ++heart) {
                int yPos = height - 32 - (((heart / 10) + 1) * 10);
                int k5 = 0;
                if (flag1) {
                    k5 = 1;
                }
                final int xPos = width / 2 - 91 + (heart % 10) * 8;
                if (minecraft.player.health <= 4) {
                    yPos += this.random.nextInt(2);
                }
                instance.drawTexture(xPos, yPos, 16 + k5 * 9, 0, 9, 9);
                if (flag1) {
                    if (heart * 2 + 1 < prevHalfHearts) {
                        instance.drawTexture(xPos, yPos, 70, 0, 9, 9);
                    }
                    if (heart * 2 + 1 == prevHalfHearts) {
                        instance.drawTexture(xPos, yPos, 79, 0, 9, 9);
                    }
                }
                if (heart * 2 + 1 < halfHearts) {
                    instance.drawTexture(xPos, yPos, 52, 0, 9, 9);
                }
                if (heart * 2 + 1 == halfHearts) {
                    instance.drawTexture(xPos, yPos, 61, 0, 9, 9);
                }
            }
        }
        GL11.glDisable(3042);
    }

    public void blitHeart(InGameHud instance, int a, int b, int c, int d, int e, int f) {
        ScreenScaler var5 = new ScreenScaler(this.minecraft.options, this.minecraft.displayWidth, this.minecraft.displayHeight);
        int var6 = var5.getScaledWidth();
        int allowedHearts = (((PlayerExtraHP) minecraft.player).getExtraHP() < 0 ? 10 + (((PlayerExtraHP) minecraft.player).getExtraHP() / 2) : 10);
        int var19 = var6 / 2 - 91 + allowedHearts * 8;
        if (a < var19)
            instance.drawTexture(a,b,c,d,e,f);
    }
    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;drawTexture(IIIIII)V", ordinal = 6))
    public void blitHeart0(InGameHud instance, int a, int b, int c, int d, int e, int f) {
        blitHeart(instance, a, b, c, d, e, f);
    }
    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;drawTexture(IIIIII)V", ordinal = 7))
    public void blitHeart1(InGameHud instance, int a, int b, int c, int d, int e, int f) {
        blitHeart(instance, a, b, c, d, e, f);
    }
    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;drawTexture(IIIIII)V", ordinal = 8))
    public void blitHeart2(InGameHud instance, int a, int b, int c, int d, int e, int f) {
        blitHeart(instance, a, b, c, d, e, f);
    }
    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;drawTexture(IIIIII)V", ordinal = 9))
    public void blitHeart3(InGameHud instance, int a, int b, int c, int d, int e, int f) {
        blitHeart(instance, a, b, c, d, e, f);
    }

    @Inject(method = "render", at = @At(value = "TAIL"))
    public void renderExtraHud(float bl, boolean i, int j, int par4, CallbackInfo ci) {
        InGameHud instance = (InGameHud) (Object) this;
        renderHearts(instance);
    }

    public void moveBubblesUpDependingOnExtraHP(InGameHud instance, int a, int b, int c, int d, int e, int f)
    {
        int multiplier = (((PlayerExtraHP) minecraft.player).getExtraHP() + 18) / 20;
        int movement = 10 * multiplier + (multiplier > 0 ? 1 : 0);
        instance.drawTexture(a,b-movement,c,d,e,f);
    }
    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;drawTexture(IIIIII)V", ordinal = 11))
    public void moveBubblesUpDependingOnExtraHP1(InGameHud instance, int a, int b, int c, int d, int e, int f)
    {
        moveBubblesUpDependingOnExtraHP(instance, a, b, c, d, e, f);
    }
    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;drawTexture(IIIIII)V", ordinal = 12))
    public void moveBubblesUpDependingOnExtraHP2(InGameHud instance, int a, int b, int c, int d, int e, int f)
    {
        moveBubblesUpDependingOnExtraHP(instance, a, b, c, d, e, f);
    }
}
