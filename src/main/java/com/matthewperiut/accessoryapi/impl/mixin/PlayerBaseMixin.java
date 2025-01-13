package com.matthewperiut.accessoryapi.impl.mixin;

import com.matthewperiut.accessoryapi.api.PlayerExtraHP;
import com.matthewperiut.accessoryapi.api.PlayerVisibility;
import com.matthewperiut.accessoryapi.api.TickableInArmorSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerBaseMixin extends LivingEntity implements PlayerExtraHP, PlayerVisibility
{

    private PlayerBaseMixin(World arg) {
        super(arg);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void tick(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) ((Object) this);
        for (int i = 0; i < player.inventory.armor.length; i++) {
            ItemStack item = player.inventory.armor[i];
            if (item != null) {
                if (item.getItem() instanceof TickableInArmorSlot tickable) {
                    var newItem = tickable.tickWhileWorn(player, item);
                    if (newItem != item) {
                        player.inventory.armor[i] = newItem;
                    }
                }
            }
        }
    }

    @Inject(method = "writeNbt", at = @At("HEAD"))
    public void writeCustomDataToTag(NbtCompound tag, CallbackInfo ci) {
        tag.putInt("ExtraHP", getExtraHP());
    }

    @Inject(method = "readNbt", at = @At("HEAD"))
    public void readCustomDataFromTag(NbtCompound tag, CallbackInfo ci) {

        if (tag.contains("ExtraHP")) {
            setExtraHP(tag.getInt("ExtraHP"));
        } else {
            setExtraHP(0);
        }
    }

    @Inject(method = "tickMovement", at = @At("HEAD"))
    public void updateDespawnCounter(CallbackInfo ci) {
        if (this.world.difficulty == 0 && (this.health >= 20 && this.health < 20 + getExtraHP()) && this.age % 20 * 12 == 0) {
            this.health += 1;
            this.hearts = this.maxHealth / 2;
        }
    }

    @Inject(method = "initDataTracker", at = @At("TAIL"))
    public void initDataTracker(CallbackInfo ci)
    {
        this.dataTracker.startTracking(31, (int)0);
    }

    public int getExtraHP() {
        return this.dataTracker.getInt(31);
    }

    public void setExtraHP(int extraHP) {
        this.dataTracker.set(31, extraHP);
        if (health > 20 + extraHP)
        {
            health = 20 + extraHP;
        }
    }

    public void addExtraHP(int extraHP) {
        setExtraHP(getExtraHP() + extraHP);
    }

    @Override
    public void setInvisible(boolean invisible)
    {
        // setFlag
        setFlag(7, invisible);
    }

    @Override
    public boolean isInvisible()
    {
        // isFlagSet
        return getFlag(7);
    }
}
