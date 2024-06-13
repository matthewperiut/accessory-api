package com.matthewperiut.accessoryapi.impl.mixin.client;

import com.matthewperiut.accessoryapi.api.BossLivingEntity;
import com.matthewperiut.accessoryapi.api.render.HasCustomRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.InGame;
import net.minecraft.client.util.ScreenScaler;
import net.minecraft.entity.Living;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitType;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.Vec3f;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(InGame.class)
public class InGameGuiMixinHUD extends DrawableHelper {
    @Shadow
    private Minecraft minecraft;

    @Unique
    boolean pumpkin = false;

    @Unique
    Living boss = null;
    @Unique
    int frames = 0;

    @Inject(method = "renderHud", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerInventory;getArmourItem(I)Lnet/minecraft/item/ItemInstance;"))
    public void renderHud(float bl, boolean i, int j, int par4, CallbackInfo ci) {
        InGame instance = (InGame) (Object) this;
        ScreenScaler scaler = new ScreenScaler(this.minecraft.options, this.minecraft.actualWidth, this.minecraft.actualHeight);
        int width = scaler.getScaledWidth();
        int height = scaler.getScaledHeight();

        frames++;
        if (frames > 120) {
            frames = 0;
            List<Living> bosses = new ArrayList<>();
            PlayerBase player = minecraft.player;
            List<Living> entities = player.level.getEntities(Living.class, Box.create(player.x - 20, player.y - 20, player.z - 20, player.x + 20, player.y + 20, player.z + 20));

            Vec3f start = Vec3f.from(player.x, player.y, player.z);
            for (Living entity : entities) {
                if (entity instanceof BossLivingEntity check) {
                    if (check.isBoss()) {
                        if (entity.equals(player))
                            continue;

                        Vec3f end = Vec3f.from(entity.x, entity.y, entity.z);
                        HitResult hitResult = player.level.method_161(start, end, false);
                        if (hitResult == null) {
                            bosses.add(entity);
                        } else if (hitResult.type == HitType.field_790) {
                            bosses.add(entity);
                        }
                    }
                }
            }

            if (bosses.isEmpty()) {
                boss = null;
            }
            if (bosses.size() == 1) {
                boss = bosses.get(0);
            } else if (bosses.size() > 1) {
                Living closest = bosses.get(0);
                for (Living b : bosses) {
                    if (player.distanceTo(b) > closest.distanceTo(player)) {
                        closest = b;
                    }
                }
                boss = closest;
            }
        }

        if (boss != null) {
            final String title = ((BossLivingEntity) boss).getName();
            minecraft.textRenderer.drawTextWithShadow(title, width / 2 - minecraft.textRenderer.getTextWidth(title) / 2, 2, -1);
            GL11.glBindTexture(3553, minecraft.textureManager.getTextureId("/assets/accessoryapi/bossHPBar.png"));
            GL11.glEnable(3042);
            GL11.glBlendFunc(775, 769);
            GL11.glColor3f(1.0f, 1.0f, 1.0f);
            GL11.glDisable(3042);
            instance.blit(width / 2 - 128, 12, 0, 16, 256, 32);
            final int w = (int) (((BossLivingEntity) boss).getHP() / (float) ((BossLivingEntity) boss).getMaxHP() * 256.0f);
            instance.blit(width / 2 - 128, 12, 0, 0, w, 16);

            if (!boss.isAlive()) {
                ((BossLivingEntity) boss).setBoss(false);
                boss = null;
            }
        }


        if (pumpkin) {
            pumpkin = false;
            return;
        }

        for (ItemInstance item : minecraft.player.inventory.armour) {
            if (item == null)
                continue;
            if (item.getType() instanceof HasCustomRenderer customRenderer) {
                if (customRenderer.getRenderer() != null) {
                    customRenderer.getRenderer().renderHUD(minecraft.player, item, minecraft, scaler, width, height);
                }
            }
        }

    }

    @Inject(method = "renderHud", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/InGame;renderPumpkinOverlay(II)V"))
    public void setPumpkin(float bl, boolean i, int j, int par4, CallbackInfo ci) {
        pumpkin = true;
    }
}
