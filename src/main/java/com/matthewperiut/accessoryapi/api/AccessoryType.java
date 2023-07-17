package com.matthewperiut.accessoryapi.api;

/**
 * Provides different accessory types, must use one if implementing accessory
 */
public enum AccessoryType {
    pendant,
    cape,
    shield,
    /**
     * ring has secondary slot
     */
    glove,
    ring,

    /**
     * misc has secondary slot
     */
    misc
}
