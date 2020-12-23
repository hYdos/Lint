package io.github.hydos.lint.block.entity;

import io.github.hydos.lint.Lint;
import io.github.hydos.lint.block.LintBlocks;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

public class BlockEntities implements ModInitializer {

	public static final BlockEntityType<SmelteryBlockEntity> SMELTERY = Registry.register(Registry.BLOCK_ENTITY_TYPE,
			Lint.id("smeltery"),
			BlockEntityType.Builder.create(
					SmelteryBlockEntity::new,
					LintBlocks.SMELTERY
			).build(null));

	@Override
	public void onInitialize() {
	}
}
