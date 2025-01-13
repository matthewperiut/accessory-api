package com.matthewperiut.accessoryapi.api;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public interface TickableInArmorSlot {
    default ItemStack tickWhileWorn(PlayerEntity player, ItemStack ItemStack) {
        return ItemStack;
    }
}
