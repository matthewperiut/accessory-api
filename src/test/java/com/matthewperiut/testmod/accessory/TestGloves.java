package com.matthewperiut.testmod.accessory;

import com.matthewperiut.accessoryapi.api.AccessoryType;
import com.matthewperiut.accessoryapi.api.helper.AccessoryRenderHelper;
import net.minecraft.client.render.entity.PlayerRenderer;
import net.minecraft.client.render.entity.model.Biped;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import net.modificationstation.stationapi.api.registry.Identifier;

public class TestGloves extends TestAccessory
{
    String texture;
    int colour;

    public TestGloves(Identifier identifier)
    {
        super(identifier);
        texture = "assets/testmod/textures/armour/test.png";
        colour = 14540253;
    }

    @Override
    public AccessoryType[] getAccessoryTypes(ItemInstance item)
    {
        return new AccessoryType[]{AccessoryType.glove};
    }

    @Override
    public void onAccessoryAdded(PlayerBase player, ItemInstance accessory)
    {
        AccessoryRenderHelper.enableFirstPersonArmOverlayRender(texture, colour);
    }

    @Override
    public void renderWhileWorn(PlayerBase player, PlayerRenderer renderer, ItemInstance accessory, Biped model, Object[] objects)
    {
        AccessoryRenderHelper.ArmOverlay(player, texture, colour, model, objects);
    }

    @Override
    public void onAccessoryRemoved(PlayerBase player, ItemInstance accessory)
    {
        AccessoryRenderHelper.disableFirstPersonArmOverlay();
    }
}
