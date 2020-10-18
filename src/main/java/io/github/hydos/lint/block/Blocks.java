package io.github.hydos.lint.block;

import io.github.hydos.lint.Lint;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.registry.Registry;

public interface Blocks {

    Block ADVENTURE_TRANSFORMER = new Block(AbstractBlock.Settings.of(Material.METAL));

    Block CORRUPT_STEM = new LintCorruptGrassBlock(StatusEffects.NAUSEA, FabricBlockSettings.of(Material.PLANT)
            .noCollision()
            .breakInstantly()
            .hardness(0)
            .sounds(BlockSoundGroup.GRASS)
            .nonOpaque()
    );

    Block WILTED_FLOWER = new LintCorruptGrassBlock(StatusEffects.POISON, FabricBlockSettings.of(Material.PLANT)
            .noCollision()
            .breakInstantly()
            .hardness(0)
            .sounds(BlockSoundGroup.GRASS)
            .nonOpaque()
    );

    Block MYSTICAL_STEM = new LintGrassBlock(StatusEffects.RESISTANCE, FabricBlockSettings.of(Material.PLANT)
            .noCollision()
            .breakInstantly()
            .hardness(0)
            .sounds(BlockSoundGroup.GRASS)
            .nonOpaque()
    );

    Block MYSTICAL_DAISY = new LintGrassBlock(StatusEffects.BAD_OMEN, net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings.of(Material.PLANT)
            .noCollision()
            .breakInstantly()
            .hardness(0)
            .sounds(BlockSoundGroup.GRASS)
            .nonOpaque());
    Block RETURN_HOME = new ReturnHomeBlock(FabricBlockSettings.of(Material.STONE).hardness(-1.0f).sounds(BlockSoundGroup.METAL));

    Block RED_BUTTON = new KingTaterButton(FabricBlockSettings.of(Material.SOIL).hardness(-0.1f).sounds(BlockSoundGroup.WET_GRASS));

    Block GREEN_BUTTON = new KingTaterButton(FabricBlockSettings.of(Material.SOIL).hardness(-0.1f).sounds(BlockSoundGroup.WET_GRASS));

    Block MYSTICAL_TRAPDOOR = new TrapdoorBlock(FabricBlockSettings.of(Material.WOOD).hardness(2).sounds(BlockSoundGroup.WOOD));

    Block DUNGEON_BRICKS = new Block(FabricBlockSettings.of(Material.STONE).hardness(4).sounds(BlockSoundGroup.STONE));

    Block.Settings PLANK_SETTINGS = FabricBlockSettings.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD);
    Block.Settings SAND_SETTINGS = FabricBlockSettings.of(Material.AGGREGATE).hardness(0.5f).sounds(BlockSoundGroup.SAND);

    Block WHITE_SAND = new FallingBlock(SAND_SETTINGS);

    Block CORRUPT_PLANKS = new Block(PLANK_SETTINGS);

    Block MYSTICAL_PLANKS = new Block(PLANK_SETTINGS);

    Block RICH_DIRT = new Block(FabricBlockSettings.of(Material.SOIL).hardness(0.5f).sounds(BlockSoundGroup.WET_GRASS));
    Block LIVELY_GRASS = new Block(FabricBlockSettings.of(Material.SOIL).hardness(0.5f).sounds(BlockSoundGroup.GRASS));

    Block MYSTICAL_LEAVES = new LeavesBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC).hardness(0.5f).sounds(BlockSoundGroup.SWEET_BERRY_BUSH).nonOpaque());
    Block MYSTICAL_FALLEN_LEAVES = new LeavesBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC).hardness(0.5f).sounds(BlockSoundGroup.SWEET_BERRY_BUSH).nonOpaque());
    Block MYSTICAL_LOG = new Block(FabricBlockSettings.of(Material.WOOD).hardness(2).sounds(BlockSoundGroup.WOOD));
    Block MYSTICAL_GRASS = new LintGrassBlock(StatusEffects.BAD_OMEN, FabricBlockSettings.of(Material.PLANT)
            .noCollision()
            .breakInstantly()
            .hardness(0)
            .sounds(BlockSoundGroup.GRASS)
            .nonOpaque()
    );
    Block MYSTICAL_SAND = new FallingBlock(SAND_SETTINGS);

    Block CORRUPT_LEAVES = new LeavesBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC).hardness(0.5f).sounds(BlockSoundGroup.SWEET_BERRY_BUSH).nonOpaque());
    Block CORRUPT_FALLEN_LEAVES = new LeavesBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC).hardness(0.5f).sounds(BlockSoundGroup.SWEET_BERRY_BUSH).nonOpaque());
    Block CORRUPT_LOG = new Block(FabricBlockSettings.of(Material.WOOD).hardness(2).sounds(BlockSoundGroup.WOOD));
    Block CORRUPT_GRASS = new Block(FabricBlockSettings.of(Material.SOLID_ORGANIC).hardness(00.5f).sounds(BlockSoundGroup.GRASS));
    Block CORRUPT_SAND = new FallingBlock(SAND_SETTINGS);

    static void registerBlock(ItemGroup itemGroup, Block block, String path) {
        Registry.register(Registry.BLOCK, Lint.id(path), block);
        Registry.register(Registry.ITEM, Lint.id(path), new BlockItem(block, new Item.Settings().group(itemGroup)));
    }

    static void initialize() {
        registerBlock(ItemGroup.REDSTONE, ADVENTURE_TRANSFORMER, "adventure_transformer");

        registerBlock(ItemGroup.DECORATIONS, CORRUPT_STEM, "corrupt_stem");
        registerBlock(ItemGroup.DECORATIONS, WILTED_FLOWER, "wilted_flower");

        registerBlock(ItemGroup.DECORATIONS, MYSTICAL_STEM, "mystical_stem");

        registerBlock(ItemGroup.DECORATIONS, MYSTICAL_DAISY, "yellow_daisy");
        registerBlock(ItemGroup.BUILDING_BLOCKS, RED_BUTTON, "red_button");

        registerBlock(ItemGroup.BUILDING_BLOCKS, GREEN_BUTTON, "green_button");

        registerBlock(ItemGroup.BUILDING_BLOCKS, MYSTICAL_TRAPDOOR, "mystical_trapdoor");

        registerBlock(ItemGroup.BUILDING_BLOCKS, DUNGEON_BRICKS, "dungeon_bricks");

        registerBlock(ItemGroup.BUILDING_BLOCKS, WHITE_SAND, "white_sand");

        registerBlock(ItemGroup.BUILDING_BLOCKS, CORRUPT_PLANKS, "corrupt_planks");

        registerBlock(ItemGroup.BUILDING_BLOCKS, MYSTICAL_PLANKS, "mystical_planks");
        registerBlock(ItemGroup.BUILDING_BLOCKS, RICH_DIRT, "rich_dirt");
        registerBlock(ItemGroup.BUILDING_BLOCKS, LIVELY_GRASS, "lively_grass");

        registerBlock(ItemGroup.BUILDING_BLOCKS, MYSTICAL_LEAVES, "mystical_leaves");
        registerBlock(ItemGroup.BUILDING_BLOCKS, MYSTICAL_LOG, "mystical_log");
        registerBlock(ItemGroup.DECORATIONS, MYSTICAL_GRASS, "mystical_grass");
        registerBlock(ItemGroup.BUILDING_BLOCKS, MYSTICAL_SAND, "mystical_sand");

        registerBlock(ItemGroup.BUILDING_BLOCKS, CORRUPT_LEAVES, "corrupt_leaves");
        registerBlock(ItemGroup.BUILDING_BLOCKS, CORRUPT_LOG, "corrupt_log");
        registerBlock(ItemGroup.BUILDING_BLOCKS, CORRUPT_GRASS, "corrupt_grass");
        registerBlock(ItemGroup.BUILDING_BLOCKS, CORRUPT_SAND, "corrupt_sand");

        registerBlock(ItemGroup.DECORATIONS, RETURN_HOME, "return_home");

        registerBlock(ItemGroup.DECORATIONS, MYSTICAL_FALLEN_LEAVES, "mystical_fallen_leaves");
        registerBlock(ItemGroup.DECORATIONS, CORRUPT_FALLEN_LEAVES, "corrupt_fallen_leaves");

        BlockRenderLayerMap.INSTANCE.putBlock(MYSTICAL_DAISY, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MYSTICAL_STEM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(CORRUPT_STEM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WILTED_FLOWER, RenderLayer.getCutout());
    }
}
