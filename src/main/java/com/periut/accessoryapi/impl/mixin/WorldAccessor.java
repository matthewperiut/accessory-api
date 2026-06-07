package com.periut.accessoryapi.impl.mixin;

import net.minecraft.world.World;
import net.minecraft.world.storage.WorldStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * World.getWorldStorage() is @Environment(SERVER) and doesn't exist in the client
 * jar, but the underlying storage field is present on both sides — read it directly
 * so singleplayer (client-authoritative) persistence works.
 */
@Mixin(World.class)
public interface WorldAccessor {
    @Accessor("storage")
    WorldStorage accessoryapi$getStorage();
}
