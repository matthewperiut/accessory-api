package com.matthewperiut.accessoryapi.api;

import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;

public class AccessoryAccess
{
    public static int getSlot(Accessory.Type type)
    {
        int result = type.ordinal() + 4;
        if (type.ordinal() > 3)
        {
            result += 1;
        }
        return result;
    }

    public static ItemInstance getAccessory(PlayerBase player, Accessory.Type type)
    {
        return getAccessory(player, type, false);
    }

    public static ItemInstance getAccessory(PlayerBase player, Accessory.Type type, boolean secondary)
    {
        int slot = getSlot(type);
        if (secondary)
        {
            slot += 1;
        }
        return player.inventory.armour[slot];
    }

    public static void setAccessory(PlayerBase player, Accessory.Type type, ItemInstance item)
    {
        setAccessory(player, type, item, false);
    }

    public static void setAccessory(PlayerBase player, Accessory.Type type, ItemInstance item, boolean secondary)
    {
        int slot = getSlot(type);
        if (secondary)
        {
            slot += 1;
        }
        player.inventory.armour[slot] = item;
    }

    private static boolean hasThisAccessory(PlayerBase player, ItemBase item, int slot)
    {
        ItemInstance armourItem = player.inventory.getArmourItem(slot);
        if (armourItem == null || armourItem.count < 1)
            return false;
        return armourItem.itemId == item.id;
    }

    /**
     * Checks whether a player has a specific accessory
     *
     * @param player The player that you're checking the accessory from
     * @param type The accessory type you're checking for
     * @param item The accessory you're looking for
     * @return whether the player has the specified accessory
     */
    public static boolean hasThisAccessory(PlayerBase player, ItemBase item, Accessory.Type type)
    {
        if (type.ordinal() == 3 || type.ordinal() == 5)
        {
            return hasThisAccessory(player, item, getSlot(type)) ||
                    hasThisAccessory(player, item, getSlot(type) + 1);
        }
        return hasThisAccessory(player, item, getSlot(type));
    }

    private static boolean hasAccessoryType (PlayerBase player, int slot)
    {
        return player.inventory.getArmourItem(slot) != null;
    }

    /**
     * Checks whether a player has any of a specific accessory type
     *
     * @param player The player that you're checking the accessory from
     * @param type The accessory type you're checking for
     * @return whether the player has the specified accessory type
     */
    public static boolean hasAccessoryType (PlayerBase player, Accessory.Type type)
    {
        if (type.ordinal() == 3 || type.ordinal() == 5)
        {
            return hasAccessoryType(player, getSlot(type)) ||
                    hasAccessoryType(player, getSlot(type) + 1);
        }
        return hasAccessoryType(player, getSlot(type));
    }

    /**
     * Damages an accessory that a player is wearing
     *
     * @param player The player that you're checking the accessory from
     * @param item The accessory you want damaged
     * @param type The accessory type you want damaged
     * @param amount The amount of damage the accessory should take
     * @return whether the item has broken or doesn't exist
     */
    public static boolean damageThisAccessory(PlayerBase player, ItemBase item, Accessory.Type type, int amount)
    {
        int slot = getSlot(type);
        if (type.ordinal() == 3 || type.ordinal() == 5)
        {
            if (hasThisAccessory(player, item, slot))
            {
                player.inventory.armour[slot].applyDamage(amount, player);
                return true;
            }
            else if (hasThisAccessory(player, item, slot + 1))
            {
                player.inventory.armour[slot+1].applyDamage(amount, player);
                return true;
            }
        }

        if (hasThisAccessory(player, item, slot))
        {
            player.inventory.armour[slot+1].applyDamage(amount, player);
            return true;
        }

        return false;
    }
}
