package com.matthewperiut.testmod.item;

import com.matthewperiut.accessoryapi.api.PlayerExtraHP;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.item.TemplateItemBase;

public class HealthItem extends TemplateItemBase {
    public HealthItem(Identifier identifier) {
        super(identifier);
    }

    @Override
    public ItemInstance use(ItemInstance arg, Level arg2, PlayerBase arg3) {
        System.out.println("wow");
        ((PlayerExtraHP) arg3).setExtraHP(((PlayerExtraHP) arg3).getExtraHP() + 1);
        System.out.println(((PlayerExtraHP) arg3).getExtraHP());
        return super.use(arg, arg2, arg3);
    }
}
