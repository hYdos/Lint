package io.github.hydos.lint.mixin;

import java.util.function.Supplier;

import com.mojang.serialization.Lifecycle;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.hydos.lint.spirit.HomeSeries;
import io.github.hydos.lint.spirit.Spirit;
import io.github.hydos.lint.spirit.SpiritRegistry;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

@Mixin(BuiltinRegistries.class)
public abstract class BuiltinRegistriesMixin {
	@Invoker(value = "addRegistry")
	public static <T, R extends MutableRegistry<T>> R addRegistry(RegistryKey<? extends Registry<T>> registryRef, R registry, Supplier<T> defaultValueSupplier, Lifecycle lifecycle) {
		throw new UnsupportedOperationException();
	}

	@Inject(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/registry/BuiltinRegistries;addRegistry(Lnet/minecraft/util/registry/RegistryKey;Ljava/util/function/Supplier;)Lnet/minecraft/util/registry/Registry;", ordinal = 0))
	private static void addCustomRegistry(CallbackInfo ci) {
		addRegistry(SpiritRegistry.HOME_SERIES_KEY, SpiritRegistry.HOME_SERIES_REGISTRY, () -> HomeSeries.LINT, Lifecycle.stable());
		addRegistry(SpiritRegistry.SPIRIT_KEY, SpiritRegistry.SPIRIT_REGISTRY, () -> Spirit.DEFAULT_SPIRIT, Lifecycle.stable());
		System.out.println("COOL");
	}
}