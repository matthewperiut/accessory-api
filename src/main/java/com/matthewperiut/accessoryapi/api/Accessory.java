package com.matthewperiut.accessoryapi.api;

import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;

public interface Accessory extends TickableInArmorSlot {
    /**
     * Determines what accessory slots your item goes to
     *
     * @param item The instance being accessed specifically.
     * @return Accessory Type String array
     */
    String[] getAccessoryTypes(ItemInstance item);

    default void onAccessoryAdded(PlayerBase player, ItemInstance accessory) {
    }

    default void onAccessoryRemoved(PlayerBase player, ItemInstance accessory) {
    }
}