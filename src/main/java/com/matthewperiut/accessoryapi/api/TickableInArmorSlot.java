package com.matthewperiut.accessoryapi.api;

import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;

public interface TickableInArmorSlot
{
    default ItemInstance tickWhileWorn(PlayerBase player, ItemInstance itemInstance)
    {
        return itemInstance;
    }
}
