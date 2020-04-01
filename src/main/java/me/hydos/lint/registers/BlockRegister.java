package me.hydos.lint.registers;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class BlockRegister {

    private static List<Block> blocks = new ArrayList<>();

    public static void registerBlocks(ItemGroup itemGroup, Block block, String identifier){
        Registry.register(Registry.BLOCK, new Identifier("lint", identifier), block);
        Registry.register(Registry.ITEM, new Identifier("lint", identifier), new BlockItem(block, new Item.Settings().group(itemGroup)));
        blocks.add(block);
    }

}
