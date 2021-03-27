/*
 * Lint
 * Copyright (C) 2020 hYdos, Valoeghese, ramidzkh
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package me.hydos.lint.refactord.block;

import me.hydos.lint.Lint;
import me.hydos.lint.block.organic.DistantLeavesBlock;
import me.hydos.lint.core.block.BlockBuilder;
import me.hydos.lint.core.block.BlockMaterial;
import me.hydos.lint.core.block.BlockMechanics;
import me.hydos.lint.core.block.Model;
import me.hydos.lint.core.block.BlockBuilder.BlockConstructor;
import me.hydos.lint.core.item.ItemData;
import me.hydos.lint.item.group.ItemGroups;
import me.hydos.lint.mixinimpl.LintPortal;
import me.hydos.lint.refactord.block.organic.LintLeavesBlock;
import me.hydos.lint.refactord.block.organic.LintSpreadableBlock;
import me.hydos.lint.refactord.block.organic.LintTallFlowerBlock;
import me.hydos.lint.util.TeleportUtils;
import me.hydos.lint.world.dimension.Dimensions;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.packet.s2c.play.StopSoundS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;

/**
 * All of lint's core blocks.
 */
public class LintBlocks2 {
	private static final BlockConstructor<FallingBlock> FALLING_BLOCK = FallingBlock::new; // In case the constructor parameters change it becomes a one line fix

	// Plants and Stuff. Also planks.

	public static final LintLeavesBlock CORRUPT_LEAVES = BlockBuilder.create()
			.material(LintMaterials.LEAVES
					.colour(MaterialColor.PURPLE))
			.model(Model.CUTOUT_CUBE_ALL)
			.customLootTable()
			.register("corrupt_leaves", LintLeavesBlock::new);

	public static final Block CORRUPT_PLANKS = BlockBuilder.create()
			.material(LintMaterials.PLANKS
					.colour(MaterialColor.PURPLE))
			.model(Model.SIMPLE_CUBE_ALL)
			.register("corrupt_planks");

	public static final LintLeavesBlock MYSTICAL_LEAVES = BlockBuilder.create()
			.material(LintMaterials.LEAVES)
			.model(Model.CUTOUT_CUBE_ALL)
			.customLootTable()
			.register("mystical_leaves", LintLeavesBlock::new);

	public static final DistantLeavesBlock CANOPY_LEAVES = BlockBuilder.create()
			.material(LintMaterials.LEAVES)
			.model(Model.CUTOUT_CUBE_ALL)
			.customLootTable()
			.register("canopy_leaves", settings -> new DistantLeavesBlock(9, settings));

	public static final LintLeavesBlock FROZEN_LEAVES = BlockBuilder.create()
			.material(LintMaterials.LEAVES
					.colour(MaterialColor.WHITE))
			.model(Model.CUTOUT_CUBE_ALL)
			.customLootTable()
			.register("frozen_leaves", LintLeavesBlock::new);

	public static final Block MYSTICAL_PLANKS = BlockBuilder.create()
			.material(LintMaterials.PLANKS
					.colour(MaterialColor.DIAMOND))
			.model(Model.SIMPLE_CUBE_ALL)
			.register("mystical_planks");

	public static final Block THAISA = BlockBuilder.create()
			.material(LintMaterials.TALL_FLOWER)
			.model(Model.TALL_PLANT)
			.itemGroup(ItemGroups.DECORATIONS)
			.itemModel(id -> ItemData.generatedModel(Lint.id("block/generic_blue_flower_top")))
			.customLootTable()
			.register("generic_blue_flower", LintTallFlowerBlock::new);

	// Soil and Sand

	public static final Block ASH = BlockBuilder.create()
			.material(LintMaterials.SAND
					.colour(MaterialColor.GRAY))
			.model(Model.SIMPLE_CUBE_ALL)
			.register("ash", FALLING_BLOCK);

	public static final Block CORRUPT_GRASS = BlockBuilder.create()
			.material(LintMaterials.GRASS_BLOCK
					.material(Material.SOLID_ORGANIC)
					.colour(MaterialColor.PURPLE))
			.model(Model.NONE)
			.customLootTable()
			.register("corrupt_grass", LintSpreadableBlock::new);

	public static final Block CORRUPT_SAND = BlockBuilder.create()
			.material(LintMaterials.SAND
					.colour(MaterialColor.PURPLE))
			.model(Model.SIMPLE_CUBE_ALL)
			.register("corrupt_sand", FALLING_BLOCK);
	
	public static final Block FROSTED_GRASS = BlockBuilder.create()
			.material(LintMaterials.GRASS_BLOCK)
			.model(Model.NONE)
			.customLootTable()
			.register("frosted_grass", LintSpreadableBlock::new);

	public static final Block LIVELY_GRASS = BlockBuilder.create()
			.material(LintMaterials.GRASS_BLOCK)
			.model(Model.NONE)
			.customLootTable()
			.register("lively_grass", LintSpreadableBlock::new);

	public static final Block MYSTICAL_SAND = BlockBuilder.create()
			.material(LintMaterials.SAND)
			.model(Model.SIMPLE_CUBE_ALL)
			.register("mystical_sand", FALLING_BLOCK);

	public static final Block RICH_SOIL = BlockBuilder.create()
			.material(LintMaterials.FARMLAND)
			.model(Model.SIMPLE_BLOCKSTATE_ONLY)
			.register("rich_soil", FarmlandBlock::new);

	public static final Block WHITE_SAND = BlockBuilder.create()
			.material(LintMaterials.SAND
					.colour(MaterialColor.QUARTZ))
			.model(Model.SIMPLE_CUBE_ALL)
			.register("white_sand", FALLING_BLOCK);

	// Underground

	public static final Block ASPHALT = BlockBuilder.create()
			.material(LintMaterials.STONE
					.hardness(1.5f))
			.model(Model.SIMPLE_CUBE_ALL)
			.register("asphalt");

	public static final Block FUSED_STONE = BlockBuilder.create()
			.material(LintMaterials.STONE)
			.model(Model.SIMPLE_CUBE_ALL)
			.customLootTable()
			.register("fused_stone");
	
	public static final Block FUSED_COBBLESTONE = BlockBuilder.create()
			.material(LintMaterials.COBBLESTONE)
			.model(Model.SIMPLE_CUBE_ALL)
			.register("fused_cobblestone");

	public static final Block INDIGO_STONE = BlockBuilder.create()
			.material(LintMaterials.STONE
					.colour(MaterialColor.PURPLE_TERRACOTTA))
			.model(Model.SIMPLE_CUBE_ALL)
			.register("indigo_stone");

	public static final Block JUREL_ORE = BlockBuilder.create()
			.material(LintMaterials.STONE
					.hardness(3.0f)
					.miningLevel(FabricToolTags.PICKAXES, 2))
			.model(Model.SIMPLE_CUBE_ALL)
			.register("jurel_ore");

	public static final Block MAGNETITE_DEPOSIT = BlockBuilder.create() // Used to use sound group metal but seeing as it is mineral form I think I should keep it as stone.
			.material(LintMaterials.STONE
					.hardness(2.75f))
			.model(Model.SIMPLE_CUBE_ALL)
			.register("magnetite_deposit");

	public static final Block PEARLESCENT_STONE = BlockBuilder.create()
			.material(LintMaterials.STONE
					.colour(MaterialColor.WHITE_TERRACOTTA))
			.model(Model.SIMPLE_CUBE_ALL)
			.customLootTable()
			.register("pearlescent_stone");
	
	public static final Block PEARLESCENT_COBBLESTONE = BlockBuilder.create()
			.material(LintMaterials.COBBLESTONE
					.colour(MaterialColor.WHITE_TERRACOTTA))
			.model(Model.SIMPLE_CUBE_ALL)
			.register("pearlescent_cobblestone");

	public static final Block TARSCAN_ORE = BlockBuilder.create()
			.material(LintMaterials.STONE
					.hardness(2.0f))
			.model(Model.SIMPLE_CUBE_ALL)
			.register("tarscan_ore");

	public static final Block SICIERON_ORE = BlockBuilder.create()
			.material(LintMaterials.STONE
					.hardness(3.0f)
					.miningLevel(FabricToolTags.PICKAXES, 1))
			.model(Model.SIMPLE_CUBE_ALL)
			.register("sicieron_ore");

	// Smeltery and Similar

	public static final Block BASIC_CASING = BlockBuilder.create()
			.material(LintMaterials.SMELTERY)
			.model(Model.SIMPLE_CUBE_ALL)
			.register("basic_casing");

	public static final Block CRACKED_BASIC_CASING = BlockBuilder.create()
			.material(LintMaterials.SMELTERY)
			.model(Model.SIMPLE_CUBE_ALL)
			.register("cracked_basic_casing");

	public static final Block CERAMIC = BlockBuilder.create()
			.material(BlockMaterial.builder()
					.material(Material.STONE)
					.colour(MaterialColor.ORANGE)
					.hardness(1.25f)
					.resistance(4.2f)
					.miningLevel(FabricToolTags.PICKAXES, 0))
			.model(Model.SIMPLE_CUBE_ALL)
			.register("ceramic");

	// The Portal

	public static final Block HAYKAMIUM_PORTAL = BlockBuilder.create()
			.material(BlockMaterial.builder()
					.material(Material.PORTAL)
					.colour(MaterialColor.CYAN)
					.strength(-1.0f)
					.collidable(false)
					.sounds(BlockSoundGroup.STONE))
			.model(Model.SIMPLE_CUBE_ALL)
			.itemGroup(null)
			.register("haykamium_portal", new BlockMechanics()
					.onNeighbourUpdate((state, world, pos, block, fromPos, notify) -> LintPortal.resolve(world, pos, fromPos, true))
					.onEntityCollision((state, world, pos, entity) -> {
						if (!world.isClient()) {
							ServerWorld fraiya = ((ServerWorld) world).getServer().getWorld(Dimensions.FRAIYA_WORLD);

							if (fraiya == null) {
								return;
							}
							if (entity instanceof ServerPlayerEntity) {
								ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) entity;
								serverPlayerEntity.networkHandler.sendPacket(new StopSoundS2CPacket());
							}
							if (entity instanceof LivingEntity) {
								TeleportUtils.teleport(((LivingEntity) entity), fraiya, new BlockPos(((pos.getX() + 0xFF) & 0x1FF) - 0x7F, 80, ((pos.getZ() + 0xFF) & 0x1FF) - 0xFF));
							}
						}
					}));
	
	// Misc

	public static final Block COOKIE = BlockBuilder.create()
			.material(BlockMaterial.copy(Blocks.CAKE))
			.model(Model.SIMPLE_CUBE_ALL)
			.itemGroup(ItemGroups.FOOD)
			.register("cookie");

	public static final Block initialise() {
		return COOKIE;
	}

	private static final class FarmlandBlock extends net.minecraft.block.FarmlandBlock {
		protected FarmlandBlock(Settings settings) {
			super(settings);
		}
	}
}
