package com.matthewperiut.accessoryapi.config;

import blue.endless.jankson.JsonElement;
import blue.endless.jankson.JsonPrimitive;
import com.google.common.collect.ImmutableMap;
import net.glasslauncher.mods.api.gcapi.api.ConfigFactoryProvider;
import net.glasslauncher.mods.api.gcapi.api.MaxLength;
import net.glasslauncher.mods.api.gcapi.impl.config.ConfigEntry;
import uk.co.benjiweber.expressions.function.OctFunction;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.function.Function;

public class AccessoryFactoryProvider implements ConfigFactoryProvider
{

    public void provideLoadFactories(ImmutableMap.Builder<Type, OctFunction<String, String, String, Field, Object, Boolean, Object, MaxLength, ConfigEntry<?>>> builder) {
//        builder.put(Style.class, (id, name, description, parentField, parentObject, isMultiplayerSynced, value, maxLength) ->
//                new StyleConfigEntry(id, name, description, parentField, parentObject, isMultiplayerSynced, (Style) value, maxLength));
    }

    public void provideSaveFactories(ImmutableMap.Builder<Type, Function<Object, JsonElement>> builder) {
//        builder.put(Style.class, (object) -> JsonPrimitive.of(((Style) object).name()));
    }
}
