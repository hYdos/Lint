package me.hydos.lint.mixin.client;

import com.mojang.serialization.Lifecycle;
import net.minecraft.world.level.LevelProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LevelProperties.class)
public class LevelPropertiesMixin {

	@Inject(method = "getLifecycle", at = @At("HEAD"), cancellable = true)
	private void weAreAlwaysStable(CallbackInfoReturnable<Lifecycle> cir){
		cir.setReturnValue(Lifecycle.stable());
	}
}
