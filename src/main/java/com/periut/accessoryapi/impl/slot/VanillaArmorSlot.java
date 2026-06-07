package com.periut.accessoryapi.impl.slot;

/**
 * Implemented (via mixin) by the vanilla armour slots in the player inventory,
 * so the empty-slot armour icons can be drawn through the native
 * {@code Slot.getBackgroundTextureId()} mechanism.
 */
public interface VanillaArmorSlot {
    /**
     * @return 0 = helmet, 1 = chestplate, 2 = leggings, 3 = boots
     */
    int getArmorType();
}
