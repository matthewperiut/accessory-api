package com.matthewperiut.accessoryapi.api.render;

import net.minecraft.client.render.entity.PlayerRenderer;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;

public interface AccessoryRenderer {
    default void renderThirdPerson(PlayerBase player, PlayerRenderer renderer, ItemInstance itemInstance, double x, double y, double z, float h, float v) {}

    default void renderFirstPerson(PlayerBase player, PlayerRenderer renderer, ItemInstance itemInstance) {}


}
