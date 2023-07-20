package com.matthewperiut.testmod.accessory;

import com.matthewperiut.accessoryapi.api.AccessoryType;
import com.matthewperiut.accessoryapi.api.helper.AccessoryRenderHelper;
import net.minecraft.client.render.entity.PlayerRenderer;
import net.minecraft.client.render.entity.model.Biped;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import net.modificationstation.stationapi.api.registry.Identifier;

public class TestPendant extends TestAccessory
{
    public TestPendant(Identifier identifier)
    {
        super(identifier);
    }

    @Override
    public AccessoryType[] getAccessoryTypes(ItemInstance item)
    {
        return new AccessoryType[]{AccessoryType.pendant};
    }

    @Override
    public void renderWhileWorn(PlayerBase player, PlayerRenderer renderer, ItemInstance accessory, Biped model, Object[] objects)
    {
        AccessoryRenderHelper.TorsoOverlay(player, "assets/testmod/textures/armour/test.png", 14540253, model, objects);
    }
}
