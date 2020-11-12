package io.github.hydos.lint.spirit;

import com.mojang.serialization.Lifecycle;

import io.github.hydos.lint.Lint;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

public class SpiritRegistry {
	private static final Identifier HOME_SERIES_ID = Lint.id("home_series");
	public static final RegistryKey<? extends Registry<HomeSeries>> HOME_SERIES_KEY = RegistryKey.ofRegistry(HOME_SERIES_ID);
	public static final MutableRegistry<HomeSeries> HOME_SERIES_REGISTRY = new DefaultedRegistry<>("lint:lint", HOME_SERIES_KEY, Lifecycle.stable());

	private static final Identifier SPIRIT_ID = Lint.id("spirit");
	public static final RegistryKey<? extends Registry<Spirit>> SPIRIT_KEY = RegistryKey.ofRegistry(SPIRIT_ID);
	public static final MutableRegistry<Spirit> SPIRIT_REGISTRY = new DefaultedRegistry<>("lint:default_spirit", SPIRIT_KEY, Lifecycle.stable());

	static {
		Registry.register(HOME_SERIES_REGISTRY, HomeSeries.LINT_ID, HomeSeries.LINT);
		Registry.register(SPIRIT_REGISTRY, Spirit.DEFAULT_SPIRIT_ID, Spirit.DEFAULT_SPIRIT);
	}
}
