package io.github.hydos.lint.item.group;

import me.hydos.lint.Lint;import me.hydos.lint.block.Blocks;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ItemGroups implements ModInitializer {

	public static final ItemGroup LINT_BLOCKS = FabricItemGroupBuilder.create(
			Lint.id("lint_blocks"))
			.icon(() -> new ItemStack(Blocks.LIVELY_GRASS))
			.build();

	public static final ItemGroup LINT_DECORATIONS = FabricItemGroupBuilder.create(
			Lint.id("lint_decorations"))
			.icon(() -> new ItemStack(Blocks.MYSTICAL_DAISY))
			.build();

	@Override
	public void onInitialize() {
	}
}
