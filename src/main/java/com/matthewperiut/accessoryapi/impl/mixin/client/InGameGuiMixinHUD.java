package com.matthewperiut.accessoryapi.impl.mixin.client;

import com.matthewperiut.accessoryapi.api.BossLivingEntity;
import com.matthewperiut.accessoryapi.api.render.HasCustomRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.ScreenScaler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitResultType;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(InGameHud.class)
public class InGameGuiMixinHUD extends DrawContext {
    @Shadow
    private Minecraft minecraft;

    @Unique
    boolean pumpkin = false;

    @Unique
    LivingEntity boss = null;
    @Unique
    int frames = 0;

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerInventory;getArmorStack(I)Lnet/minecraft/item/ItemStack;"))
    public void renderHud(float bl, boolean i, int j, int par4, CallbackInfo ci) {
        InGameHud instance = (InGameHud) (Object) this;
        ScreenScaler scaler = new ScreenScaler(this.minecraft.options, this.minecraft.displayWidth, this.minecraft.displayHeight);
        int width = scaler.getScaledWidth();
        int height = scaler.getScaledHeight();

        frames++;
        if (frames > 120) {
            frames = 0;
            List<LivingEntity> bosses = new ArrayList<>();
            PlayerEntity player = minecraft.player;
            List<LivingEntity> entities = player.world.collectEntitiesByClass(LivingEntity.class, Box.create(player.x - 20, player.y - 20, player.z - 20, player.x + 20, player.y + 20, player.z + 20));

            Vec3d start = Vec3d.createCached(player.x, player.y, player.z);
            for (LivingEntity entity : entities) {
                if (entity instanceof BossLivingEntity check) {
                    if (check.isBoss()) {
                        if (entity.equals(player))
                            continue;

                        Vec3d end = Vec3d.createCached(entity.x, entity.y, entity.z);
                        HitResult hitResult = player.world.raycast(start, end, false);
                        if (hitResult == null) {
                            bosses.add(entity);
                        } else if (hitResult.type == HitResultType.ENTITY) {
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
                LivingEntity closest = bosses.get(0);
                for (LivingEntity b : bosses) {
                    if (player.getDistance(b) > closest.getDistance(player)) {
                        closest = b;
                    }
                }
                boss = closest;
            }
        }

        if (boss != null) {
            final String title = ((BossLivingEntity) boss).getName();
            minecraft.textRenderer.drawWithShadow(title, width / 2 - minecraft.textRenderer.getWidth(title) / 2, 2, -1);
            GL11.glBindTexture(3553, minecraft.textureManager.getTextureId("/assets/accessoryapi/bossHPBar.png"));
            GL11.glEnable(3042);
            GL11.glBlendFunc(775, 769);
            GL11.glColor3f(1.0f, 1.0f, 1.0f);
            GL11.glDisable(3042);
            instance.drawTexture(width / 2 - 128, 12, 0, 16, 256, 32);
            final int w = (int) (((BossLivingEntity) boss).getHP() / (float) ((BossLivingEntity) boss).getMaxHP() * 256.0f);
            instance.drawTexture(width / 2 - 128, 12, 0, 0, w, 16);

            if (!boss.isAlive()) {
                ((BossLivingEntity) boss).setBoss(false);
                boss = null;
            }
        }


        if (pumpkin) {
            pumpkin = false;
            return;
        }

        for (ItemStack item : minecraft.player.inventory.armor) {
            if (item == null)
                continue;
            if (item.getItem() instanceof HasCustomRenderer customRenderer) {
                if (customRenderer.getRenderer() != null) {
                    customRenderer.getRenderer().renderHUD(minecraft.player, item, minecraft, scaler, width, height);
                }
            }
        }

    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderPumpkinOverlay(II)V"))
    public void setPumpkin(float bl, boolean i, int j, int par4, CallbackInfo ci) {
        pumpkin = true;
    }
}
