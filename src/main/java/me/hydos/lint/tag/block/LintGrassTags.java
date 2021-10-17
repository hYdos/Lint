package me.hydos.lint.tag.block;

import me.hydos.lint.Lint;
import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.block.Block;
import net.minecraft.tag.Tag;

public final class LintGrassTags {
	public static final Tag<Block> UNTAINTED = TagFactory.BLOCK.create(Lint.id("untainted/grass"));

	private LintGrassTags() {}

	/* no-op */
	static void initialize() {}
}
