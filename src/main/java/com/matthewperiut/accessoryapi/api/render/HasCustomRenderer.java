package com.matthewperiut.accessoryapi.api.render;

import java.util.Optional;

public interface HasCustomRenderer {
    /**
     * Used to provide the accessory renderer variable that you initialized in constructRenderer
     *
     * @return renderer currently being used
     */
    Optional<AccessoryRenderer> getRenderer();

    /**
     * This is used to initialize the accessory renderer variable to be used in getRenderer
     * Called when getRenderer() returns null.
     */
    void constructRenderer();
}
