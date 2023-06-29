package com.matthewperiut.accessoryapi.impl.mixin;

import com.matthewperiut.accessoryapi.impl.AccessorySlot;
import net.minecraft.container.ContainerBase;
import net.minecraft.container.slot.Slot;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.entity.player.PlayerContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Crafting;
import net.minecraft.item.ItemInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerContainer.class)
public class PlayerContainerMixin extends ContainerBase
{
    @Shadow
    public Crafting craftingInv;

    //public InventoryBase resultInv;
    //public boolean local;
    int slotCounter = 0;
    @ModifyArg(method = "<init>(Lnet/minecraft/entity/player/PlayerInventory;Z)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerContainer;addSlot(Lnet/minecraft/container/slot/Slot;)V"), index = 0)
    private Slot changeTopSlots(Slot par1) {

        if (par1.x == 144) // x of crafting result
        {
            par1.x = 134;
            par1.y = 62;
            slotCounter = 0;
        }

        if (slotCounter > 0 && slotCounter < 5) // 1, 2, 3, 4 are crafting slots
        {
            par1.x += 37;
            par1.y -= 18;
        }

        if (slotCounter > 4 && slotCounter < 9)
        {
            par1.x += 54;
        }



        slotCounter++;
        return par1;
    }

    @Inject(method = "<init>(Lnet/minecraft/entity/player/PlayerInventory;Z)V", at = @At(value = "TAIL"))
    private void addSlots(PlayerInventory arg, boolean par2, CallbackInfo ci)
    {
        int slotnum = 40;

        // labeled slots
        for (int i = 0; i < 2; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (i == 1)
                {
                    if (j == 0 || j == 1)
                    {
                        continue;
                    }
                }
                int type = i * 3 + j;
                if (type > 3)
                {
                    type--;
                }
                int finalType = type;
                this.addSlot(new AccessorySlot(arg, slotnum, 80 + 18 * i, 8 + j * 18, finalType));
                slotnum++;
            }
        }

        // ring slots
        for (int i = 0; i < 2; i++)
        {
            this.addSlot(new AccessorySlot(arg, slotnum, 98, 8 + i * 18, 3));
            slotnum++;
        }

        // misc slots
        for (int i = 0; i < 2; i++)
        {
            this.addSlot(new AccessorySlot(arg, slotnum, 80 + 18 * i, 62, 5));
            slotnum++;
        }
    }

    @Override
    public boolean canUse(PlayerBase arg) {
        return true;
    }
}
