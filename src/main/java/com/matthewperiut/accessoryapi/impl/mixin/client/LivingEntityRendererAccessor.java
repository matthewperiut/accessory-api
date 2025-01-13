package com.matthewperiut.accessoryapi.impl.mixin.client;

import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LivingEntityRenderer.class)
public interface LivingEntityRendererAccessor {
    @Invoker("getHeadBob")
    float invoke828(LivingEntity living, float f);

    @Invoker("getHandSwingProgress")
    float invoke820(LivingEntity living, float f);

    @Invoker("applyTranslation")
    void invoke826(LivingEntity arg, double d, double d1, double d2);

    @Invoker("applyHandSwingRotation")
    void invoke824(LivingEntity arg, float f, float f1, float f2);

    @Invoker("applyScale")
    void invoke823(LivingEntity living, float f);
}
