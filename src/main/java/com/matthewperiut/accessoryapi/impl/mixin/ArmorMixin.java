package com.matthewperiut.accessoryapi.impl.mixin;

import com.matthewperiut.accessoryapi.api.TickableInArmorSlot;
import net.minecraft.item.armour.Armour;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Armour.class)
public class ArmorMixin implements TickableInArmorSlot
{
}
