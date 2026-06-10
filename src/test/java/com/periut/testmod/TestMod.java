package com.periut.testmod;

import com.periut.accessoryapi.api.AccessoryRegister;
import com.periut.retroapi.component.RetroComponentType;
import com.periut.retroapi.component.RetroComponents;
import com.periut.testmod.accessory.RainbowAccessory;
import com.periut.testmod.accessory.TestAccessory;
import com.periut.testmod.accessory.TestAccessoryWithRenderer;
import com.periut.testmod.accessory.TestShield;
import com.periut.testmod.item.HealthItem;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.ornithemc.osl.core.api.util.NamespacedIdentifier;
import net.ornithemc.osl.core.api.util.NamespacedIdentifiers;

import static com.periut.retroapi.register.item.RetroItemAccess.allocateId;
import static com.periut.retroapi.register.item.RetroItemAccess.of;

public class TestMod implements ModInitializer {
    public static final String MOD_ID = "testmod";

    public static Item testCape, rainbowCape, testGloves, rainbowGloves, testMisc, testPendant, testRing, testShield, testAll;
    public static Item slime, blueSlime;
    public static Item healthAdder;

    // Per-stack data the StationAPI version kept in getStationNbt().
    public static RetroComponentType<Float> HUE;

    public static NamespacedIdentifier id(String name) {
        return NamespacedIdentifiers.from(MOD_ID, name);
    }

    @Override
    public void onInitialize() {
        AccessoryRegister.requestSlot("pendant", 1);
        AccessoryRegister.requestSlot("cape", 1);
        AccessoryRegister.requestSlot("shield", 1);
        AccessoryRegister.add("misc", 0, 3);
        AccessoryRegister.add("misc", 1, 3);
        AccessoryRegister.requestSlot("ring", 2);
        AccessoryRegister.requestSlot("gloves", 1);

        for (int i = 0; i < 4; i++)
            AccessoryRegister.add("slime", "/assets/testmod/textures/slot/extra.png", 0, 0);

        AccessoryRegister.requestSlot("none", 8);

        HUE = RetroComponents.register(id("hue"), 0f, RetroComponentType.FLOAT);

        testCape = of(new TestAccessoryWithRenderer(allocateId(), "/assets/testmod/textures/capes/cape.png", new String[]{"cape"}))
                .texture(id("testcape")).register(id("test_cape"));
        testGloves = of(new TestAccessoryWithRenderer(allocateId(), "/assets/testmod/textures/armour/test.png", new String[]{"gloves"}))
                .texture(id("testgloves")).register(id("test_gloves"));
        testPendant = of(new TestAccessoryWithRenderer(allocateId(), "/assets/testmod/textures/armour/test.png", new String[]{"pendant"}))
                .texture(id("testpendant")).register(id("test_pendant"));
        rainbowCape = of(new RainbowAccessory(allocateId(), "cape"))
                .texture(id("rainbowcape")).register(id("rainbow_cape"));
        rainbowGloves = of(new RainbowAccessory(allocateId(), "gloves"))
                .texture(id("rainbowgloves")).register(id("rainbow_gloves"));

        testMisc = of(new TestAccessory(allocateId(), "misc"))
                .texture(id("testmisc")).register(id("test_misc"));
        testRing = of(new TestAccessory(allocateId(), "ring"))
                .texture(id("testring")).register(id("test_ring"));
        testShield = of(new TestShield(allocateId()))
                .texture(id("testshield")).register(id("test_shield"));
        testAll = of(new TestAccessory(allocateId(), "all", "ring", "misc", "slime"))
                .texture(id("testall")).register(id("test_all"));

        slime = of(new TestAccessory(allocateId(), "slime"))
                .texture(id("slime")).register(id("test_blob"));
        blueSlime = of(new TestAccessory(allocateId(), "slime"))
                .texture(id("blue_slime")).register(id("test_blue"));

        healthAdder = of(new HealthItem(allocateId()))
                .register(id("health_adder"));
    }
}
