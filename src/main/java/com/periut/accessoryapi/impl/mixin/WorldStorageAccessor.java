package com.periut.accessoryapi.impl.mixin;

import java.io.File;
import net.minecraft.world.storage.AlphaWorldStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AlphaWorldStorage.class)
public interface WorldStorageAccessor {
    @Invoker("getDirectory")
    File accessoryapi$getWorldDirectory();
}
