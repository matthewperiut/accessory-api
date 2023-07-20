package com.matthewperiut.accessoryapi.impl.extended;

import net.minecraft.container.slot.Slot;
import net.minecraft.entity.player.PlayerContainer;
import net.minecraft.util.maths.Vec2i;

import java.util.ArrayList;
import java.util.Collections;

public class CustomAccessoryStorage
{
    public static ArrayList<CustomAccessoryInfo> slotInfo = new ArrayList<>();
    public static ArrayList<PreservedSlot> slotOrder = new ArrayList<>();

    public static class PreservedSlot
    {
        public String slotName;
        public String slotType;
        public String texture = "";
        public int tx;
        public int ty;
        public Vec2i pos;

        public PreservedSlot(String slotName, String slotType, Vec2i pos)
        {
            this.slotName = slotName;
            this.slotType = slotType;
            this.pos = pos;
        }
    }

    public static void initializeCustomAccessoryPositions()
    {
        // This whole function is a little slow, but it's called once per world run, so it's okay
        slotOrder.clear();

        ArrayList<PreservedSlot> taken = new ArrayList<>();
        ArrayList<CustomAccessoryInfo> info = (ArrayList<CustomAccessoryInfo>) slotInfo.clone();

        for (CustomAccessoryInfo a : info)
        {
            if (a.applyPreferred)
            {
                boolean available = true;
                int h = a.h.ordinal();
                int v = a.v.ordinal();
                Vec2i pos = new Vec2i(116 + h * 18, 8 + v * 18);
                for (PreservedSlot slot : taken)
                {
                    if (slot.pos.equals(pos))
                    {
                        available = false;
                        break;
                    }
                }
                boolean secondary = false;
                if (!available)
                {
                    for (int x = 0; x < 3; x++)
                    {
                        available = true;
                        pos = new Vec2i(116 + x * 18, 8 + v * 18);
                        for (PreservedSlot slot : taken)
                        {
                            if (slot.pos.equals(pos))
                            {
                                available = false;
                                break;
                            }
                        }

                        if (available)
                        {
                            secondary = true;
                            break;
                        }
                    }
                }

                if (available || secondary)
                {
                    taken.add(new PreservedSlot(a.name, a.type, pos));
                    taken.get(taken.size() - 1).texture = a.texture;
                    taken.get(taken.size() - 1).tx = a.tx;
                    taken.get(taken.size() - 1).ty = a.ty;
                    info.remove(a);
                }
            }
        }

        for (int h = 0; h < 3; h++)
        {
            for (int v = 0; v < 4; v++)
            {
                boolean available = true;
                for (PreservedSlot slot : taken)
                {
                    if (slot.pos.x == (116 + h * 18) && (slot.pos.z == 8 + v * 18))
                    {
                        slotOrder.add(slot);
                        available = false;
                        break;
                    }
                }

                if (available)
                {
                    if (info.size() > 0)
                    {
                        CustomAccessoryInfo accessory = info.get(0);
                        slotOrder.add(new PreservedSlot(accessory.name, accessory.type, new Vec2i(116 + h * 18, 8 + v * 18)));
                        slotOrder.get(slotOrder.size() - 1).texture = accessory.texture;
                        slotOrder.get(slotOrder.size() - 1).tx = accessory.tx;
                        slotOrder.get(slotOrder.size() - 1).ty = accessory.ty;
                        info.remove(0); // Remove the accessory from the list after adding
                    }
                }
            }
        }
        Collections.reverse(slotOrder);
    }

    public static void setCustomSlotsPos(PlayerContainer container, boolean show)
    {
        int start = container.slots.size() - slotOrder.size();
        int end = container.slots.size();

        for (int i = start; i < end; i++)
        {
            Slot slot = (Slot) container.slots.get(i);
            slot.x = slotOrder.get(end - i - 1).pos.x;
            slot.y = slotOrder.get(end - i - 1).pos.z + (show ? 0 : 1000);
        }
    }
}