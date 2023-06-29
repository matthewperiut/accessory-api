package com.matthewperiut.accessoryapi.impl;

import net.fabricmc.api.ModInitializer;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.HashMap;

public class AccessoryEntry implements ModInitializer
{
    public static final String MODID = "accessoryapi";
    public static final Logger LOGGER = (Logger) LoggerFactory.getLogger(MODID);

    public static HashMap<String, PlayerInfo> PlayersAccessoriesModels = new HashMap<>();
    @Override
    public void onInitialize()
    {

    }
}