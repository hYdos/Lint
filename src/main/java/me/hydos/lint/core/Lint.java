package me.hydos.lint.core;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Lint implements DedicatedServerModInitializer {

    public static final Block ADVENTURE_TRANSFORMER = new Block(AbstractBlock.Settings.of(Material.METAL));
    public static final String MODID = "lint";

    public void onInitializeServer() {
        Registry.register(Registry.BLOCK, new Identifier(MODID, "adventure_transformer"), ADVENTURE_TRANSFORMER);
    }
}
