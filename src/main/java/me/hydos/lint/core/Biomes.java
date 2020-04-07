package me.hydos.lint.core;

import me.hydos.lint.world.dimension.biomes.CorruptForest;
import me.hydos.lint.world.dimension.biomes.MysticalForest;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.ChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;

import static me.hydos.lint.world.dungeon.TutorialJigsaws.FEATURE;

public interface Biomes {

    Biome MYSTICAL_FOREST = Registry.register(Registry.BIOME, new Identifier("lint", "mystical_forest"), new MysticalForest());
    Biome CORRUPT_FOREST = Registry.register(Registry.BIOME, new Identifier("lint", "corrupt_forest"), new CorruptForest());

    static void onInitialize() {
        Registry.BIOME.forEach(biome -> biome.addFeature(GenerationStep.Feature.RAW_GENERATION, FEATURE.configure(new DefaultFeatureConfig()).createDecoratedFeature(Decorator.CHANCE_HEIGHTMAP_DOUBLE.configure(new ChanceDecoratorConfig(100)))));
    }
}
