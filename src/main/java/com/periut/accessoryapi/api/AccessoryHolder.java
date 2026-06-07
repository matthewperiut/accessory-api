package com.periut.accessoryapi.api;

/**
 * Implemented on every {@link net.minecraft.entity.player.PlayerEntity} via mixin.
 * Cast a player to this interface to reach their accessory inventory:
 * <pre>{@code AccessoryInventory accessories = ((AccessoryHolder) player).getAccessoryInventory();}</pre>
 */
public interface AccessoryHolder {
    AccessoryInventory getAccessoryInventory();
}
