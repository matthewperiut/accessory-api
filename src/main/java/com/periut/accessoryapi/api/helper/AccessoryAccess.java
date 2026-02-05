package com.periut.accessoryapi.api.helper;

import com.periut.accessoryapi.AccessoryAPI;
import com.periut.accessoryapi.api.Accessory;
import java.util.ArrayList;
import java.util.Arrays;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class AccessoryAccess {

    /**
     * @param player The player you are checking.
     * @return The full array of the player's accessories.
     */
    public static ItemStack[] getAccessories(PlayerEntity player) {
        return Arrays.copyOfRange(player.inventory.armor, AccessoryAPI.config.armorOffset, player.inventory.armor.length);
    }

    /**
     * @param player The player you are checking.
     * @param slot   The index of the accessory inventory you want to check, DO NOT offset for armour slots.
     * @return The accessory in the specified slot.
     */
    public static ItemStack getAccessory(PlayerEntity player, int slot) {
        return getAccessories(player)[slot];
    }

    /**
     * @param player The player you are giving the accessory to.
     * @param slot   The slot you are placing the accessory in.
     * @param item   The item you would like to place.
     */
    public static void setAccessory(PlayerEntity player, int slot, ItemStack item) {
        player.inventory.armor[slot + AccessoryAPI.config.armorOffset] = item;
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

    /*
         Maybe change this method to use <T extends ItemBase & Accessory>
         for the sake of only checking applicable slots.
    */

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
}
