package com.periut.testmod.item;

import com.periut.accessoryapi.api.PlayerExtraHP;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class HealthItem extends TemplateItem {
    public HealthItem(@NotNull Identifier identifier) {
        super(identifier);
    }

    @Override
    public ItemStack use(ItemStack arg, World arg2, PlayerEntity arg3) {
        System.out.println("wow");
        ((PlayerExtraHP) arg3).setExtraHP(((PlayerExtraHP) arg3).getExtraHP() + 1);
        System.out.println(((PlayerExtraHP) arg3).getExtraHP());
        return super.use(arg, arg2, arg3);
    }
}
