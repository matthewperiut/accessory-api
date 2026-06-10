package com.periut.testmod.item;

import com.periut.accessoryapi.api.PlayerExtraHP;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class HealthItem extends Item {
    public HealthItem(int id) {
        super(id);
    }

    @Override
    public ItemStack use(ItemStack arg, World arg2, PlayerEntity arg3) {
        System.out.println("wow");
        ((PlayerExtraHP) arg3).setExtraHP(((PlayerExtraHP) arg3).getExtraHP() + 1);
        System.out.println(((PlayerExtraHP) arg3).getExtraHP());
        return super.use(arg, arg2, arg3);
    }
}
