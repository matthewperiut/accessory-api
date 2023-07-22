package com.matthewperiut.accessoryapi.api.helper;

import com.matthewperiut.accessoryapi.api.Accessory;
import com.matthewperiut.accessoryapi.config.AccessoryConfig;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;

import java.util.ArrayList;
import java.util.Arrays;

public class AccessoryAccess
{

    /**
     * @param player The player you are checking.
     * @return The full array of the player's accessories.
     */
    public static ItemInstance[] getAccessories(PlayerBase player)
    {
        return Arrays.copyOfRange(player.inventory.armour, AccessoryConfig.config.armorOffset, player.inventory.armour.length);
    }

    /**
     * @param player The player you are checking.
     * @param slot   The index of the accessory inventory you want to check, DO NOT offset for armour slots.
     * @return The accessory in the specified slot.
     */
    public static ItemInstance getAccessory(PlayerBase player, int slot)
    {
        return getAccessories(player)[slot];
    }

    /**
     * @param player The player you are giving the accessory to.
     * @param slot   The slot you are placing the accessory in.
     * @param item   The item you would like to place.
     */
    public static void setAccessory(PlayerBase player, int slot, ItemInstance item)
    {
        player.inventory.armour[slot + AccessoryConfig.config.armorOffset] = item;
    }

    /**
     * @param player The player you are checking.
     * @param type   The type of accessory you are looking for.
     * @return The array of the player's accessories that match the type.
     */
    public static ItemInstance[] getAccessories(PlayerBase player, String type)
    {
        var foundItems = new ArrayList<ItemInstance>();
        for (ItemInstance item : getAccessories(player))
        {
            if (item != null && item.getType() instanceof Accessory accessory)
            {
                if (Arrays.asList(accessory.getAccessoryTypes(item)).contains(type))
                {
                    foundItems.add(item);
                }
            }
        }
        return foundItems.toArray(ItemInstance[]::new);
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
    private static boolean hasAccessory(PlayerBase player, ItemBase itemType)
    {
        for (ItemInstance item : getAccessories(player))
        {
            if (item != null && item.getType() == itemType)
            {
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
    private static boolean hasAnyAccessoriesOfType(PlayerBase player, String type)
    {
        for (ItemInstance item : getAccessories(player))
        {
            if (item != null && item.getType() instanceof Accessory accessory)
            {
                if (Arrays.asList(accessory.getAccessoryTypes(item)).contains(type))
                {
                    return true;
                }
            }
        }
        return false;
    }
}
