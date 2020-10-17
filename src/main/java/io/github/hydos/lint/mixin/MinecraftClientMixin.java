package io.github.hydos.lint.mixin;

import io.github.hydos.lint.sound.Sounds;
import io.github.hydos.lint.entity.Entities;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.MusicType;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.sound.MusicSound;
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
    private void getMusicType(CallbackInfoReturnable<MusicSound> callbackInfoReturnable) {
        ClientWorld world = this.world;
        Entity entity = this.cameraEntity;

        if (world != null && entity != null && !world.getEntitiesByType(Entities.KING_TATER, entity.getBoundingBox().expand(40), $ -> true).isEmpty()) {
            callbackInfoReturnable.setReturnValue(MusicType.createIngameMusic(Sounds.KING_TATER));
        }
    }
}
