package com.periut.testmod.accessory;

import com.periut.accessoryapi.api.render.AccessoryRenderer;
import com.periut.accessoryapi.api.render.HasCustomRenderer;
import com.periut.accessoryapi.api.render.builtin.CapeRenderer;
import com.periut.accessoryapi.api.render.builtin.ConfigurableRenderer;
import com.periut.accessoryapi.api.render.builtin.GloveRenderer;
import com.periut.accessoryapi.api.render.builtin.NecklaceRenderer;
import org.jetbrains.annotations.Nullable;

public class TestAccessoryWithRenderer extends TestAccessory implements HasCustomRenderer {
    protected ConfigurableRenderer renderer;
    private String texture;

    public TestAccessoryWithRenderer(int id, String texture, String[] types) {
        super(id, types);
        this.texture = texture;
    }

    public TestAccessoryWithRenderer(int id, String[] types) {
        super(id, types);
    }

    @Override
    public @Nullable AccessoryRenderer getRenderer() {
        return renderer;
    }

    public void setRenderer(ConfigurableRenderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void constructRenderer() {
        if (texture != null && types.length > 0) {
            switch (types[0]) {
                case "cape" -> renderer = new CapeRenderer(texture);
                case "pendant" -> renderer = new NecklaceRenderer(texture);
                case "gloves" -> renderer = new GloveRenderer(texture);
            }
        }
    }
}
