package com.matthewperiut.accessoryapi.impl.mixin.client;

import com.matthewperiut.accessoryapi.impl.extended.CustomAccessoryStorage;
import net.minecraft.client.gui.screen.container.ContainerBase;
import net.minecraft.entity.player.PlayerContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ContainerBase.class)
public class ContainerBaseMixin
{
    @Shadow
    public net.minecraft.container.ContainerBase container;

    @Unique
    private void resetPlayerInv()
    {
        if (this.container instanceof PlayerContainer pc)
        {
            // crafting result pos ( for aether )
            pc.getSlot(0).x = 134;
            pc.getSlot(0).y = 62;

            int slot = 1; // skip crafting result
            for (int x = 0; x < 2; ++x)
            {
                for (int y = 0; y < 2; ++y)
                {
                    pc.getSlot(slot).x = 125 + (18 * x);
                    pc.getSlot(slot).y = 8 + (18 * y);
                    slot++;
                }
            }

            CustomAccessoryStorage.setCustomSlotsPos(pc, false);
        }
    }

    @Inject(method = "keyPressed", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/AbstractClientPlayer;closeContainer()V"))
    protected void keyPressed(char i, int par2, CallbackInfo ci)
    {
        resetPlayerInv();
    }

    @Inject(method = "onClose", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/BaseClientInteractionManager;closeContainer(ILnet/minecraft/entity/player/PlayerBase;)V"))
    public void onClose(CallbackInfo ci)
    {
        resetPlayerInv();
    }
}
