package com.periut.accessoryapi.impl.slot;

import com.periut.accessoryapi.AccessoryAPI;
import java.util.ArrayList;
import java.util.Collections;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.ChunkPos;

public class AccessorySlotStorage {
    private static final int startX = 62;
    private static final int startY = 8;
    public static ArrayList<AccessorySlotInfo> slotInfo = new ArrayList<>();
    public static ArrayList<PreservedSlot> slotOrder = new ArrayList<>();
    public static int extraSlots = 0;

    public static void initializeCustomAccessoryPositions() {
        // This whole function is a little slow, but it's called once per world run, so it's okay
        slotOrder.clear();

        ArrayList<PreservedSlot> taken = new ArrayList<>();
        ArrayList<AccessorySlotInfo> info = (ArrayList<AccessorySlotInfo>) slotInfo.clone();

        for (AccessorySlotInfo a : slotInfo) {
            if (a.applyPreferred) {
                boolean available = true;
                int h = a.h;
                int v = a.v;
                ChunkPos pos = new ChunkPos(startX + h * 18, startY + v * 18);
                for (PreservedSlot slot : taken) {
                    if (slot.pos.equals(pos)) {
                        available = false;
                        break;
                    }
                }
                boolean secondary = false;
                if (!available) {
                    for (int x = 0; x < 6; x++) {
                        available = true;
                        pos = new ChunkPos(startX + x * 18, startY + v * 18);
                        for (PreservedSlot slot : taken) {
                            if (slot.pos.equals(pos)) {
                                available = false;
                                break;
                            }
                        }

                        if (available) {
                            secondary = true;
                            break;
                        }
                    }
                }

                if (available || secondary) {
                    taken.add(new PreservedSlot(a.type, pos));
                    taken.get(taken.size() - 1).texture = a.texture;
                    taken.get(taken.size() - 1).tx = a.tx;
                    taken.get(taken.size() - 1).ty = a.ty;
                    info.remove(a);
                }
            }
        }

        for (int h = 0; h < 6; h++) {
            for (int v = 0; v < 4; v++) {
                boolean available = true;
                for (PreservedSlot slot : taken) {
                    if (slot.pos.x == (startX + h * 18) && (slot.pos.z == startY + v * 18)) {
                        slotOrder.add(slot);
                        available = false;
                        break;
                    }
                }

                if (available) {
                    if (info.size() > 0) {
                        AccessorySlotInfo accessory = info.get(0);
                        slotOrder.add(new PreservedSlot(accessory.type, new ChunkPos(startX + h * 18, startY + v * 18)));
                        slotOrder.get(slotOrder.size() - 1).texture = accessory.texture;
                        slotOrder.get(slotOrder.size() - 1).tx = accessory.tx;
                        slotOrder.get(slotOrder.size() - 1).ty = accessory.ty;
                        info.remove(0); // Remove the accessory from the list after adding
                    }
                }
            }
        }
        Collections.reverse(slotOrder);

        if (slotOrder.size() > 8)
            extraSlots = slotOrder.size() - 8;
        if (slotOrder.isEmpty())
            AccessoryAPI.noSlotsAdded = true;
    }

    public static void showOverflowSlots(PlayerScreenHandler container) {
        int start = container.slots.size() - slotOrder.size();
        int end = container.slots.size();

        for (int i = start; i < end; i++) {
            Slot slot = (Slot) container.slots.get(i);
            if (slot.x > 114 && slot.y > 500) {
                slot.y -= 1000;
            }
        }
    }

    public static void hideOverflowSlots(PlayerScreenHandler container) {
        int start = container.slots.size() - slotOrder.size();
        int end = container.slots.size();

        for (int i = start; i < end; i++) {
            Slot slot = (Slot) container.slots.get(i);
            if (slot.x > 114 && slot.y < 500) {
                slot.y += 1000;
            }
        }
    }

    public static class PreservedSlot {
        public String slotType;
        public String texture = "";
        public int tx;
        public int ty;
        public ChunkPos pos;

        public PreservedSlot(String slotType, ChunkPos pos) {
            this.slotType = slotType;
            this.pos = pos;
        }
    }
}
