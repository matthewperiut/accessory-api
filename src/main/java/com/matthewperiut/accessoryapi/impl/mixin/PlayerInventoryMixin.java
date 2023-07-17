package com.matthewperiut.accessoryapi.impl.mixin;

import net.minecraft.entity.player.PlayerBase;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerInventory.class)
public class PlayerInventoryMixin
{
    @Shadow
    public ItemInstance[] main = new ItemInstance[36];
    @Shadow
    public ItemInstance[] armour;

    @Inject(method = "<init>", at = @At("TAIL"))
    public void setNewSize(PlayerBase par1, CallbackInfo ci)
    {
        armour = new ItemInstance[12];
    }

    /**
     * @author Matthew Periut
     * @reason add Armor slot loading by increasing armour's size
     */
    /*
    @Overwrite
    public void fromTag(ListTag arg)
    {
        this.main = new ItemInstance[36];
        this.armour = new ItemInstance[12];

        for(int var2 = 0; var2 < arg.size(); ++var2) {
            CompoundTag var3 = (CompoundTag)arg.get(var2);
            int var4 = var3.getByte("Slot") & 255;
            ItemInstance var5 = new ItemInstance(var3);
            if (var5.getType() != null)
            {
                if (var4 < this.main.length)
                {
                    this.main[var4] = var5;
                }

                if (var4 >= 100 && var4 < this.armour.length + 100) {
                    this.armour[var4 - 100] = var5;
                }
            }
        }
    }*/
    @ModifyConstant(
            method = "fromTag",
            constant = @Constant(intValue = 4),
            require = 1
    )
    private int modifyArmourSize(int original)
    {
        return 12;
    }
}
