package com.matthewperiut.accessoryapi.api;

import com.matthewperiut.accessoryapi.impl.extended.CustomAccessoryInfo;
import com.matthewperiut.accessoryapi.impl.extended.CustomAccessoryStorage;

public class CustomAccessoryRegister
{
    public enum HorizontalPositions
    {Left, Middle, Right}

    public enum VerticalPositions
    {Helmet, Chestplate, Leggings, Boots}

    public static void add(String SlotName, String AccessoryType)
    {
        CustomAccessoryStorage.slotInfo.add(new CustomAccessoryInfo(SlotName, AccessoryType));
    }

    public static void add(String SlotName, String AccessoryType, String Texture, int texPosX, int texPosY)
    {
        CustomAccessoryStorage.slotInfo.add(new CustomAccessoryInfo(SlotName, AccessoryType, Texture, texPosX, texPosY));
    }

    public static void add(String SlotName, String AccessoryType, HorizontalPositions h, VerticalPositions v)
    {
        CustomAccessoryStorage.slotInfo.add(new CustomAccessoryInfo(SlotName, AccessoryType, h, v));
    }

    public static void add(String SlotName, String AccessoryType, String Texture, int texPosX, int texPosY, HorizontalPositions h, VerticalPositions v)
    {
        CustomAccessoryStorage.slotInfo.add(new CustomAccessoryInfo(SlotName, AccessoryType, Texture, texPosX, texPosY, h, v));
    }
}
