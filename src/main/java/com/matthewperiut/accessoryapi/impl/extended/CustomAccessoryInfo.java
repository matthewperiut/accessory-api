package com.matthewperiut.accessoryapi.impl.extended;

import com.matthewperiut.accessoryapi.api.CustomAccessoryRegister;

public class CustomAccessoryInfo
{
    // Setup Position Variables
    public boolean applyPreferred = false;

    int v;
    int h;

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

    public CustomAccessoryInfo(String SlotName, String AccessoryType, int hp, int vp)
    {
        this(SlotName, AccessoryType);
        applyPreferred = true;
        h = hp;
        v = vp;
    }

    public CustomAccessoryInfo(String SlotName, String AccessoryType, String Texture, int texPosX, int texPosY, int hp, int vp)
    {
        this(SlotName, AccessoryType, Texture, texPosX, texPosY);
        applyPreferred = true;
        h = hp;
        v = vp;
    }
}
