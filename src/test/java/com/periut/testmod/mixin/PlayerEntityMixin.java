package com.periut.testmod.mixin;

import com.periut.testmod.ItemListener;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @Inject(method = "<init>", at = @At(value = "TAIL"))
    public void onInit(World par1, CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        Item[] items = {ItemListener.testCape, ItemListener.testGloves, ItemListener.testMisc, ItemListener.testPendant, ItemListener.testRing, ItemListener.testShield, ItemListener.testAll, ItemListener.slime, ItemListener.blueSlime, ItemListener.rainbowGloves, ItemListener.rainbowCape, Item.IRON_CHESTPLATE};

        for (int i = 0; i < items.length; i++) {
            player.inventory.setStack(i, new ItemStack(items[i], 1));
        }
    }
}
