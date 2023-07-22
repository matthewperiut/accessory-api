package com.matthewperiut.accessoryapi.impl.mixin;

import com.matthewperiut.accessoryapi.impl.AccessoryMod;
import com.matthewperiut.accessoryapi.impl.slot.AccessorySlot;
import com.matthewperiut.accessoryapi.impl.slot.AccessorySlotStorage;
import net.minecraft.container.ContainerBase;
import net.minecraft.container.slot.Slot;
import net.minecraft.entity.player.PlayerContainer;
import net.minecraft.entity.player.PlayerInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.matthewperiut.accessoryapi.impl.slot.AccessorySlotStorage.hideOverflowSlots;
import static com.matthewperiut.accessoryapi.impl.slot.AccessorySlotStorage.slotOrder;

@Mixin(PlayerContainer.class)
public abstract class PlayerContainerMixin extends ContainerBase
{
    @Unique
    int slotCounter = 0;

    @ModifyArg(method = "<init>(Lnet/minecraft/entity/player/PlayerInventory;Z)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerContainer;addSlot(Lnet/minecraft/container/slot/Slot;)V"), index = 0)
    private Slot changeTopSlots(Slot par1)
    {

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

        if (!AccessoryMod.leftArmourSlots)
        {
            if (slotCounter > 4 && slotCounter < 9) // armour
            {
                par1.x += 54;
            }
        }

        slotCounter++;
        return par1;
    }

    @Inject(method = "<init>(Lnet/minecraft/entity/player/PlayerInventory;Z)V", at = @At(value = "TAIL"))
    private void addSlots(PlayerInventory inv, boolean par2, CallbackInfo ci)
    {
        int slotnum = 40;

        AccessorySlotStorage.initializeCustomAccessoryPositions();

        for (int i = 0; i < slotOrder.size(); i++)
        {
            AccessorySlotStorage.PreservedSlot slot = slotOrder.get(i);
            addSlot(new AccessorySlot(inv, slotnum, slot.pos.x + 18, slot.pos.z, slot.slotType));
            slotnum++;
        }

        hideOverflowSlots((PlayerContainer) (Object) this);
    }
}
