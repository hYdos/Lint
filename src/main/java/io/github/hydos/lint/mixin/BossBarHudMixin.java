package io.github.hydos.lint.mixin;

import io.github.hydos.lint.world.biome.Biomes;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.BossBarHud;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BossBarHud.class)
public class BossBarHudMixin {

    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(method = "shouldThickenFog", at = @At("HEAD"), cancellable = true)
    private void thickenBiomeFog(CallbackInfoReturnable<Boolean> cir) {
        assert client.world != null;
        assert client.player != null;
        if (client.world.getBiome(client.player.getBlockPos()).getFogColor() == Biomes.CORRUPT_FOG_COLOUR) {
            cir.setReturnValue(true);
        }
    }

}
