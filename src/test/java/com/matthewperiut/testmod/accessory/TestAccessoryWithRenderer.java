package com.matthewperiut.testmod.accessory;

import com.matthewperiut.accessoryapi.api.render.AccessoryRenderer;
import com.matthewperiut.accessoryapi.api.render.HasCustomRenderer;
import com.matthewperiut.accessoryapi.api.render.builtin.ConfigurableRenderer;
import net.modificationstation.stationapi.api.registry.Identifier;

public class TestAccessoryWithRenderer extends TestAccessory implements HasCustomRenderer
{
    private final ConfigurableRenderer renderer;

    public TestAccessoryWithRenderer(Identifier identifier, ConfigurableRenderer renderer, String... types)
    {
        super(identifier, types);
        this.renderer = renderer;
    }

    @Override
    public AccessoryRenderer getRenderer()
    {
        return renderer;
    }
}
