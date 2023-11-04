package com.matthewperiut.accessoryapi.impl.mixin;

import com.matthewperiut.accessoryapi.api.PlayerExtraHP;
import com.matthewperiut.accessoryapi.api.TickableInArmorSlot;
import net.minecraft.entity.Living;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.minecraft.util.io.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerBase.class)
public abstract class PlayerBaseMixin extends Living implements PlayerExtraHP {

    public PlayerBaseMixin(Level arg) {
        super(arg);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void tick(CallbackInfo ci) {
        PlayerBase player = (PlayerBase) ((Object) this);
        for (int i = 0; i < player.inventory.armour.length; i++) {
            ItemInstance item = player.inventory.armour[i];
            if (item != null) {
                var newItem = ((TickableInArmorSlot) item.getType()).tickWhileWorn(player, item);
                if (newItem != item) {
                    player.inventory.armour[i] = newItem;
                }
            }
        }
    }

    @Inject(method = "writeCustomDataToTag", at = @At("HEAD"))
    public void writeCustomDataToTag(CompoundTag tag, CallbackInfo ci) {
        tag.put("ExtraHP", getExtraHP());
    }

    @Inject(method = "readCustomDataFromTag", at = @At("HEAD"))
    public void readCustomDataFromTag(CompoundTag tag, CallbackInfo ci) {

        if (tag.containsKey("ExtraHP")) {
            setExtraHP(tag.getInt("ExtraHP"));
        } else {
            setExtraHP(0);
        }
    }

    @Inject(method = "updateDespawnCounter", at = @At("HEAD"))
    public void updateDespawnCounter(CallbackInfo ci) {
        if (this.level.difficulty == 0 && (this.health >= 20 && this.health < 20 + getExtraHP()) && this.field_1645 % 20 * 12 == 0) {
            this.health += 1;
            this.field_1613 = this.field_1009 / 2;
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
        this.dataTracker.setInt(31, extraHP);
        if (health > 20 + extraHP)
        {
            health = 20 + extraHP;
        }
    }

    public void addExtraHP(int extraHP) {
        setExtraHP(getExtraHP() + extraHP);
    }
}
