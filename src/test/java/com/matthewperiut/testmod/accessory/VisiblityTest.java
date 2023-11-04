package com.matthewperiut.testmod.accessory;

import net.minecraft.entity.EntityBase;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitType;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.Vec3f;
import net.modificationstation.stationapi.api.registry.Identifier;

import java.util.List;

public class VisiblityTest extends TestAccessory
{
    public VisiblityTest(Identifier identifier) {
        super(identifier);
    }

    @Override
    public String[] getAccessoryTypes(ItemInstance item)
    {
        return new String[]{ "ring" };
    }

    @Override
    public ItemInstance tickWhileWorn(PlayerBase player, ItemInstance itemInstance)
    {
        List<EntityBase> entities = player.level.getEntities(EntityBase.class, Box.create(player.x - 10, player.y - 10, player.z - 10, player.x + 10, player.y + 10, player.z + 10));

        Vec3f start = Vec3f.from(player.x, player.y, player.z);
        for (EntityBase entity : entities)
        {
            if (entity.equals(player))
                continue;

            Vec3f end = Vec3f.from(entity.x, entity.y, entity.z);
            HitResult hitResult = player.level.method_161(start, end, false);
            if (hitResult == null)
            {
                System.out.println(entity + " sees you 1");
            }
            else if (hitResult.type == HitType.field_790)
            {
                System.out.println(entity + " sees you 2");
            }
        }

        return itemInstance;
    }
}
