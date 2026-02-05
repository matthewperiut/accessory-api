package com.periut.accessoryapi.api.render.builtin;

import com.periut.accessoryapi.api.render.AccessoryRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.texture.TextureManager;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public abstract class ConfigurableRenderer implements AccessoryRenderer {
    protected String texture;
    protected Color color = Color.WHITE;

    public ConfigurableRenderer(String texture) {
        super();
        this.texture = texture;
    }

    public ConfigurableRenderer withColor(Color color) {
        this.color = color;
        return this;
    }

    protected void bindTextureAndApplyColors(float brightness) {
        TextureManager textureManager = EntityRenderDispatcher.INSTANCE.textureManager;
        if (textureManager == null) {
            System.err.println("The entity texture manager is currently null");
            return;
        }
        textureManager.bindTexture(textureManager.getTextureId(texture));
        GL11.glColor3f(brightness * color.getRed() / 255f, brightness * color.getGreen() / 255f, brightness * color.getBlue() / 255f);
    }
}
