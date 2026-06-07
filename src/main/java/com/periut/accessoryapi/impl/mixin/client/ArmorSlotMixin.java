package com.periut.accessoryapi.impl.mixin.client;

import com.periut.accessoryapi.AccessoryAPI;
import com.periut.accessoryapi.impl.slot.VanillaArmorSlot;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

// the anonymous armour Slot subclass created in PlayerScreenHandler's constructor
@Mixin(targets = "net.minecraft.screen.PlayerScreenHandler$82076943")
public abstract class ArmorSlotMixin extends Slot implements VanillaArmorSlot {
    // armour type: 0 = helmet, 1 = chestplate, 2 = leggings, 3 = boots
    @Shadow
    @Final
    int f_79668884;

    public ArmorSlotMixin(Inventory arg, int i, int j, int k) {
        super(arg, i, j, k);
    }

    @Override
    public int getArmorType() {
        return f_79668884;
    }

    @Override
    public int getBackgroundTextureId() {
        // when accessoryapi isn't modifying the inventory, defer to the vanilla
        // behaviour (or whatever another mod has changed it to)
        if (AccessoryAPI.noSlotsAdded)
            return super.getBackgroundTextureId();
        // any id >= 0 makes HandledScreen.drawSlot draw an empty-slot icon;
        // the texture and uv are redirected to accessoryapi's inventory.png in ContainerBaseMixin
        return f_79668884;
    }
}
