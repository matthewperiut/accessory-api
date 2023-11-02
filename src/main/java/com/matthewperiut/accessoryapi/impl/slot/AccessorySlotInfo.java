package com.matthewperiut.accessoryapi.impl.slot;

public class AccessorySlotInfo {
    // Setup Position Variables
    public boolean applyPreferred = false;
    // Identifiers
    public String type;
    // Rendering
    public String texture = "";
    public int tx;
    public int ty;
    int v;
    int h;

    public AccessorySlotInfo(String accessoryType) {
        type = accessoryType;
    }

    public AccessorySlotInfo(String accessoryType, String texture, int texPosX, int texPosY) {
        this(accessoryType);
        this.texture = texture;
        tx = texPosX;
        ty = texPosY;
    }

    public AccessorySlotInfo(String accessoryType, int hp, int vp) {
        this(accessoryType);
        applyPreferred = true;
        h = hp;
        v = vp;
    }

    public AccessorySlotInfo(String accessoryType, String texture, int texPosX, int texPosY, int hp, int vp) {
        this(accessoryType, texture, texPosX, texPosY);
        applyPreferred = true;
        h = hp;
        v = vp;
    }
}
