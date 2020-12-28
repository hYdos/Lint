package me.hydos.lint.block;

import me.hydos.lint.Lint;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class BlockEntry {
	public Block block;
	public String translation;
	public Item drops;
	public ToolType breakingTool;
	public boolean generic;
	public Identifier id;
	public String textureId;

	public BlockEntry(Block block, String translation, Item drops, ToolType breakingTool, Identifier id, String textureId, boolean generic) {
		this.block = block;
		this.translation = translation;
		this.drops = drops;
		this.breakingTool = breakingTool;
		this.id = id;
		this.textureId = textureId;
		this.generic = generic;
	}

	public enum ToolType {
		SHOVEL, HOE, AXE, PICKAXE, NONE
	}

	public static BlockEntry empty(Block block, String id) {
		return new BlockEntry(block, "Not Translated", block.asItem(), ToolType.NONE, Lint.id(id), id, true);
	}
}
