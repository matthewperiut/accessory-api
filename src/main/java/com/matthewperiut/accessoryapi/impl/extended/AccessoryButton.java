package com.matthewperiut.accessoryapi.impl.extended;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widgets.Button;
import org.lwjgl.opengl.GL11;

public class AccessoryButton extends Button
{
    public AccessoryButton(int id, int x, int y)
    {
        super(id, x, y, 10, 10, "");
    }

    @Override
    public void render(Minecraft minecraft, int i, int j)
    {
        if (this.visible)
        {
            GL11.glBindTexture(3553, minecraft.textureManager.getTextureId("assets/accessoryapi/buttons.png"));
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

            boolean hovered = i >= this.x && j >= this.y && i < this.x + this.width && j < this.y + this.height;

            int tpx = hovered ? 30 : 10; // texture position x
            int tpy = 0; // texture position y

            this.blit(this.x, this.y, tpx, tpy, 10, 10);
            this.postRender(minecraft, i, j);
        }
    }
}
