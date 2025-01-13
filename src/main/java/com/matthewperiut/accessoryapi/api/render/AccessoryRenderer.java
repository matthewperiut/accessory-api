package com.matthewperiut.accessoryapi.api.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.util.ScreenScaler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public interface AccessoryRenderer {
    AccessoryRenderer NULL_RENDERER = new AccessoryRenderer() {
    };

    default void renderThirdPerson(PlayerEntity player, PlayerEntityRenderer renderer, ItemStack itemStack, double x, double y, double z, float h, float v) {
    }

    default void renderFirstPerson(PlayerEntity player, PlayerEntityRenderer renderer, ItemStack itemStack) {
    }

    default void renderHUD(PlayerEntity player, ItemStack itemStack, Minecraft minecraft, ScreenScaler screenScaler, int scaledWidth, int scaledHeight) {
    }
}
