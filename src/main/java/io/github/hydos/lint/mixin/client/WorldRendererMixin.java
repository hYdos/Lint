package io.github.hydos.lint.mixin.client;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import me.hydos.lint.Lint;import me.hydos.lint.world.dimension.Dimensions;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
	private static final Identifier HAYKAM_SUN = Lint.id("textures/environment/twin_sun.png");

	@Shadow
	@Final
	private static Identifier SUN;
	@Shadow
	private ClientWorld world;

	@Redirect(method = "renderSky", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/WorldRenderer;SUN:Lnet/minecraft/util/Identifier;"))
	private Identifier getCoolsun() {
		if (world.getDimension() == Dimensions.HAYKAM) {
			return HAYKAM_SUN;
		} else {
			return SUN;
		}
	}
}
