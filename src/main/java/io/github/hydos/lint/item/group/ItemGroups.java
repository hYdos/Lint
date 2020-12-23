package io.github.hydos.lint.item.group;

import io.github.hydos.lint.Lint;
import io.github.hydos.lint.block.LintBlocks;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ItemGroups implements ModInitializer {

	public static final ItemGroup LINT_BLOCKS = FabricItemGroupBuilder.create(
			Lint.id("lint_blocks"))
			.icon(() -> new ItemStack(LintBlocks.LIVELY_GRASS))
			.build();

	public static final ItemGroup LINT_DECORATIONS = FabricItemGroupBuilder.create(
			Lint.id("lint_decorations"))
			.icon(() -> new ItemStack(LintBlocks.MYSTICAL_DAISY))
			.build();

	@Override
	public void onInitialize() {
	}
}
