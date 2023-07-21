package com.matthewperiut.testmod;

import com.matthewperiut.testmod.accessory.*;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.ItemBase;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.registry.ModID;
import net.modificationstation.stationapi.api.util.Null;

public class ItemListener
{
    public static ItemBase testCape, testGloves, testMisc, testPendant, testRing, testShield, testAll;
    public static ItemBase slime, blueSlime;

    @Entrypoint.ModID
    public static final ModID MOD_ID = Null.get();

    @EventListener
    public void registerItems(ItemRegistryEvent event)
    {
        testCape = new TestCape(Identifier.of(MOD_ID, "test_cape"));
        testGloves = new TestGloves(Identifier.of(MOD_ID, "test_gloves"));
        testMisc = new TestAccessory(Identifier.of(MOD_ID, "test_misc"), "misc");
        testPendant = new TestPendant(Identifier.of(MOD_ID, "test_pendant"));
        testRing = new TestAccessory(Identifier.of(MOD_ID, "test_ring"), "ring");
        testShield = new TestShield(Identifier.of(MOD_ID, "test_shield"));
        testAll = new TestAccessory(Identifier.of(MOD_ID, "test_all"), "all");

        slime = new TestAccessory(Identifier.of(MOD_ID, "test_blob"), "slime");
        blueSlime = new TestAccessory(Identifier.of(MOD_ID, "test_blue"), "slime");
    }
}