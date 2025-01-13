package com.matthewperiut.accessoryapi.impl.mixin;

import com.matthewperiut.accessoryapi.api.BossLivingEntity;
import com.matthewperiut.accessoryapi.api.PlayerExtraHP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LivingEntity.class, priority = 900)
abstract public class LivingBaseMixin extends Entity implements BossLivingEntity
{
    @Shadow public int maxHealth;

    @Shadow public int health;
    @Unique int maxHP = -1;
    @Unique String parsedBossName = "";

    private LivingBaseMixin(World arg) {
        super(arg);
    }

    @Inject(method = "heal", at = @At("HEAD"), cancellable = true)
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

            this.hearts = this.maxHealth / 2;
        }

        ci.cancel();
    }

    @Override
    public void setBoss(boolean boss)
    {
        // setFlag
        setFlag(6, boss);
        dataTracker.set(30, health);
        prevHP = health;
    }

    @Override
    public boolean isBoss()
    {
        // isFlagSet
        return getFlag(6);
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
            if ((Object) this instanceof PlayerExtraHP extraHP)
            {
                maxHP += extraHP.getExtraHP();
            }
        }
        return maxHP;
    }

    @Override
    public String getName()
    {
        if (parsedBossName.isEmpty())
        {
            if (((Object) this) instanceof PlayerEntity player) {
                parsedBossName = player.name + " ";
            }
            else {
                String input = getRegistryEntry();
                StringBuilder output = new StringBuilder();
                for (int i = 0; i < input.length(); i++)
                {
                    if (Character.isUpperCase(input.charAt(i)) && i != 0) {
                        output.append(" ");
                    }

                    output.append(input.charAt(i));
                }

                parsedBossName = output + " ";
                String customTitle = getCustomTitle();
                if (!customTitle.isEmpty()) {
                    parsedBossName += customTitle + " ";
                }
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
                    dataTracker.set(30, health);
                    prevHP = health;
                }
            }
        }
    }
}
