package com.matthewperiut.accessoryapi.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.PlayerRenderer;
import net.minecraft.client.render.entity.model.Biped;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;

public interface Accessory
{
    /**
     * Determines what accessory slot your item goes to
     *
     * @return Accessory Slot Types array
     */
    AccessoryType[] getAccessoryTypes(ItemInstance item);

    /**
     * Determines what custom accessory slot your item goes to
     *
     * @return Accessory Type String array
     */
    default String[] getCustomAccessoryTypes(ItemInstance item)
    {
        return new String[0];
    }

    default void tickWhileWorn(PlayerBase player, ItemInstance accessory)
    {
    }

    /**
     * Render function for a specific player, providing cuboids or bipeds for use
     *
     * @param player    Player you're rendering
     * @param accessory Specific accessory item instance you're rendering
     * @param model     A provided model (expect to be either empty or filled with previous accessories' model so define it)
     * @param objects   specific data pertaining to what you're rendering, usually contains:
     *                  { x, y, z, h, v } for most accessories, but capes only have { f }
     */
    @Environment(EnvType.CLIENT)
    default void renderWhileWorn(PlayerBase player, PlayerRenderer renderer, ItemInstance accessory, Biped model, final Object[] objects)
    {
    }

    default void onAccessoryAdded(PlayerBase player, ItemInstance accessory)
    {
    }

    default void onAccessoryRemoved(PlayerBase player, ItemInstance accessory)
    {
    }
}