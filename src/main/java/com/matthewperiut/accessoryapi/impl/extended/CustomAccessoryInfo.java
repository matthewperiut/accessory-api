package com.matthewperiut.accessoryapi.impl.extended;

import com.matthewperiut.accessoryapi.api.CustomAccessoryRegister;

public class CustomAccessoryInfo
{
    // Setup Position Variables
    public boolean applyPreferred = false;

    CustomAccessoryRegister.VerticalPositions v;
    CustomAccessoryRegister.HorizontalPositions h;

    // Identifiers
    public String name;
    public String type;

    // Rendering
    public String texture = "";
    public int tx;
    public int ty;

    public CustomAccessoryInfo(String SlotName, String AccessoryType)
    {
        name = SlotName;
        type = AccessoryType;
    }

    public CustomAccessoryInfo(String SlotName, String AccessoryType, String Texture, int texPosX, int texPosY)
    {
        this(SlotName, AccessoryType);
        texture = Texture;
        tx = texPosX;
        ty = texPosY;
    }

    public CustomAccessoryInfo(String SlotName, String AccessoryType, CustomAccessoryRegister.HorizontalPositions hp, CustomAccessoryRegister.VerticalPositions vp)
    {
        this(SlotName, AccessoryType);
        applyPreferred = true;
        h = hp;
        v = vp;
    }

    public CustomAccessoryInfo(String SlotName, String AccessoryType, String Texture, int texPosX, int texPosY, CustomAccessoryRegister.HorizontalPositions hp, CustomAccessoryRegister.VerticalPositions vp)
    {
        this(SlotName, AccessoryType, Texture, texPosX, texPosY);
        applyPreferred = true;
        h = hp;
        v = vp;
    }
}
