package io.github.hydos.lint.spirit;

import java.util.function.Supplier;

import com.mojang.serialization.Codec;

import io.github.hydos.lint.Lint;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.RegistryElementCodec;

public class HomeSeries {
	public static final Codec<HomeSeries> CODEC = Codec.unit(() -> new HomeSeries());
	public static final Codec<Supplier<HomeSeries>> REGISTRY_CODEC = RegistryElementCodec.of(SpiritRegistry.HOME_SERIES_KEY, CODEC);

	public static final Identifier LINT_ID = Lint.id("lint");
	public static final HomeSeries LINT = new HomeSeries();
}
