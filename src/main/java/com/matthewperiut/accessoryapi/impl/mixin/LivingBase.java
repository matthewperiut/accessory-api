package com.matthewperiut.accessoryapi.impl.mixin;

import com.matthewperiut.accessoryapi.api.PlayerExtraHP;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.Living;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Living.class, priority = 900)
abstract public class LivingBase extends EntityBase {
    @Shadow public int field_1009;

    @Shadow public int health;

    public LivingBase(Level arg) {
        super(arg);
    }

    @Inject(method = "addHealth", at = @At("HEAD"), cancellable = true)
    public void addHealth(int health, CallbackInfo ci)
    {
        int extraHP = 0;
        if (((Object) this) instanceof PlayerExtraHP playerExtraHP)
        {
            extraHP = playerExtraHP.getExtraHP();
        }

        if (this.health > 0) {
            this.health += health;
            if (this.health > 20 + extraHP) {
                this.health = 20 + extraHP;
            }

            this.field_1613 = this.field_1009 / 2;
        }

        ci.cancel();
    }
}
