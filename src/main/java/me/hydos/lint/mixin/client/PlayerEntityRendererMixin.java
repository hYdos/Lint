package me.hydos.lint.mixin.client;

import me.hydos.lint.client.entity.render.feature.LilTaterShoulderFeatureRenderer;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    public PlayerEntityRendererMixin(EntityRenderDispatcher dispatcher, PlayerEntityModel<AbstractClientPlayerEntity> model, float shadowSize) {
        super(dispatcher, model, shadowSize);
    }

    @Inject(at = @At("TAIL"), method = "<init>(Lnet/minecraft/client/render/entity/EntityRenderDispatcher;Z)V")
    public void addFeatures(EntityRenderDispatcher entityRenderDispatcher, boolean bl, CallbackInfo ci) {
        this.addFeature(new LilTaterShoulderFeatureRenderer<>(this));
    }
}
