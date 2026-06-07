package com.periut.accessoryapi.impl.mixin;

import com.periut.accessoryapi.api.AccessoryHolder;
import com.periut.accessoryapi.api.AccessoryInventory;
import com.periut.accessoryapi.api.PlayerExtraHP;
import com.periut.accessoryapi.api.PlayerVisibility;
import com.periut.accessoryapi.api.TickableInArmorSlot;
import com.periut.accessoryapi.impl.AccessoryPersistence;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerBaseMixin extends LivingEntity implements PlayerExtraHP, PlayerVisibility, AccessoryHolder
{
    @Unique
    private AccessoryInventory accessoryapi$accessories;
    @Unique
    private boolean accessoryapi$persistentDataLoaded = false;
    @Unique
    private boolean accessoryapi$nbtApplied = false;

    private PlayerBaseMixin(World arg) {
        super(arg);
    }

    @Override
    public AccessoryInventory getAccessoryInventory() {
        if (accessoryapi$accessories == null) {
            accessoryapi$accessories = new AccessoryInventory((PlayerEntity) (Object) this);
        }
        return accessoryapi$accessories;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void accessoryapi$loadPersistentData(CallbackInfo ci) {
        // Lazy-load on the first tick of every player entity. This single hook covers
        // world load, server join, and the fresh entities created on respawn (both the
        // singleplayer client respawn and PlayerManager.respawnPlayer on servers).
        // On multiplayer clients there is no local storage; contents arrive over the network.
        if (!accessoryapi$persistentDataLoaded) {
            accessoryapi$persistentDataLoaded = true;
            if (!this.world.isRemote) {
                AccessoryPersistence.load((PlayerEntity) (Object) this);
                // Fresh spawn (no NBT was applied to this entity = respawn or first join):
                // vanilla starts the player at 20 health, so fill the extra hearts too.
                // World/server loads go through readNbt and keep their saved health.
                if (!accessoryapi$nbtApplied) {
                    this.health = 20 + getExtraHP();
                }
            }
        }
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

        AccessoryInventory accessories = getAccessoryInventory();
        for (int i = 0; i < accessories.size(); i++) {
            ItemStack item = accessories.getStack(i);
            if (item != null) {
                if (item.getItem() instanceof TickableInArmorSlot tickable) {
                    var newItem = tickable.tickWhileWorn(player, item);
                    if (newItem != item) {
                        accessories.replaceStack(i, newItem);
                    }
                }
            }
        }
    }

    @Inject(method = "onKilledBy", at = @At("TAIL"))
    public void accessoryapi$dropAccessoriesOnDeath(Entity adversary, CallbackInfo ci) {
        // Mirrors inventory.dropInventory() in this method: scatter the accessories too.
        // Note ServerPlayerEntity.onKilledBy does NOT call super — it has its own hook.
        getAccessoryInventory().dropAll();
        if (!this.world.isRemote) {
            // persist immediately: items are gone, ExtraHP stays for the respawned player
            AccessoryPersistence.save((PlayerEntity) (Object) this);
        }
    }

    @Inject(method = "writeNbt", at = @At("HEAD"))
    public void writeCustomDataToTag(NbtCompound tag, CallbackInfo ci) {
        tag.putInt("ExtraHP", getExtraHP());
        // Piggyback on every vanilla player save (level.dat in singleplayer,
        // players/<name>.dat on servers) to write our separate accessory file.
        if (!this.world.isRemote && accessoryapi$persistentDataLoaded) {
            AccessoryPersistence.save((PlayerEntity) (Object) this);
        }
    }

    @Inject(method = "readNbt", at = @At("HEAD"))
    public void readCustomDataFromTag(NbtCompound tag, CallbackInfo ci) {
        // saved data applied -> this is a world load/join, not a fresh respawn
        accessoryapi$nbtApplied = true;
        // Legacy fallback — the accessories file (loaded on first tick) takes precedence.
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
