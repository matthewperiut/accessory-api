package com.periut.accessoryapi.impl.mixin.server;

import com.periut.accessoryapi.AccessoryAPI;
import com.periut.accessoryapi.impl.slot.AccessorySlotStorage;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerMixin {
    // fixing this initialized garbage in default impl:
    // private ItemStack[] shownItems = new ItemStack[]{null, null, null, null, null};
    // there may be a need to do this better in the future, but I can't think of one.
    @Shadow
    private ItemStack[] equipment = new ItemStack[1 + AccessoryAPI.config.armorOffset + AccessorySlotStorage.slotInfo.size()];

    // unfortunately, this is the one place where we aren't just doing armor.length/size, it's a hard coded 5 signifying:
    // shown items: 1 for held item, next 4 for armor slots
    // so, we're doing 1 + total armor slots
    // the '1' is representing the held item.
    @ModifyConstant(method = "tick()V", constant = @Constant(intValue = 5), require = 1)
    private int modifyShownItemSize(int original) {
        int desired = 1 + AccessoryAPI.config.armorOffset + AccessorySlotStorage.slotInfo.size();
        // Guard: the tick loop both reads and writes this.equipment[i], so we must never
        // iterate past the actual length of that array. The @Shadow initializer above is NOT
        // applied to the target class (Mixin ignores shadow-field initializers), so equipment
        // keeps the vanilla length of 5. If a mod registers a custom slot after that, `desired`
        // would exceed the array and the server tick would crash with an
        // ArrayIndexOutOfBoundsException. Clamp to the real array size to stay in bounds.
        return Math.min(desired, equipment.length);
    }
}
