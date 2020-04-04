package me.hydos.lint.core;

import me.hydos.lint.blocks.MysticalGrassBlock;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public interface Blocks {

    Block.Settings PLANK_SETTINGS = FabricBlockSettings.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).build();
    Block.Settings SAND_SETTINGS = FabricBlockSettings.of(Material.SAND).hardness(0.5f).sounds(BlockSoundGroup.SAND).build();

    Block WHITE_SAND = new Block(SAND_SETTINGS);

    Block CORRUPT_PLANKS = new Block(PLANK_SETTINGS);

    Block MYSTICAL_PLANKS = new Block(PLANK_SETTINGS);

    Block RICH_DIRT = new Block(FabricBlockSettings.of(Material.EARTH).hardness(0.5f).sounds(BlockSoundGroup.WET_GRASS).build());
    Block LIVELY_GRASS = new Block(FabricBlockSettings.of(Material.EARTH).hardness(0.5f).sounds(BlockSoundGroup.GRASS).build());

    Block MYSTICAL_LEAVES = new LeavesBlock(FabricBlockSettings.of(Material.ORGANIC).hardness(0.5f).sounds(BlockSoundGroup.SWEET_BERRY_BUSH).nonOpaque().build());
    Block MYSTICAL_LOG = new Block(FabricBlockSettings.of(Material.WOOD).hardness(2).sounds(BlockSoundGroup.WOOD).build());
    Block MYSTICAL_GRASS = new MysticalGrassBlock(FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().hardness(0).sounds(BlockSoundGroup.GRASS).nonOpaque().build());
    Block MYSTICAL_SAND = new FallingBlock(SAND_SETTINGS);

    Block CORRUPT_LEAVES = new LeavesBlock(FabricBlockSettings.of(Material.ORGANIC).hardness(0.5f).sounds(BlockSoundGroup.SWEET_BERRY_BUSH).nonOpaque().build());
    Block CORRUPT_LOG = new Block(FabricBlockSettings.of(Material.WOOD).hardness(2).sounds(BlockSoundGroup.WOOD).build());
    Block CORRUPT_GRASS = new Block(FabricBlockSettings.of(Material.ORGANIC).hardness(00.5f).sounds(BlockSoundGroup.GRASS).build());
    Block CORRUPT_SAND = new FallingBlock(SAND_SETTINGS);

    static void onInitialize() {

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
        registerBlock(ItemGroup.DECORATIONS, CORRUPT_GRASS, "corrupt_grass");
        registerBlock(ItemGroup.BUILDING_BLOCKS, CORRUPT_SAND, "corrupt_sand");
    }

    static void registerBlock(ItemGroup itemGroup, Block block, String identifier) {
        Registry.register(Registry.BLOCK, new Identifier("lint", identifier), block);
        Registry.register(Registry.ITEM, new Identifier("lint", identifier), new BlockItem(block, new Item.Settings().group(itemGroup)));
    }
}
