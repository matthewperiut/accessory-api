package com.matthewperiut.accessoryapi.impl.mixin;

import com.matthewperiut.accessoryapi.api.AccessoryType;
import com.matthewperiut.accessoryapi.impl.AccessorySlot;
import com.matthewperiut.accessoryapi.impl.extended.CustomAccessoryStorage;
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

import static com.matthewperiut.accessoryapi.impl.extended.CustomAccessoryStorage.setCustomSlotsPos;
import static com.matthewperiut.accessoryapi.impl.extended.CustomAccessoryStorage.slotOrder;

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

        if (slotCounter > 4 && slotCounter < 9) // armour
        {
            par1.x += 54;
        }


        slotCounter++;
        return par1;
    }

    @Inject(method = "<init>(Lnet/minecraft/entity/player/PlayerInventory;Z)V", at = @At(value = "TAIL"))
    private void addSlots(PlayerInventory inv, boolean par2, CallbackInfo ci)
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

                this.addSlot(new AccessorySlot(inv, slotnum, 80 + 18 * i, 8 + j * 18, AccessoryType.values()[slotnum - 40]));
                slotnum++;
            }
        }

        // ring slots
        for (int i = 0; i < 2; i++)
        {
            this.addSlot(new AccessorySlot(inv, slotnum, 98, 8 + i * 18, AccessoryType.ring));
            slotnum++;
        }

        // misc slots
        for (int i = 0; i < 2; i++)
        {
            this.addSlot(new AccessorySlot(inv, slotnum, 80 + 18 * i, 62, AccessoryType.misc));
            slotnum++;
        }

        // custom slots
        CustomAccessoryStorage.initializeCustomAccessoryPositions();

        for (int i = slotOrder.size()-1; i > -1; i--)
        {
            CustomAccessoryStorage.PreservedSlot slot = slotOrder.get(i);
            System.out.println(slotnum + " " + slot.pos.x + " " + slot.pos.z + " " + slot.slotType);
            this.addSlot(new AccessorySlot(inv, slotnum, slot.pos.x, slot.pos.z, slot.slotType));
            slotnum++;
        }

        setCustomSlotsPos((PlayerContainer) (Object) this, false);
    }
}
