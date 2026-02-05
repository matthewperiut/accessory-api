package com.periut.accessoryapi.impl.mixin;

import com.periut.accessoryapi.api.TickableInArmorSlot;
import net.minecraft.item.ArmorItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ArmorItem.class)
public class ArmorMixin implements TickableInArmorSlot {
}
