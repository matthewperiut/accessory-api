package com.matthewperiut.accessoryapi.impl.mixin.client;

import com.matthewperiut.accessoryapi.AccessoryAPI;
import com.matthewperiut.accessoryapi.impl.slot.AccessoryButton;
import com.matthewperiut.accessoryapi.impl.slot.AccessorySlotStorage;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.slot.Slot;
import org.lwjgl.opengl.GL11;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.matthewperiut.accessoryapi.impl.slot.AccessoryInventoryPlacement.resetPlayerInv;
import static com.matthewperiut.accessoryapi.impl.slot.AccessorySlotStorage.*;

@Mixin(value = InventoryScreen.class)
public abstract class PlayerInventoryGuiMixin extends HandledScreen {
    // uses the aether-style armour slot by having the armour slots on the right of the player rendering
    @Unique
    private static final int AETHER_U = 0, AETHER_V = 0, AETHER_W = 154, AETHER_H = 72;
    // regular armour slot location
    @Unique
    private static final int REGULAR_U = 72, REGULAR_V = 0, REGULAR_W = 82, REGULAR_H = 72;
    @Unique
    private static final int SLOT_U = 154, SLOT_V = 54, SLOT_W = 18, SLOT_H = 18;
    @Unique
    private static final int CRAFT_U = 172, CRAFT_V = 0, CRAFT_W = 36, CRAFT_H = 72;
    @Unique
    private static final int CRAFT_X = 117;
    @Unique
    private static final int CORNER_INSET = 7;
    @Unique
    boolean extended = false;
    @Unique
    int wpx = 0;
    @Shadow
    private float mouseX;
    public PlayerInventoryGuiMixin(net.minecraft.screen.ScreenHandler arg) {
        super(arg);
    }

    @Inject(method = "init", at = @At("TAIL"))
    public void init(CallbackInfo ci) {
        int startX = (width - backgroundWidth) / 2;
        int startY = (height - backgroundHeight) / 2;

        if (slotOrder.size() > 8) buttons.add(new AccessoryButton(250, startX + 115, startY + 6));
    }

    @Inject(method = "buttonClicked", at = @At("TAIL"))
    protected void buttonClicked(ButtonWidget button, CallbackInfo ci) {
        if (button.id == 250) {
            int startX = (width - backgroundWidth) / 2;
            int startY = (height - backgroundHeight) / 2;
            extended = !extended;
            ((AccessoryButton) button).goBack = extended;

            if (extended) {
                for (int i = 0; i < 5; i++) {
                    ((Slot) handler.slots.get(i)).y = 1000;
                }

                button.x = startX + 165;
                button.y = startY + 1;
                showOverflowSlots((PlayerScreenHandler) handler);
            } else {
                resetPlayerInv(handler);
                button.x = startX + 115;
                button.y = startY + 6;
                hideOverflowSlots((PlayerScreenHandler) handler);
            }


            AccessoryButton.time_clicked = System.currentTimeMillis();
        }
    }

    @Inject(method = "drawBackground", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glEnable(I)V", ordinal = 0))
    public void bindAetherPlayerGuiTexture(float par1, CallbackInfo ci) {
        if (AccessoryAPI.noSlotsAdded)
            return;

        int var2 = minecraft.textureManager.getTextureId("/assets/accessoryapi/inventory.png");
        minecraft.textureManager.bindTexture(var2);

        int startX = (width - backgroundWidth) / 2;
        int startY = (height - backgroundHeight) / 2;
        if (AccessoryAPI.config.aetherStyleArmor) {
            //blit(startX + texOffsetX, startY + texOffsetY, texOffsetX, texOffsetY, topSizeX, topSizeY);
            drawTexture(startX + CORNER_INSET, startY + CORNER_INSET, AETHER_U, AETHER_V, AETHER_W, AETHER_H);
        } else {
            drawTexture(startX + CORNER_INSET + REGULAR_U, startY + CORNER_INSET + REGULAR_V, REGULAR_U, REGULAR_V, REGULAR_W, REGULAR_H);
        }

        int craft_centering_shift = 0;
        if (slotOrder.size() < 1)
            craft_centering_shift -= 18;
        else if (slotOrder.size() < 5)
            craft_centering_shift -= 9;

        if (!extended)
            drawTexture(startX + CRAFT_X + CORNER_INSET + craft_centering_shift, startY + CORNER_INSET, CRAFT_U, CRAFT_V, CRAFT_W, CRAFT_H);

        // blank tile (first inventory slot, bottom left)

        int start = handler.slots.size() - slotOrder.size();
        int end = handler.slots.size();
        int extra = (slotOrder.size() > 8 ? slotOrder.size() - 8 : 0);

        for (int i = start + (!extended ? extra : 0); i < end; i++) {
            Slot slot = (Slot) handler.slots.get(i);
            PreservedSlot info = slotOrder.get(i - start);
            minecraft.textureManager.bindTexture(var2);
            drawTexture(startX + slot.x - 1, startY + slot.y - 1, SLOT_U, SLOT_V, SLOT_W, SLOT_H);
            if (!slot.hasStack()) {
                if (!info.texture.isEmpty()) {
                    int slot_tex = minecraft.textureManager.getTextureId(info.texture);
                    minecraft.textureManager.bindTexture(slot_tex);
                    drawTexture(startX + slot.x, startY + slot.y, info.tx, info.ty, 16, 16);
                }
            }
        }
    }

    @Redirect(method = "drawBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ingame/InventoryScreen;drawTexture(IIIIII)V"))
    public void blitButCaptureWindowPos(InventoryScreen instance, int i, int j, int k, int l, int m, int n) {
        instance.drawTexture(i, j, k, l, m, n);
        wpx = i; // window pos x
    }

    @Redirect(method = "drawBackground", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glTranslatef(FFF)V"))
    public void translateAetherPlayerModel(float x, float y, float z) {
        if (!AccessoryAPI.config.aetherStyleArmor || AccessoryAPI.noSlotsAdded) {
            GL11.glTranslatef(x, y, z);
            return;
        }

        if (x != 0.f) {
            GL11.glTranslatef(x - 18, y - 50, z);
        }
    }

    @Redirect(method = "drawBackground", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/ClientPlayerEntity;yaw:F", opcode = Opcodes.PUTFIELD, ordinal = 0))
    private void injected(ClientPlayerEntity instance, float value) {
        float newValue;
        if (!AccessoryAPI.config.aetherStyleArmor || AccessoryAPI.noSlotsAdded) {
            newValue = value;
        } else {
            float var9 = (float) (wpx + 33) - mouseX;
            newValue = (float) Math.atan(var9 / 40.0F) * 40.0F;
        }
        instance.yaw = newValue;
    }

    @Redirect(method = "drawForeground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;draw(Ljava/lang/String;III)V", ordinal = 0))
    // net.minecraft.client.gui.screen.container.ContainerBase
    public void renderForeground(TextRenderer instance, String i, int j, int k, int color) {

    }
}