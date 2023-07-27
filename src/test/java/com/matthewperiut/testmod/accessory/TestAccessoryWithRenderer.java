package com.matthewperiut.testmod.accessory;

import com.matthewperiut.accessoryapi.api.render.AccessoryRenderer;
import com.matthewperiut.accessoryapi.api.render.HasCustomRenderer;
import com.matthewperiut.accessoryapi.api.render.builtin.CapeRenderer;
import com.matthewperiut.accessoryapi.api.render.builtin.ConfigurableRenderer;
import com.matthewperiut.accessoryapi.api.render.builtin.GloveRenderer;
import com.matthewperiut.accessoryapi.api.render.builtin.NecklaceRenderer;
import net.modificationstation.stationapi.api.registry.Identifier;

import java.util.Optional;

public class TestAccessoryWithRenderer extends TestAccessory implements HasCustomRenderer
{
    protected ConfigurableRenderer renderer;
    private String texture;

    public TestAccessoryWithRenderer(Identifier identifier, String texture, String[] types)
    {
        super(identifier, types);
        this.texture = texture;
    }

    public TestAccessoryWithRenderer(Identifier identifier, String[] types)
    {
        super(identifier, types);
    }

    public void setRenderer(ConfigurableRenderer renderer)
    {
        this.renderer = renderer;
    }

    @Override
    public Optional<AccessoryRenderer> getRenderer()
    {
        return Optional.ofNullable(renderer);
    }

    @Override
    public void constructRenderer()
    {
        if (texture != null && types.length > 0)
        {
            switch (types[0])
            {
                case "cape" -> renderer = new CapeRenderer(texture);
                case "pendant" -> renderer = new NecklaceRenderer(texture);
                case "gloves" -> renderer = new GloveRenderer(texture);
            }
        }
    }
}
