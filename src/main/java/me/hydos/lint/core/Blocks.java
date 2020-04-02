package me.hydos.lint.core;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public interface Blocks {

    Block RICH_DIRT = new Block(FabricBlockSettings.of(Material.EARTH).hardness(0.5f).sounds(BlockSoundGroup.GRASS).build());
    Block LIVELY_GRASS = new Block(FabricBlockSettings.of(Material.EARTH).hardness(0.5f).sounds(BlockSoundGroup.GRASS).build());


    static void onInitialize(){
        registerBlock(ItemGroup.BUILDING_BLOCKS, RICH_DIRT, "rich_dirt");
        registerBlock(ItemGroup.BUILDING_BLOCKS, LIVELY_GRASS, "lively_grass");
    }

    static void registerBlock(ItemGroup itemGroup, Block block, String identifier){
        Registry.register(Registry.BLOCK, new Identifier("lint", identifier), block);
        Registry.register(Registry.ITEM, new Identifier("lint", identifier), new BlockItem(block, new Item.Settings().group(itemGroup)));
    }


}
