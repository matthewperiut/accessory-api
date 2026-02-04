package com.matthewperiut.testmod;

import com.matthewperiut.testmod.accessory.RainbowAccessory;
import com.matthewperiut.testmod.accessory.TestAccessory;
import com.matthewperiut.testmod.accessory.TestAccessoryWithRenderer;
import com.matthewperiut.testmod.accessory.TestShield;
import com.matthewperiut.testmod.item.HealthItem;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;

public class ItemListener {
    @Entrypoint.Namespace
    public static Namespace MOD_ID = Null.get();
    public static Item testCape, rainbowCape, testGloves, rainbowGloves, testMisc, testPendant, testRing, testShield, testAll;
    public static Item slime, blueSlime;
    public static Item healthAdder;
    public static Item visibilityTest;

    @EventListener
    public void registerItems(ItemRegistryEvent event) {
        testCape = new TestAccessoryWithRenderer(MOD_ID.id("test_cape"), "assets/testmod/textures/capes/cape.png", new String[]{"cape"});
        testGloves = new TestAccessoryWithRenderer(MOD_ID.id("test_gloves"), "assets/testmod/textures/armour/test.png", new String[]{"gloves"});
        testPendant = new TestAccessoryWithRenderer(MOD_ID.id("test_pendant"), "assets/testmod/textures/armour/test.png", new String[]{"pendant"});
        rainbowCape = new RainbowAccessory(MOD_ID.id("rainbow_cape"), "cape");
        rainbowGloves = new RainbowAccessory(MOD_ID.id("rainbow_gloves"), "gloves");

        testMisc = new TestAccessory(MOD_ID.id("test_misc"), "misc");
        testRing = new TestAccessory(MOD_ID.id("test_ring"), "ring");
        testShield = new TestShield(MOD_ID.id("test_shield"));
        testAll = new TestAccessory(MOD_ID.id("test_all"), "all", "ring", "misc", "slime");

        slime = new TestAccessory(MOD_ID.id("test_blob"), "slime");
        blueSlime = new TestAccessory(MOD_ID.id("test_blue"), "slime");

        healthAdder = new HealthItem(MOD_ID.id("health_adder")).setTranslationKey(MOD_ID, "health_adder");
        //visibilityTest = new VisiblityTest(MOD_ID.id("visibility_test")).setTranslationKey(MOD_ID, "visibility_test");
    }
}