package com.periut.accessoryapi.api;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public interface Accessory extends TickableInArmorSlot {
    /**
     * Determines what accessory slots your item goes to
     *
     * @param item The instance being accessed specifically.
     * @return Accessory Type String array
     */
    String[] getAccessoryTypes(ItemStack item);

    default void onAccessoryAdded(PlayerEntity player, ItemStack accessory) {
    }

    default void onAccessoryRemoved(PlayerEntity player, ItemStack accessory) {
    }
}