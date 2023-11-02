package com.matthewperiut.testmod.mixin;

import com.matthewperiut.testmod.ItemListener;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerBase.class)
public class PlayerEntityMixin {
    @Inject(method = "<init>", at = @At(value = "TAIL"))
    public void onInit(Level par1, CallbackInfo ci) {
        PlayerBase player = (PlayerBase) (Object) this;
        ItemBase[] items = {ItemListener.testCape, ItemListener.testGloves, ItemListener.testMisc, ItemListener.testPendant, ItemListener.testRing, ItemListener.testShield, ItemListener.testAll, ItemListener.slime, ItemListener.blueSlime, ItemListener.rainbowGloves, ItemListener.rainbowCape, ItemBase.ironChestplate};

        for (int i = 0; i < items.length; i++) {
            player.inventory.setInventoryItem(i, new ItemInstance(items[i], 1));
        }
    }
}
