package com.matthewperiut.accessoryapi.api;

import com.matthewperiut.accessoryapi.impl.slot.AccessorySlotInfo;
import com.matthewperiut.accessoryapi.impl.slot.AccessorySlotStorage;

import java.util.HashMap;
import java.util.Map;

public class AccessoryRegister {
    private static final Map<String, Integer> defaultTextureMap = new HashMap<>() {{
        put("pendant", 0);
        put("cape", 1);
        put("shield", 2);
        put("ring", 3);
        put("gloves", 4);
        put("misc", 5);
    }};
    private static final String defaultTexture = "/assets/accessoryapi/inventory.png";
    private static final int DEFAULT_ACCESSORY_Y = 72;

    private static int getDefaultTextureId(final String accessoryType) {
        return defaultTextureMap.getOrDefault(accessoryType, -1);
    }

    public static void requestSlot(String accessoryType, int count) {
        int existingCount = getNumberOfType(accessoryType);
        if (existingCount < count) {
            for (int i = existingCount; i < count; i++) {
                add(accessoryType);
            }
        }
    }

    public static void add(String accessoryType) {
        int d = getDefaultTextureId(accessoryType);
        if (d != -1)
            AccessorySlotStorage.slotInfo.add(new AccessorySlotInfo(accessoryType, defaultTexture, d * 16, DEFAULT_ACCESSORY_Y));
        else
            AccessorySlotStorage.slotInfo.add(new AccessorySlotInfo(accessoryType));
    }

    public static void add(String accessoryType, String texture, int texPosX, int texPosY) {
        AccessorySlotStorage.slotInfo.add(new AccessorySlotInfo(accessoryType, texture, texPosX, texPosY));
    }

    public static void add(String accessoryType, int h, int v) {
        int d = getDefaultTextureId(accessoryType);
        if (d != -1)
            AccessorySlotStorage.slotInfo.add(new AccessorySlotInfo(accessoryType, defaultTexture, d * 16, DEFAULT_ACCESSORY_Y, h, v));
        else
            AccessorySlotStorage.slotInfo.add(new AccessorySlotInfo(accessoryType, h, v));
    }

    public static void add(String accessoryType, String texture, int texPosX, int texPosY, int h, int v) {
        AccessorySlotStorage.slotInfo.add(new AccessorySlotInfo(accessoryType, texture, texPosX, texPosY, h, v));
    }

    public static int getNumberOfType(String accessoryType) {
        int count = 0;
        for (AccessorySlotInfo slot : AccessorySlotStorage.slotInfo) {
            if (slot.type.equals(accessoryType))
                count++;
        }
        return count;
    }
}
