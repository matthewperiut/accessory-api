package com.matthewperiut.accessoryapi.impl.slot;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widgets.Button;
import org.lwjgl.opengl.GL11;

public class AccessoryButton extends Button {
    public static long time_clicked = 0;
    public boolean goBack = false;

    public AccessoryButton(int id, int x, int y) {
        super(id, x, y, 10, 10, "");
    }

    @Override
    public void render(Minecraft minecraft, int i, int j) {
        if (visible) {
            GL11.glBindTexture(3553, minecraft.textureManager.getTextureId("assets/accessoryapi/buttons.png"));
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

            boolean hovered = i >= x && j >= y && i < x + width && j < y + height;

            int tpx = hovered ? 30 : 10; // texture position x
            int tpy = goBack ? 10 : 0; // texture position y

            blit(x, y, tpx, tpy, 10, 10);
            postRender(minecraft, i, j);
        }
    }
}
