package me.hydos.lint.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.hydos.lint.mixinimpl.SoundShitCache;
import net.minecraft.client.sound.BiomeEffectSoundPlayer;
import net.minecraft.world.biome.Biome;

@Mixin(BiomeEffectSoundPlayer.class)
public class BiomeEffectSoundPlayerMixin {
	@Shadow
	private Biome activeBiome;

	@Inject(
			at = @At(value = "HEAD"),
			method = "tick")
	public void musicGood1(CallbackInfo info) {
		if (this.activeBiome != null) {
			SoundShitCache.prev = this.activeBiome.getLoopSound();
		}
	}

	@Inject(
			at = @At(
					value = "INVOKE",
					target = "Lit/unimi/dsi/fastutil/objects/Object2ObjectArrayMap;values()Lit/unimi/dsi/fastutil/objects/ObjectCollection;",
					ordinal = 1),
			method = "tick")
	public void musicGood2(CallbackInfo info) {
		SoundShitCache.next = this.activeBiome.getLoopSound();
	}
}