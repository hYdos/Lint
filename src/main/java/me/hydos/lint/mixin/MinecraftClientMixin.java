package me.hydos.lint.mixin;

import me.hydos.lint.core.Entities;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.MusicTracker;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Shadow
    public ClientWorld world;

    @Shadow
    public Entity cameraEntity;

    @Inject(method = "getMusicType", at = @At("HEAD"), cancellable = true)
    private void getMusicType(CallbackInfoReturnable<MusicTracker.MusicType> callbackInfoReturnable) {
        if (world != null && cameraEntity != null) {
            if (!world.getEntities(Entities.KING_TATER, cameraEntity.getBoundingBox().expand(40), $ -> true).isEmpty()) {
                callbackInfoReturnable.setReturnValue(MusicTracker.MusicType.valueOf("KING_TATER"));
            }
        }
    }
}
