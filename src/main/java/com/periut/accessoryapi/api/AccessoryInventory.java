package com.periut.accessoryapi.api;

import com.periut.accessoryapi.impl.slot.AccessorySlotStorage;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;

/**
 * Per-player accessory storage, separate from the vanilla armor array.
 * <p>
 * Each slot is identified by its accessory type plus an ordinal among slots of the
 * same type (e.g. the second "ring" slot is {@code ("ring", 1)}), so persisted items
 * survive mods being added/removed or slots being registered in a different order —
 * no raw index assumptions.
 */
public class AccessoryInventory implements Inventory {
    private final PlayerEntity owner;
    private final ItemStack[] stacks;
    private final String[] slotTypes;
    private final int[] slotTypeIndices;

    public AccessoryInventory(PlayerEntity owner) {
        AccessorySlotStorage.ensureInitialized();
        this.owner = owner;
        int count = AccessorySlotStorage.slotOrder.size();
        stacks = new ItemStack[count];
        slotTypes = new String[count];
        slotTypeIndices = new int[count];
        Map<String, Integer> seen = new HashMap<>();
        for (int i = 0; i < count; i++) {
            String type = AccessorySlotStorage.slotOrder.get(i).slotType;
            slotTypes[i] = type;
            slotTypeIndices[i] = seen.merge(type, 1, Integer::sum) - 1;
        }
    }

    public PlayerEntity getOwner() {
        return owner;
    }

    /** The accessory type of the given slot (e.g. "ring"). */
    public String getSlotType(int slot) {
        return slotTypes[slot];
    }

    /** The ordinal of this slot among slots of the same type (0 for the first "ring" slot, 1 for the second, ...). */
    public int getSlotTypeIndex(int slot) {
        return slotTypeIndices[slot];
    }

    /** @return the slot index for the given (type, ordinal) pair, or -1 if no such slot is registered. */
    public int getSlotFor(String type, int typeIndex) {
        for (int i = 0; i < stacks.length; i++) {
            if (slotTypes[i].equals(type) && slotTypeIndices[i] == typeIndex) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int size() {
        return stacks.length;
    }

    @Override
    public ItemStack getStack(int slot) {
        return stacks[slot];
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack stack = stacks[slot];
        if (stack == null) {
            return null;
        }
        if (stack.count <= amount) {
            stacks[slot] = null;
            fireRemoved(stack);
            markDirty();
            return stack;
        }
        ItemStack split = stack.split(amount);
        if (stack.count == 0) {
            stacks[slot] = null;
            fireRemoved(stack);
        }
        markDirty();
        return split;
    }

    /** Sets the stack and fires {@link Accessory#onAccessoryRemoved}/{@link Accessory#onAccessoryAdded} callbacks. */
    @Override
    public void setStack(int slot, ItemStack stack) {
        ItemStack old = stacks[slot];
        stacks[slot] = stack;
        if (old != null) {
            fireRemoved(old);
        }
        if (stack != null && stack.getItem() instanceof Accessory accessory) {
            accessory.onAccessoryAdded(owner, stack);
        }
        markDirty();
    }

    /** Sets the stack without firing accessory callbacks (used for ticking replacements and remote-player sync). */
    public void replaceStack(int slot, ItemStack stack) {
        stacks[slot] = stack;
        markDirty();
    }

    /**
     * Equips the stack into the first empty slot whose type the item accepts
     * (per {@link Accessory#getAccessoryTypes}).
     *
     * @return whether a fitting empty slot was found.
     */
    public boolean equipAnywhere(ItemStack stack) {
        if (stack == null || !(stack.getItem() instanceof Accessory accessory)) {
            return false;
        }
        String[] types = accessory.getAccessoryTypes(stack);
        if (types == null) {
            return false;
        }
        for (int i = 0; i < stacks.length; i++) {
            if (stacks[i] != null) {
                continue;
            }
            for (String type : types) {
                if (slotTypes[i].equals(type)) {
                    setStack(i, stack);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Standard recovery chain for items that can no longer go where they were saved:
     * any fitting empty accessory slot, else an empty main-inventory slot, else
     * dropped at the owner's feet — nothing is silently deleted.
     */
    public void equipOrStash(ItemStack stack) {
        if (stack == null) {
            return;
        }
        if (!equipAnywhere(stack) && !owner.inventory.addStack(stack)) {
            owner.dropItem(stack, false);
        }
    }

    /** Drops every accessory at the owner's position (vanilla death-scatter style) and clears the inventory. */
    public void dropAll() {
        for (int i = 0; i < stacks.length; i++) {
            ItemStack stack = stacks[i];
            if (stack != null) {
                stacks[i] = null;
                fireRemoved(stack);
                owner.dropItem(stack, true);
            }
        }
        markDirty();
    }

    private void fireRemoved(ItemStack stack) {
        if (stack.getItem() instanceof Accessory accessory) {
            accessory.onAccessoryRemoved(owner, stack);
        }
    }

    /** Writes non-empty slots as {Type, TypeIndex, id, Damage, Count} compounds. */
    public NbtList writeNbt(NbtList list) {
        for (int i = 0; i < stacks.length; i++) {
            if (stacks[i] != null) {
                NbtCompound entry = new NbtCompound();
                entry.putString("Type", slotTypes[i]);
                entry.putInt("TypeIndex", slotTypeIndices[i]);
                stacks[i].writeNbt(entry);
                list.add(entry);
            }
        }
        return list;
    }

    /**
     * Places saved items back by (type, ordinal). Items whose exact slot no longer
     * exists (a mod was removed, fewer slots of that type) fall back through
     * {@link #equipOrStash}: any other fitting slot, then the main inventory, then
     * a drop at the player's feet — nothing is silently deleted.
     */
    public void readNbt(NbtList list) {
        for (int i = 0; i < list.size(); i++) {
            NbtCompound entry = (NbtCompound) list.get(i);
            ItemStack stack = new ItemStack(entry);
            if (stack.getItem() == null) {
                continue;
            }
            int slot = getSlotFor(entry.getString("Type"), entry.getInt("TypeIndex"));
            if (slot != -1 && stacks[slot] == null) {
                setStack(slot, stack);
            } else {
                equipOrStash(stack);
            }
        }
    }

    @Override
    public String getName() {
        return "Accessories";
    }

    @Override
    public int getMaxCountPerStack() {
        return 1;
    }

    @Override
    public void markDirty() {
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return player == owner;
    }
}
