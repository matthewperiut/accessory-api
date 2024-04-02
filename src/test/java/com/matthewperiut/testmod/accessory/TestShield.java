package com.matthewperiut.testmod.accessory;

import net.modificationstation.stationapi.api.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class TestShield extends TestAccessory {
    public TestShield(@NotNull Identifier identifier) {
        super(identifier, "shield");
    }
}
