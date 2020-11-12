package io.github.hydos.lint.spirit;

import java.util.function.Supplier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import io.github.hydos.lint.Lint;
import net.minecraft.util.Identifier;

public class Spirit {
	public static final Codec<Spirit> CODEC = RecordCodecBuilder.create(instance -> {
		return instance.group(
			HomeSeries.REGISTRY_CODEC.optionalFieldOf("home_series", () -> HomeSeries.LINT).forGetter(Spirit::getHomeSeries)
		).apply(instance, Spirit::new);
	});
	public static final Identifier DEFAULT_SPIRIT_ID = Lint.id("default_spirit");
	public static final Spirit DEFAULT_SPIRIT = new Spirit(() -> HomeSeries.LINT);

	private final Supplier<HomeSeries> homeSeries;

	public Spirit(Supplier<HomeSeries> homeSeries) {
		this.homeSeries = homeSeries;
	}

	public Supplier<HomeSeries> getHomeSeries() {
		return this.homeSeries;
	}
}
