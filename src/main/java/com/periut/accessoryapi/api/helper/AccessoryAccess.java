package com.periut.accessoryapi.api.helper;

import com.periut.accessoryapi.api.Accessory;
import com.periut.accessoryapi.api.AccessoryHolder;
import com.periut.accessoryapi.api.AccessoryInventory;
import java.util.ArrayList;
import java.util.Arrays;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class AccessoryAccess {

    /**
     * @param player The player you are checking.
     * @return The player's accessory inventory (the live storage, separate from vanilla armor).
     */
    public static AccessoryInventory getAccessoryInventory(PlayerEntity player) {
        return ((AccessoryHolder) player).getAccessoryInventory();
    }

    /**
     * @param player The player you are checking.
     * @return The full array of the player's accessories (a copy — use the inventory to modify).
     */
    public static ItemStack[] getAccessories(PlayerEntity player) {
        AccessoryInventory inventory = getAccessoryInventory(player);
        ItemStack[] accessories = new ItemStack[inventory.size()];
        for (int i = 0; i < accessories.length; i++) {
            accessories[i] = inventory.getStack(i);
        }
        return accessories;
    }

    /**
     * @param player The player you are checking.
     * @param slot   The index of the accessory inventory you want to check, DO NOT offset for armour slots.
     * @return The accessory in the specified slot.
     */
    public static ItemStack getAccessory(PlayerEntity player, int slot) {
        return getAccessoryInventory(player).getStack(slot);
    }

    /**
     * @param player The player you are giving the accessory to.
     * @param slot   The slot you are placing the accessory in.
     * @param item   The item you would like to place.
     */
    public static void setAccessory(PlayerEntity player, int slot, ItemStack item) {
        getAccessoryInventory(player).setStack(slot, item);
    }

    /**
     * @param player The player you are checking.
     * @param type   The type of accessory you are looking for.
     * @return The array of the player's accessories that match the type.
     */
    public static ItemStack[] getAccessories(PlayerEntity player, String type) {
        var foundItems = new ArrayList<ItemStack>();
        for (ItemStack item : getAccessories(player)) {
            if (item != null && item.getItem() instanceof Accessory accessory) {
                if (Arrays.asList(accessory.getAccessoryTypes(item)).contains(type)) {
                    foundItems.add(item);
                }
            }
        }
        return foundItems.toArray(ItemStack[]::new);
    }

    /**
     * @param player   The player you are checking.
     * @param itemType The item you are looking for.
     * @return Whether the player has any items that match the provided item type.
     */
    public static boolean hasAccessory(PlayerEntity player, Item itemType) {
        for (ItemStack item : getAccessories(player)) {
            if (item != null && item.getItem() == itemType) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param player The player you are checking.
     * @param type   The type of accessory you are looking for.
     * @return Whether the player has any accessories in their inventory that match the type.
     */
    public static boolean hasAnyAccessoriesOfType(PlayerEntity player, String type) {
        for (ItemStack item : getAccessories(player)) {
            if (item != null && item.getItem() instanceof Accessory accessory) {
                if (Arrays.asList(accessory.getAccessoryTypes(item)).contains(type)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Removes the first equipped accessory matching the item type (fires removal callbacks).
     * Replaces old patterns like {@code player.inventory.armor[6] = null}.
     *
     * @param player   The player to remove the accessory from.
     * @param itemType The item to remove.
     * @return Whether an accessory was removed.
     */
    public static boolean removeAccessory(PlayerEntity player, Item itemType) {
        AccessoryInventory inventory = getAccessoryInventory(player);
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack item = inventory.getStack(i);
            if (item != null && item.getItem() == itemType) {
                inventory.setStack(i, null);
                return true;
            }
        }
        return false;
    }

    /**
     * Drops all of the player's accessories at their position, like a death drop.
     * Useful for mods that implement custom death/drop handling.
     *
     * @param player The player whose accessories should drop.
     */
    public static void dropAccessories(PlayerEntity player) {
        getAccessoryInventory(player).dropAll();
    }
}
