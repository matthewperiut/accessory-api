package com.matthewperiut.accessoryapi.impl.mixin.client;

import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.Living;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LivingEntityRenderer.class)
public interface LivingEntityRendererAccessor
{
    @Invoker("method_828")
    float invoke828(Living living, float f);

    @Invoker("method_820")
    float invoke820(Living living, float f);

    @Invoker("method_826")
    void invoke826(Living arg, double d, double d1, double d2);

    @Invoker("method_824")
    void invoke824(Living arg, float f, float f1, float f2);

    @Invoker("method_823")
    void invoke823(Living living, float f);
}
