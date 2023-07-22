package com.matthewperiut.testmod;

import com.matthewperiut.accessoryapi.api.render.builtin.CapeRenderer;
import com.matthewperiut.accessoryapi.api.render.builtin.GloveRenderer;
import com.matthewperiut.accessoryapi.api.render.builtin.NecklaceRenderer;
import com.matthewperiut.testmod.accessory.RainbowAccessory;
import com.matthewperiut.testmod.accessory.TestAccessory;
import com.matthewperiut.testmod.accessory.TestAccessoryWithRenderer;
import com.matthewperiut.testmod.accessory.TestShield;
import com.matthewperiut.testmod.client.RainbowCapeRenderer;
import com.matthewperiut.testmod.client.RainbowGloveRenderer;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.ItemBase;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.registry.ModID;
import net.modificationstation.stationapi.api.util.Null;

public class ItemListener
{
    public static ItemBase testCape, rainbowCape, testGloves, rainbowGloves, testMisc, testPendant, testRing, testShield, testAll;
    public static ItemBase slime, blueSlime;

    @Entrypoint.ModID
    public static final ModID MOD_ID = Null.get();

    @EventListener
    public void registerItems(ItemRegistryEvent event)
    {
        testCape = new TestAccessoryWithRenderer(MOD_ID.id("test_cape"), new CapeRenderer("assets/testmod/textures/capes/cape.png"), "cape");
        rainbowCape = new RainbowAccessory(MOD_ID.id("rainbow_cape"), new RainbowCapeRenderer("assets/testmod/textures/capes/cape.png"), "cape");
        testGloves = new TestAccessoryWithRenderer(MOD_ID.id("test_gloves"), new GloveRenderer("assets/testmod/textures/armour/test.png"), "gloves");
        rainbowGloves = new RainbowAccessory(MOD_ID.id("rainbow_gloves"), new RainbowGloveRenderer("assets/testmod/textures/armour/test.png"), "gloves");
        testMisc = new TestAccessory(MOD_ID.id("test_misc"), "misc");
        testPendant = new TestAccessoryWithRenderer(MOD_ID.id("test_pendant"), new NecklaceRenderer("assets/testmod/textures/armour/test.png"), "pendant");
        testRing = new TestAccessory(MOD_ID.id("test_ring"), "ring");

        testShield = new TestShield(MOD_ID.id("test_shield"));
        testAll = new TestAccessory(MOD_ID.id("test_all"), "all", "ring", "misc", "slime");

        slime = new TestAccessory(MOD_ID.id("test_blob"), "slime");
        blueSlime = new TestAccessory(MOD_ID.id("test_blue"), "slime");
    }
}