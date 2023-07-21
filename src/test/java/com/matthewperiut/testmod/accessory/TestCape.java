package com.matthewperiut.testmod.accessory;

import com.matthewperiut.accessoryapi.api.helper.AccessoryRenderHelper;
import net.minecraft.client.render.entity.PlayerRenderer;
import net.minecraft.client.render.entity.model.Biped;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.registry.Identifier;

public class TestCape extends TestAccessory
{
    public TestCape(Identifier identifier)
    {
        super(identifier, "cape");
    }

    @Override
    public void inventoryTick(ItemInstance arg, Level arg2, EntityBase arg3, int i, boolean bl) {
        System.out.println("HIIIII");
        super.inventoryTick(arg, arg2, arg3, i, bl);
    }

    @Override
    public void renderWhileWorn(PlayerBase player, PlayerRenderer renderer, ItemInstance accessory, Biped model, Object[] objects)
    {
        AccessoryRenderHelper.cape(player, "assets/testmod/textures/capes/cape.png", model, objects);
    }
}
