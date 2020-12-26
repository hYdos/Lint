package me.hydos.lint.mixin.client;

import java.util.Optional;
import java.util.Random;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.BiomeEffectSoundPlayer;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.sound.BiomeAdditionsSound;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;

// How not to write a mixin
// Illustrated tutorial
@Mixin(BiomeEffectSoundPlayer.class)
public class BiomeEffectSoundPlayerMixin {
	@Final @Shadow private ClientPlayerEntity player;
	@Final @Shadow private SoundManager soundManager;
	@Final @Shadow private BiomeAccess biomeAccess;
	@Final @Shadow private Random random;
	@Shadow private Object2ObjectArrayMap<Biome, BiomeEffectSoundPlayer.MusicLoop> soundLoops;
	@Shadow private Optional<BiomeMoodSound> moodSound;
	@Shadow private Optional<BiomeAdditionsSound> additionsSound;
	@Shadow private float moodPercentage;
	@Shadow private Biome activeBiome;

	private Biome inactiveBiome;

	@Inject(
			at = @At(value = "HEAD"),
			method = "tick")
	public void musicGood1(CallbackInfo info) {
		this.inactiveBiome = this.activeBiome;
	}

	@Inject(
			at = @At(
					value = "INVOKE",
					target = "Lit/unimi/dsi/fastutil/objects/Object2ObjectArrayMap;values()Lit/unimi/dsi/fastutil/objects/ObjectCollection;",
					ordinal = 1),
			method = "tick")
	public void musicGood2(CallbackInfo info) {
		if (this.inactiveBiome != null && this.inactiveBiome.getLoopSound().isPresent()) {
			Biome biome = this.biomeAccess.getBiome(this.player.getX(), this.player.getY(), this.player.getZ());

			if (biome.getLoopSound().isPresent()) {
				if (biome.getLoopSound().get().equals(biome.getLoopSound().get())) {
					info.cancel(); // no

					// vanilla shit
					this.additionsSound.ifPresent((biomeAdditionsSound) -> {
						if (this.random.nextDouble() < biomeAdditionsSound.getChance()) {
							this.soundManager.play(PositionedSoundInstance.ambient(biomeAdditionsSound.getSound()));
						}

					});
					this.moodSound.ifPresent((biomeMoodSound) -> {
						World world = this.player.world;
						int i = biomeMoodSound.getSpawnRange() * 2 + 1;
						BlockPos blockPos = new BlockPos(this.player.getX() + (double)this.random.nextInt(i) - (double)biomeMoodSound.getSpawnRange(), this.player.getEyeY() + (double)this.random.nextInt(i) - (double)biomeMoodSound.getSpawnRange(), this.player.getZ() + (double)this.random.nextInt(i) - (double)biomeMoodSound.getSpawnRange());
						int j = world.getLightLevel(LightType.SKY, blockPos);
						if (j > 0) {
							this.moodPercentage -= (float)j / (float)world.getMaxLightLevel() * 0.001F;
						} else {
							this.moodPercentage -= (float)(world.getLightLevel(LightType.BLOCK, blockPos) - 1) / (float)biomeMoodSound.getCultivationTicks();
						}

						if (this.moodPercentage >= 1.0F) {
							double d = (double)blockPos.getX() + 0.5D;
							double e = (double)blockPos.getY() + 0.5D;
							double f = (double)blockPos.getZ() + 0.5D;
							double g = d - this.player.getX();
							double h = e - this.player.getEyeY();
							double k = f - this.player.getZ();
							double l = (double)MathHelper.sqrt(g * g + h * h + k * k);
							double m = l + biomeMoodSound.getExtraDistance();
							PositionedSoundInstance positionedSoundInstance = PositionedSoundInstance.ambient(biomeMoodSound.getSound(), this.player.getX() + g / l * m, this.player.getEyeY() + h / l * m, this.player.getZ() + k / l * m);
							this.soundManager.play(positionedSoundInstance);
							this.moodPercentage = 0.0F;
						} else {
							this.moodPercentage = Math.max(this.moodPercentage, 0.0F);
						}

					});
				}
			}
		}
	}
}
