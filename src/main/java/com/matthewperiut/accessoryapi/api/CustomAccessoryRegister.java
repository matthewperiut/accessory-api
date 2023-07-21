package com.matthewperiut.accessoryapi.api;

import com.matthewperiut.accessoryapi.impl.extended.CustomAccessoryInfo;
import com.matthewperiut.accessoryapi.impl.extended.CustomAccessoryStorage;

public class CustomAccessoryRegister
{
    public static void add(String SlotName, String AccessoryType)
    {
        CustomAccessoryStorage.slotInfo.add(new CustomAccessoryInfo(SlotName, AccessoryType));
    }

    public static void add(String SlotName, String AccessoryType, String Texture, int texPosX, int texPosY)
    {
        CustomAccessoryStorage.slotInfo.add(new CustomAccessoryInfo(SlotName, AccessoryType, Texture, texPosX, texPosY));
    }

    public static void add(String SlotName, String AccessoryType, int h, int v)
    {
        CustomAccessoryStorage.slotInfo.add(new CustomAccessoryInfo(SlotName, AccessoryType, h, v));
    }

    public static void add(String SlotName, String AccessoryType, String Texture, int texPosX, int texPosY, int h, int v)
    {
        CustomAccessoryStorage.slotInfo.add(new CustomAccessoryInfo(SlotName, AccessoryType, Texture, texPosX, texPosY, h, v));
    }
}
