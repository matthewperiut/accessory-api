package com.matthewperiut.testmod;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.texture.TextureRegisterEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.registry.ModID;
import net.modificationstation.stationapi.api.util.Null;

public class TextureListener
{
    @Entrypoint.ModID
    public static final ModID MOD_ID = Null.get();

    @EventListener
    public void registerTextures(TextureRegisterEvent event)
    {
        ItemListener.testCape.setTexture(Identifier.of(MOD_ID, "item/testcape"));
        ItemListener.testGloves.setTexture(Identifier.of(MOD_ID, "item/testgloves"));
        ItemListener.testMisc.setTexture(Identifier.of(MOD_ID, "item/testmisc"));
        ItemListener.testPendant.setTexture(Identifier.of(MOD_ID, "item/testpendant"));
        ItemListener.testRing.setTexture(Identifier.of(MOD_ID, "item/testring"));
        ItemListener.testShield.setTexture(Identifier.of(MOD_ID, "item/testshield"));
        ItemListener.testAll.setTexture(Identifier.of(MOD_ID, "item/testall"));

        ItemListener.slime.setTexture(Identifier.of(MOD_ID, "item/slime"));
        ItemListener.blueSlime.setTexture(Identifier.of(MOD_ID, "item/blue_slime"));
    }
}