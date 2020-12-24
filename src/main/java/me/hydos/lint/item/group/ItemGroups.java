package me.hydos.lint.item.group;

import me.hydos.lint.Lint;import me.hydos.lint.block.Blocks;
import me.hydos.lint.item.Items;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ItemGroups {

	public static final ItemGroup BLOCKS = FabricItemGroupBuilder.create(
			Lint.id("lint_blocks"))
			.icon(() -> new ItemStack(Blocks.LIVELY_GRASS))
			.build();

	public static final ItemGroup DECORATIONS = FabricItemGroupBuilder.create(
			Lint.id("lint_decorations"))
			.icon(() -> new ItemStack(Blocks.MYSTICAL_DAISY))
			.build();

	public static final ItemGroup TOOLS = FabricItemGroupBuilder.create(
			Lint.id("lint_tools"))
			.icon(() -> new ItemStack(Blocks.MYSTICAL_DAISY))
			.build();

	public static final ItemGroup ITEMS = FabricItemGroupBuilder.create(
			Lint.id("lint_items"))
			.icon(() -> new ItemStack(Items.TATER_ESSENCE))
			.build();

	public static void register() {
	}
}
