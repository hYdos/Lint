package io.github.hydos.lint.mixin.client;

import io.github.hydos.lint.entity.Entities;
import io.github.hydos.lint.sound.Sounds;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.sound.MusicSound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Shadow
    public ClientWorld world;

    @Shadow
    public Entity cameraEntity;

    @Inject(method = "getMusicType", at = @At("HEAD"), cancellable = true)
    private void getMusicType(CallbackInfoReturnable<MusicSound> callbackInfoReturnable) {
        ClientWorld world = this.world;
        Entity entity = this.cameraEntity;

        if (world != null && entity != null && !world.getEntitiesByType(Entities.KING_TATER, entity.getBoundingBox().expand(40), $ -> true).isEmpty()) {
            callbackInfoReturnable.setReturnValue(Sounds.KING_TATER_LOOP);
        }
        if (world != null && entity != null && !world.getEntitiesByType(Entities.I5, entity.getBoundingBox().expand(40), $ -> true).isEmpty()) {
            callbackInfoReturnable.setReturnValue(Sounds.I509_LOOP);
        }
//        if (world != null && entity != null && !world.getEntitiesByType(Entities.LEX_MANOS, entity.getBoundingBox().expand(40), $ -> true).isEmpty()) {
//            callbackInfoReturnable.setReturnValue(Sounds.KING_TATER_LOOP);
//        }
    }
}
