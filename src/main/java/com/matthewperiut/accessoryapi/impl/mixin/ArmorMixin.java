package com.matthewperiut.accessoryapi.impl.mixin;

import com.matthewperiut.accessoryapi.api.TickableInArmorSlot;
import net.minecraft.item.ArmorItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ArmorItem.class)
public class ArmorMixin implements TickableInArmorSlot {
}
