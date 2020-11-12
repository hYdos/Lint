package io.github.hydos.lint.mixin;

import com.google.common.collect.ImmutableMap;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import io.github.hydos.lint.spirit.HomeSeries;
import io.github.hydos.lint.spirit.Spirit;
import io.github.hydos.lint.spirit.SpiritRegistry;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

@Mixin(DynamicRegistryManager.class)
public class DynamicRegistryManagerMixin {
	@Inject(method = "method_30531", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/registry/DynamicRegistryManager;register(Lcom/google/common/collect/ImmutableMap$Builder;Lnet/minecraft/util/registry/RegistryKey;Lcom/mojang/serialization/Codec;)V", ordinal = 0), locals = LocalCapture.CAPTURE_FAILHARD)
	private static void registerCustomDynamicRegistries(CallbackInfoReturnable<ImmutableMap<RegistryKey<? extends Registry<?>>, DynamicRegistryManager.Info<?>>> ci, ImmutableMap.Builder<RegistryKey<? extends Registry<?>>, DynamicRegistryManager.Info<?>> builder) {
		builder.put(SpiritRegistry.HOME_SERIES_KEY, new DynamicRegistryManager.Info<>(SpiritRegistry.HOME_SERIES_KEY, HomeSeries.CODEC, null));
		builder.put(SpiritRegistry.SPIRIT_KEY, new DynamicRegistryManager.Info<>(SpiritRegistry.SPIRIT_KEY, Spirit.CODEC, null));
	}
}
