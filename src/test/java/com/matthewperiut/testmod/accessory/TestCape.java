package com.matthewperiut.testmod.accessory;

import com.matthewperiut.accessoryapi.api.helper.AccessoryRenderHelper;
import com.matthewperiut.accessoryapi.api.normal.AccessoryType;
import net.minecraft.client.render.entity.PlayerRenderer;
import net.minecraft.client.render.entity.model.Biped;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import net.modificationstation.stationapi.api.registry.Identifier;

public class TestCape extends TestAccessory
{
    public TestCape(Identifier identifier)
    {
        super(identifier);
    }

    @Override
    public AccessoryType[] getAccessoryTypes(ItemInstance item)
    {
        return new AccessoryType[]{AccessoryType.cape};
    }

    @Override
    public void renderWhileWorn(PlayerBase player, PlayerRenderer renderer, ItemInstance accessory, Biped model, Object[] playerRenderData)
    {
        AccessoryRenderHelper.Cape(player, "assets/testmod/textures/capes/cape.png", model, playerRenderData);
    }
}
