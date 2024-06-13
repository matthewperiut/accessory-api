package com.matthewperiut.accessoryapi.api.render;

import org.jetbrains.annotations.Nullable;

public interface HasCustomRenderer {
    /**
     * Used to provide the accessory renderer variable that you initialized in constructRenderer
     *
     * @return renderer currently being used, null if renderer hasn't been constructed yet.
     * Use AccessoryRenderer.NULL_RENDERER if you want nothing to render, otherwise
     * constructRenderer() will be run every frame.
     */
    @Nullable
    AccessoryRenderer getRenderer();

    /**
     * This is used to initialize the accessory renderer variable to be used in getRenderer
     * Called when getRenderer() returns null.
     */
    void constructRenderer();
}
