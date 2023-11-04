package com.matthewperiut.accessoryapi.impl.mixin;

import com.matthewperiut.accessoryapi.api.BossLivingEntity;
import com.matthewperiut.accessoryapi.api.PlayerExtraHP;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.Living;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Living.class, priority = 900)
abstract public class LivingBaseMixin extends EntityBase implements BossLivingEntity
{
    @Shadow public int field_1009;

    @Shadow public int health;
    @Unique int maxHP = -1;
    @Unique String parsedBossName = "";

    private LivingBaseMixin(Level arg) {
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

    @Override
    public void setBoss(boolean boss)
    {
        // setFlag
        method_1326(6, boss);
        dataTracker.setInt(30, health);
        prevHP = health;
    }

    @Override
    public boolean isBoss()
    {
        // isFlagSet
        return method_1345(6);
    }

    @Override
    public int getHP() {
        return dataTracker.getInt(30);
    }

    @Override
    public int getMaxHP()
    {
        if (maxHP == -1)
        {
            maxHP = health;
        }
        return maxHP;
    }

    @Override
    public String getName()
    {
        if (parsedBossName.isEmpty())
        {
            if (((Object) this) instanceof PlayerBase player) {
                parsedBossName = player.name;
            }
            else {
                String input = getStringId();
                StringBuilder output = new StringBuilder();
                for (int i = 0; i < input.length(); i++)
                {
                    if (Character.isUpperCase(input.charAt(i)) && i != 0) {
                        output.append(" ");
                    }

                    output.append(input.charAt(i));
                }

                parsedBossName = output + " " + getCustomTitle();
            }
        }
        return parsedBossName;
    }

    @Inject(method = "initDataTracker", at = @At("RETURN"))
    protected void initDataTrackerBossHP(CallbackInfo ci)
    {
        dataTracker.startTracking(30, (int) 0);
    }

    @Unique int prevHP = 0;
    @Inject(method = "tick", at = @At("TAIL"))
    protected void updateHP(CallbackInfo ci) {
        if (isBoss()) {
            if (prevHP == 0) {
                prevHP = health;
            } else {
                if (health != prevHP) {
                    dataTracker.setInt(30, health);
                    prevHP = health;
                }
            }
        }
    }
}
