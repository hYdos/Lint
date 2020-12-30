package me.hydos.lint.tag;

import me.hydos.lint.Lint;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.tag.Tag;

public class LintBlockTags {

	public static final Tag<Block> BASIC_CASING = TagRegistry.block(Lint.id("basic_smeltery_casing"));

	public static void register() {
	}
}
