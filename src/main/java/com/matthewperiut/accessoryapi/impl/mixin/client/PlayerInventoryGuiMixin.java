package com.matthewperiut.accessoryapi.impl.mixin.client;

import com.matthewperiut.accessoryapi.impl.AccessoryMod;
import com.matthewperiut.accessoryapi.impl.slot.AccessoryButton;
import com.matthewperiut.accessoryapi.impl.slot.AccessorySlotStorage;
import net.minecraft.client.gui.screen.container.ContainerBase;
import net.minecraft.client.gui.screen.container.PlayerInventory;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.container.slot.Slot;
import net.minecraft.entity.player.AbstractClientPlayer;
import net.minecraft.entity.player.PlayerContainer;
import org.lwjgl.opengl.GL11;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.matthewperiut.accessoryapi.impl.slot.AccessorySlotStorage.showOverflowSlots;
import static com.matthewperiut.accessoryapi.impl.slot.AccessorySlotStorage.slotOrder;

@Mixin(value = PlayerInventory.class)
public abstract class PlayerInventoryGuiMixin extends ContainerBase
{
    @Unique
    boolean extended = false;

    @Shadow
    private float mouseX;

    public PlayerInventoryGuiMixin(net.minecraft.container.ContainerBase arg)
    {
        super(arg);
    }

    @Inject(method = "init", at = @At("TAIL"))
    public void init(CallbackInfo ci)
    {
        int startX = (this.width - this.containerWidth) / 2;
        int startY = (this.height - this.containerHeight) / 2;

        if (slotOrder.size() > 8) this.buttons.add(new AccessoryButton(250, startX + 115, startY + 6));
    }

    @Inject(method = "buttonClicked", at = @At("TAIL"))
    protected void buttonClicked(Button button, CallbackInfo ci)
    {
        if (button.id == 250)
        {
            extended = true;
            button.visible = false;
            for (int i = 0; i < 5; i++)
            {
                ((Slot) container.slots.get(i)).y = 1000;
            }

            showOverflowSlots((PlayerContainer) this.container);
        }
    }

    @Inject(method = "renderContainerBackground", at = @At("TAIL"))
    public void bindAetherPlayerGuiTexture(float par1, CallbackInfo ci)
    {
        int var2 = this.minecraft.textureManager.getTextureId("/assets/accessoryapi/inventory.png");
        this.minecraft.textureManager.bindTexture(var2);

        int texOffsetX = 7, texOffsetY = 7;
        int topSizeX = 154, topSizeY = 72;

        if (AccessoryMod.leftArmourSlots)
        {
            texOffsetX += 72;
            topSizeX -= 72;
        }

        int startX = (this.width - this.containerWidth) / 2;
        int startY = (this.height - this.containerHeight) / 2;
        this.blit(startX + texOffsetX, startY + texOffsetY, texOffsetX, texOffsetY, topSizeX, topSizeY);

        if (extended) this.blit(startX + 117, startY, 176, 0, 59, 79);

        // todo: maybe make this performant?
        //this.minecraft.player.inventory.armour[]

        // blank tile (first inventory slot, bottom left)
        int blankX = 176, blankY = 79;

        int start = container.slots.size() - slotOrder.size();
        int end = container.slots.size();
        int extra = (slotOrder.size() > 8 ? slotOrder.size() - 8 : 0);

        for (int i = start + (!extended ? extra : 0); i < end; i++)
        {
            Slot slot = (Slot) container.slots.get(i);
            AccessorySlotStorage.PreservedSlot info = slotOrder.get(i - start);
            this.minecraft.textureManager.bindTexture(var2);
            this.blit(startX + slot.x - 1, startY + slot.y - 1, blankX, blankY, 18, 18);
            if (!slot.hasItem())
            {
                int slot_tex = this.minecraft.textureManager.getTextureId(info.texture);
                this.minecraft.textureManager.bindTexture(slot_tex);
                this.blit(startX + slot.x, startY + slot.y, info.tx, info.ty, 16, 16);
            }
        }
    }

    @Unique
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
        if (AccessoryMod.leftArmourSlots)
        {
            GL11.glTranslatef(x, y, z);
            return;
        }

        if (x != 0.f)
        {
            GL11.glTranslatef(x - 18, y - 50, z);
        }
    }

    @Redirect(method = "renderContainerBackground", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/AbstractClientPlayer;yaw:F", opcode = Opcodes.PUTFIELD, ordinal = 0))
    private void injected(AbstractClientPlayer instance, float value)
    {
        float newValue;
        if (AccessoryMod.leftArmourSlots)
        {
            newValue = value;
        }
        else
        {
            float var9 = (float) (wpx + 33) - this.mouseX;
            newValue = (float) Math.atan(var9 / 40.0F) * 40.0F;
        }
        instance.yaw = newValue;
    }

    @Redirect(method = "renderForeground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/TextRenderer;drawText(Ljava/lang/String;III)V", ordinal = 0))
    // net.minecraft.client.gui.screen.container.ContainerBase
    public void renderForeground(TextRenderer instance, String i, int j, int k, int color)
    {

    }
}