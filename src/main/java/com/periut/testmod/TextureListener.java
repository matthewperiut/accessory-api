package com.periut.testmod;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.texture.TextureRegisterEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;

public class TextureListener {
    @Entrypoint.Namespace
    public static Namespace MOD_ID = Null.get();

    @EventListener
    public void registerTextures(TextureRegisterEvent event) {
        ItemListener.testCape.setTexture(MOD_ID.id("item/testcape"));
        ItemListener.rainbowCape.setTexture(MOD_ID.id("item/rainbowcape"));
        ItemListener.testGloves.setTexture(MOD_ID.id("item/testgloves"));
        ItemListener.rainbowGloves.setTexture(MOD_ID.id("item/rainbowgloves"));
        ItemListener.testMisc.setTexture(MOD_ID.id("item/testmisc"));
        ItemListener.testPendant.setTexture(MOD_ID.id("item/testpendant"));
        ItemListener.testRing.setTexture(MOD_ID.id("item/testring"));
        ItemListener.testShield.setTexture(MOD_ID.id("item/testshield"));
        ItemListener.testAll.setTexture(MOD_ID.id("item/testall"));

        ItemListener.slime.setTexture(MOD_ID.id("item/slime"));
        ItemListener.blueSlime.setTexture(MOD_ID.id("item/blue_slime"));
    }
}