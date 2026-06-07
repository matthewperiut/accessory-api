package com.periut.accessoryapi.impl;

import com.periut.accessoryapi.AccessoryAPI;
import com.periut.accessoryapi.api.Accessory;
import com.periut.accessoryapi.api.AccessoryHolder;
import com.periut.accessoryapi.api.AccessoryInventory;
import com.periut.accessoryapi.api.PlayerExtraHP;
import com.periut.accessoryapi.impl.mixin.WorldAccessor;
import com.periut.accessoryapi.impl.mixin.WorldStorageAccessor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.logging.Logger;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtList;
import net.minecraft.world.storage.AlphaWorldStorage;
import net.minecraft.world.storage.WorldStorage;

/**
 * Per-player accessory data, persisted to its own file:
 * {@code <world>/accessories/<playername>.dat}
 * <p>
 * The file holds the player's accessory items (keyed by slot type, not raw index)
 * and their ExtraHP. Because it lives outside the player tag, ExtraHP survives
 * death/respawn, and accessory items can be cleared on death independently of
 * the vanilla inventory.
 */
public final class AccessoryPersistence {
    private static final Logger LOGGER = Logger.getLogger("Minecraft");

    private AccessoryPersistence() {
    }

    /** @return the player's accessory data file, or null when there is no local world storage (e.g. multiplayer client). */
    public static File getFile(PlayerEntity player) {
        if (player == null || player.world == null || player.name == null) {
            return null;
        }
        // World.getWorldStorage() is server-only; read the field via accessor instead
        WorldStorage storage = ((WorldAccessor) player.world).accessoryapi$getStorage();
        if (!(storage instanceof AlphaWorldStorage worldStorage)) {
            return null;
        }
        File worldDir = ((WorldStorageAccessor) worldStorage).accessoryapi$getWorldDirectory();
        return new File(new File(worldDir, "accessories"), player.name + ".dat");
    }

    public static void save(PlayerEntity player) {
        File file = getFile(player);
        if (file == null) {
            return;
        }

        NbtCompound root = new NbtCompound();
        root.putInt("ExtraHP", ((PlayerExtraHP) player).getExtraHP());
        NbtList items = new NbtList();
        ((AccessoryHolder) player).getAccessoryInventory().writeNbt(items);
        root.put("Accessories", (NbtElement) items);

        try {
            file.getParentFile().mkdirs();
            // write-then-rename so a crash mid-write can't corrupt the existing file
            File tmp = new File(file.getParentFile(), "_" + file.getName() + ".tmp");
            NbtIo.writeCompressed(root, new FileOutputStream(tmp));
            if (file.exists()) {
                file.delete();
            }
            tmp.renameTo(file);
        } catch (Exception e) {
            LOGGER.warning("[" + AccessoryAPI.MOD_ID + "] Failed to save accessory data for " + player.name);
        }
    }

    public static void load(PlayerEntity player) {
        File file = getFile(player);
        if (file == null || !file.exists()) {
            return;
        }

        try {
            NbtCompound root = NbtIo.readCompressed(new FileInputStream(file));
            if (root == null) {
                return;
            }
            if (root.contains("ExtraHP")) {
                ((PlayerExtraHP) player).setExtraHP(root.getInt("ExtraHP"));
            }
            ((AccessoryHolder) player).getAccessoryInventory().readNbt(root.getList("Accessories"));
        } catch (Exception e) {
            LOGGER.warning("[" + AccessoryAPI.MOD_ID + "] Failed to load accessory data for " + player.name);
        }
    }

    /**
     * One-time migration from the old format, where accessories were saved as extra
     * vanilla armor slots ({@code Slot >= 100 + armorOffset} in the "Inventory" list).
     * Only runs while the player has no accessory file yet — once saved, the new
     * file is the single source of truth.
     */
    public static void migrateLegacyInventory(PlayerEntity player, NbtList inventoryNbt) {
        if (player == null || player.world == null || player.world.isRemote) {
            return;
        }
        File file = getFile(player);
        if (file == null || file.exists()) {
            return;
        }

        int offset = 100 + AccessoryAPI.config.armorOffset;
        AccessoryInventory accessories = ((AccessoryHolder) player).getAccessoryInventory();
        for (int i = 0; i < inventoryNbt.size(); i++) {
            NbtCompound entry = (NbtCompound) inventoryNbt.get(i);
            int slot = entry.getByte("Slot") & 255;
            if (slot < offset) {
                continue;
            }
            ItemStack stack = new ItemStack(entry);
            if (stack.getItem() == null) {
                continue;
            }
            // old storage was positional: armor[armorOffset + i] <-> accessory slot i.
            // Keep that position when it still exists, is free, and its type still
            // fits the item; otherwise fall back to any fitting slot -> empty main
            // inventory -> drop at the player's feet.
            int target = slot - offset;
            if (target < accessories.size() && accessories.getStack(target) == null
                    && accessoryFits(stack, accessories.getSlotType(target))) {
                accessories.setStack(target, stack);
            } else {
                accessories.equipOrStash(stack);
            }
        }
    }

    private static boolean accessoryFits(ItemStack stack, String slotType) {
        if (!(stack.getItem() instanceof Accessory accessory)) {
            return false;
        }
        String[] types = accessory.getAccessoryTypes(stack);
        if (types == null) {
            return false;
        }
        for (String type : types) {
            if (slotType.equals(type)) {
                return true;
            }
        }
        return false;
    }
}
