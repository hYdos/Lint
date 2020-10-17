package io.github.hydos.lint;

import io.github.hydos.lint.world.biome.Biomes;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Lint implements ModInitializer {

    public static final Block ADVENTURE_TRANSFORMER = new Block(AbstractBlock.Settings.of(Material.METAL));
    public static final String MODID = "lint";

    public static Identifier id(String path) {
        return new Identifier(MODID, path);
    }

    public void onInitialize() {
        Biomes.register();
        Registry.register(Registry.BLOCK, new Identifier(MODID, "adventure_transformer"), ADVENTURE_TRANSFORMER);
    }
}
