package com.matthewperiut.testmod.accessory;

import com.matthewperiut.accessoryapi.api.helper.AccessoryRenderHelper;
import com.matthewperiut.accessoryapi.api.normal.AccessoryType;
import net.minecraft.client.render.entity.PlayerRenderer;
import net.minecraft.client.render.entity.model.Biped;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import net.modificationstation.stationapi.api.registry.Identifier;

public class TestGloves extends TestAccessory
{
    public TestGloves(Identifier identifier)
    {
        super(identifier);
    }

    @Override
    public AccessoryType[] getAccessoryTypes(ItemInstance item)
    {
        return new AccessoryType[]{AccessoryType.glove};
    }

    @Override
    public void renderWhileWorn(PlayerBase player, PlayerRenderer renderer, ItemInstance accessory, Biped model, Object[] objects)
    {
        AccessoryRenderHelper.ArmOverlay(player, "assets/testmod/textures/armour/test.png", 14540253, model, objects);
    }
}
