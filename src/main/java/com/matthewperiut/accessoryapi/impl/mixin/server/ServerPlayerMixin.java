package com.matthewperiut.accessoryapi.impl.mixin.server;

import com.matthewperiut.accessoryapi.AccessoryAPI;
import com.matthewperiut.accessoryapi.impl.slot.AccessorySlotStorage;
import net.minecraft.entity.player.ServerPlayer;
import net.minecraft.item.ItemInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {
    // fixing this initialized garbage in default impl:
    // private ItemInstance[] shownItems = new ItemInstance[]{null, null, null, null, null};
    // there may be a need to do this better in the future, but I can't think of one.
    @Shadow
    private ItemInstance[] shownItems = new ItemInstance[1 + AccessoryAPI.config.armorOffset + AccessorySlotStorage.slotInfo.size()];

    // unfortunately, this is the one place where we aren't just doing armor.length/size, it's a hard coded 5 signifying:
    // shown items: 1 for held item, next 4 for armor slots
    // so, we're doing 1 + total armor slots
    // the '1' is representing the held item.
    @ModifyConstant(method = "tick()V", constant = @Constant(intValue = 5), require = 1)
    private int modifyShownItemSize(int original) {
        return 1 + AccessoryAPI.config.armorOffset + AccessorySlotStorage.slotInfo.size();
    }
}
