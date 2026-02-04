package com.matthewperiut.testmod.accessory;//package com.matthewperiut.testmod.accessory;
//
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.hit.HitResult;
//import net.minecraft.util.hit.HitResultType;
//import net.minecraft.util.math.Box;
//import net.modificationstation.stationapi.api.util.Identifier;
//import net.modificationstation.stationapi.api.util.math.Vec3f;
//
//import java.util.List;
//
//public class VisiblityTest extends TestAccessory
//{
//    public VisiblityTest(Identifier identifier) {
//        super(identifier);
//    }
//
//    @Override
//    public String[] getAccessoryTypes(ItemStack item)
//    {
//        return new String[]{ "ring" };
//    }
//
//    @Override
//    public ItemStack tickWhileWorn(PlayerEntity player, ItemStack itemStack)
//    {
//        List<Entity> entities = player.world.getEntities(Entity.class, Box.create(player.x - 10, player.y - 10, player.z - 10, player.x + 10, player.y + 10, player.z + 10));
//
//        Vec3f start = Vec3f.from(player.x, player.y, player.z);
//        for (Entity entity : entities)
//        {
//            if (entity.equals(player))
//                continue;
//
//            Vec3f end = Vec3f.from(entity.x, entity.y, entity.z);
//            HitResult hitResult = player.world.raycast(start, end, false);
//            if (hitResult == null)
//            {
//                System.out.println(entity + " sees you 1");
//            }
//            else if (hitResult.type == HitResultType.ENTITY)
//            {
//                System.out.println(entity + " sees you 2");
//            }
//        }
//
//        return itemStack;
//    }
//}
