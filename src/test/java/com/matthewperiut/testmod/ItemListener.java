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

    @Entrypoint.ModID
    public static final ModID MOD_ID = Null.get();

    @EventListener
    public void registerItems(ItemRegistryEvent event)
    {
        testCape = new TestCape(Identifier.of(MOD_ID, "test_cape")).setTranslationKey(MOD_ID, "test_cape");
        testGloves = new TestGloves(Identifier.of(MOD_ID, "test_gloves")).setTranslationKey(MOD_ID, "test_gloves");
        testMisc = new TestMisc(Identifier.of(MOD_ID, "test_misc")).setTranslationKey(MOD_ID, "test_misc");
        testPendant = new TestPendant(Identifier.of(MOD_ID, "test_pendant")).setTranslationKey(MOD_ID, "test_pendant");
        testRing = new TestRing(Identifier.of(MOD_ID, "test_ring")).setTranslationKey(MOD_ID, "test_ring");
        testShield = new TestShield(Identifier.of(MOD_ID, "test_shield")).setTranslationKey(MOD_ID, "test_shield");
        testAll = new TestAll(Identifier.of(MOD_ID, "test_all")).setTranslationKey(MOD_ID, "test_all");
    }
}