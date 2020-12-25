package me.hydos.lint.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.tag.FluidTags;
import net.minecraft.tag.Tag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin {

	private FluidState currentFluid;

	@Redirect(method = "updateMovementInFluid", at = @At(value = "INVOKE", target = "Lnet/minecraft/fluid/FluidState;isIn(Lnet/minecraft/tag/Tag;)Z"))
	private boolean isIn(FluidState state, Tag<Fluid> tag) {
		this.currentFluid = state;
		if (!state.getFluid().isIn(tag) && !tag.equals(FluidTags.LAVA)) {
			return !state.isEmpty();
		} else {
			return state.isIn(tag);
		}
	}

	@Inject(method = "onSwimmingStart", at = @At("HEAD"), cancellable = true)
	private void checkIfWater(CallbackInfo ci) {
		if (currentFluid != null) {
			if (!currentFluid.isIn(FluidTags.WATER)) {
				ci.cancel();
			}
		}
	}
}
