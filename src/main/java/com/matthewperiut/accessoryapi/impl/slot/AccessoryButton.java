package com.matthewperiut.accessoryapi.impl.slot;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.entity.player.PlayerContainer;
import org.lwjgl.opengl.GL11;

import static com.matthewperiut.accessoryapi.impl.slot.AccessorySlotStorage.hideOverflowSlots;

public class AccessoryButton extends Button
{
    public static long time_clicked = 0;
    public boolean goBack = false;

    public AccessoryButton(int id, int x, int y)
    {
        super(id, x, y, 10, 10, "");
    }

    @Override
    public void render(Minecraft minecraft, int i, int j)
    {
        if (visible)
        {
            GL11.glBindTexture(3553, minecraft.textureManager.getTextureId("assets/accessoryapi/buttons.png"));
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

            boolean hovered = i >= x && j >= y && i < x + width && j < y + height;

            int tpx = hovered ? 30 : 10; // texture position x
            int tpy = goBack ? 10 : 0; // texture position y

            blit(x, y, tpx, tpy, 10, 10);
            postRender(minecraft, i, j);
        }
    }

    public static void resetPlayerInv(net.minecraft.container.ContainerBase container)
    {
        if (container instanceof PlayerContainer pc)
        {
            // crafting result pos ( for aether )
            pc.getSlot(0).x = 134;
            pc.getSlot(0).y = 62;

            int slot = 1; // skip crafting result
            for (int y = 0; y < 2; ++y)
            {
                for (int x = 0; x < 2; ++x)
                {
                    pc.getSlot(slot).x = 125 + (18 * x);
                    pc.getSlot(slot).y = 8 + (18 * y);
                    slot++;
                }
            }

            hideOverflowSlots(pc);
        }
    }
}
