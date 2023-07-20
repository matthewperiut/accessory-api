package com.matthewperiut.accessoryapi.impl;

import com.matthewperiut.accessoryapi.impl.extended.CustomAccessoryInfo;
import com.matthewperiut.accessoryapi.impl.extended.CustomAccessoryStorage;
import net.minecraft.client.render.entity.model.Biped;

import java.util.ArrayList;
import java.util.HashSet;

public class PlayerInfo
{
    public Biped shield = new Biped(1.25f);

    public Biped gloveAndPendant = new Biped(0.6f);

    public Biped misc1 = new Biped(0.6f);

    public Biped misc2 = new Biped(0.6f);

    public ArrayList<Biped> custom_models = new ArrayList<>();

    public static ArrayList<String> custom_accessory_types;
    public static int custom_model_num = CalculateModelNum();

    private static int CalculateModelNum()
    {
        HashSet<String> custom_accessory_types_no_duplicate = new HashSet<>();
        for (CustomAccessoryInfo info : CustomAccessoryStorage.slotInfo)
        {
            custom_accessory_types_no_duplicate.add(info.type);
        }
        custom_accessory_types = new ArrayList<String>(custom_accessory_types_no_duplicate);
        return custom_accessory_types.size();
    }

    public PlayerInfo()
    {
        // overlay models
        for (int i = 0; i < custom_model_num; i++)
        {
            custom_models.add(new Biped(0.6f));
        }
    }
}
