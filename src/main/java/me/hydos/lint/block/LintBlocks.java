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

package me.hydos.lint.block;

import me.hydos.lint.Lint;
import me.hydos.lint.block.organic.*;
import me.hydos.lint.block.util.BlockBuilder;
import me.hydos.lint.block.util.BlockBuilder.BlockConstructor;
import me.hydos.lint.block.util.BlockMaterial;
import me.hydos.lint.block.util.BlockMechanics;
import me.hydos.lint.block.util.Model;
import me.hydos.lint.item.ItemData;
import me.hydos.lint.item.group.ItemGroups;
import me.hydos.lint.mixinimpl.LintPortal;
import me.hydos.lint.util.Power;
import me.hydos.lint.util.TeleportUtils;
import me.hydos.lint.world.dimension.Dimensions;
import me.hydos.lint.world.tree.CanopyTree;
import me.hydos.lint.world.tree.CorruptTree;
import me.hydos.lint.world.tree.FrozenTree;
import me.hydos.lint.world.tree.MysticalTree;
import net.devtech.arrp.json.recipe.*;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.*;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemGroup;
import net.minecraft.network.packet.s2c.play.StopSoundS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShapes;

import static me.hydos.lint.Lint.RESOURCE_PACK;
import static me.hydos.lint.Lint.id;

/**
 * All of lint's core blocks.
 */
public class LintBlocks {
	private static final BlockConstructor<FallingBlock> FALLING_BLOCK = FallingBlock::new; // In case the constructor parameters change it becomes a one line fix

	// TODO: add type parameter for plant methods so it looks cleaner

	private static LintFlowerBlock createPlant(String id, StatusEffect effect) {
		return BlockBuilder.create()
				.material(LintMaterials.PLANT)
				.model(Model.CROSS)
				.itemGroup(ItemGroup.DECORATIONS)
				.itemModel(ItemData::generatedModel)
				.register(id, settings -> new LintFlowerBlock(effect, settings));
	}

	private static CorruptFlower createCorruptPlant(String id, StatusEffect effect) {
		return BlockBuilder.create()
				.material(LintMaterials.PLANT)
				.model(Model.CROSS)
				.itemGroup(ItemGroup.DECORATIONS)
				.itemModel(ItemData::generatedModel)
				.register(id, settings -> new CorruptFlower(effect, settings));
	}

	private static FrostedFlower createFrostedPlant(String id, StatusEffect effect) {
		return BlockBuilder.create()
				.material(LintMaterials.PLANT)
				.model(Model.CROSS)
				.itemGroup(ItemGroup.DECORATIONS)
				.itemModel(ItemData::generatedModel)
				.register(id, settings -> new FrostedFlower(effect, settings));
	}

	private static LintFlowerBlock createTussockPlant(String id, StatusEffect effect) {
		return BlockBuilder.create()
				.material(LintMaterials.TUSSOCK)
				.model(Model.CROSS)
				.itemGroup(ItemGroup.DECORATIONS)
				.itemModel(ItemData::generatedModel)
				.register(id, settings -> new LintFlowerBlock(effect, settings));
	}

	private static CorruptFlower createCorruptTussockPlant(String id, StatusEffect effect) {
		return BlockBuilder.create()
				.material(LintMaterials.TUSSOCK)
				.model(Model.CROSS)
				.itemGroup(ItemGroup.DECORATIONS)
				.itemModel(ItemData::generatedModel)
				.register(id, settings -> new CorruptFlower(effect, settings));
	}

	private static FrostedFlower createFrostedTussockPlant(String id, StatusEffect effect) {
		return BlockBuilder.create()
				.material(LintMaterials.TUSSOCK)
				.model(Model.CROSS)
				.itemGroup(ItemGroup.DECORATIONS)
				.itemModel(ItemData::generatedModel)
				.register(id, settings -> new FrostedFlower(effect, settings));
	}

	private static LintSaplingBlock createSapling(String id, SaplingGenerator tree, Block growsOn) {
		return BlockBuilder.create()
				.material(LintMaterials.SAPLING)
				.model(Model.CROSS)
				.itemGroup(ItemGroup.DECORATIONS)
				.itemModel(ItemData::generatedModel)
				.customLootTable()
				.register(id, settings -> new LintSaplingBlock(tree, settings, growsOn.getDefaultState()));
	}

	// Plants

	public static final FallenLeavesBlock CORRUPT_FALLEN_LEAVES = BlockBuilder.create()
			.material(LintMaterials.FALLEN_LEAVES
					.colour(MapColor.PURPLE))
			.model(Model.CUTOUT_SIMPLE_BLOCKSTATE)
			.itemModel(ItemData::generatedModel)
			.itemGroup(ItemGroup.DECORATIONS)
			.register("corrupt_fallen_leaves", FallenLeavesBlock::new);

	public static final CorruptFlower CORRUPT_STEM = createCorruptPlant("corrupt_stem", StatusEffects.NAUSEA);
	public static final FlowerBlock DILL = createPlant("dill", StatusEffects.LUCK);

	public static final FlowerBlock SPEARMINT = createPlant("spearmint", StatusEffects.HASTE);
	public static final FlowerBlock KARAI = createPlant("karai", StatusEffects.NAUSEA);
	public static final CorruptFlower KUREI = createCorruptPlant("kurei", StatusEffects.NAUSEA);

	public static final FlowerBlock MYSTICAL_DAISY = createPlant("yellow_daisy", StatusEffects.BAD_OMEN);

	public static final FallenLeavesBlock MYSTICAL_FALLEN_LEAVES = BlockBuilder.create()
			.material(LintMaterials.FALLEN_LEAVES
					.colour(MapColor.DIAMOND_BLUE))
			.model(Model.CUTOUT_SIMPLE_BLOCKSTATE)
			.itemModel(ItemData::generatedModel)
			.itemGroup(ItemGroup.DECORATIONS)
			.register("mystical_fallen_leaves", FallenLeavesBlock::new);

	public static final FlowerBlock MYSTICAL_GRASS_PLANT = BlockBuilder.create()
			.material(LintMaterials.PLANT)
			.model(Model.CROSS)
			.register("mystical_grass", settings -> new LintFlowerBlock(StatusEffects.BAD_OMEN, settings, VoxelShapes.cuboid(0.125, 0.0, 0.125, 0.9375, 0.5, 0.875)));

	public static final FlowerBlock MYSTICAL_STEM = createPlant("mystical_stem", StatusEffects.JUMP_BOOST);
	public static final FrostedFlower FROZEN_STEM = createFrostedPlant("frozen_stem", StatusEffects.SPEED);
	public static final FlowerBlock RED_TUSSOCK = createTussockPlant("red_tussock", StatusEffects.FIRE_RESISTANCE);
	public static final FrostedFlower MARIGOLD = createFrostedPlant("marigold", StatusEffects.GLOWING);
	public static final FrostedFlower SAKHALIN_MINT = createFrostedPlant("sakhalin_mint", StatusEffects.HEALTH_BOOST);

	public static final Block TATERBANE = BlockBuilder.create()
			.material(BlockMaterial.builder()
					.material(Material.PLANT)
					.colour(MapColor.RED)
					.strength(0.5f)
					.sounds(BlockSoundGroup.GRASS))
			.model(Model.CUTOUT_SIMPLE_BLOCKSTATE)
			.register("taterbane", LintFlowerBlock.Taterbane::new);

	public static final Block THAISA = BlockBuilder.create()
			.material(LintMaterials.TALL_FLOWER)
			.model(Model.TALL_PLANT)
			.itemGroup(ItemGroups.DECORATIONS)
			.itemModel(id -> ItemData.generatedModel(Lint.id("generic_blue_flower_top")))
			.customLootTable()
			.register("generic_blue_flower", LintTallFlowerBlock::new);

	public static final FlowerBlock TUSSOCK = createTussockPlant("tussock", StatusEffects.RESISTANCE); // Resistance because tussock is somewhat "hard"
	public static final FrostedFlower FROSTED_TUSSOCK = createFrostedTussockPlant("frosted_tussock", StatusEffects.RESISTANCE);
	public static final FlowerBlock WATERMINT = createPlant("watermint", StatusEffects.HASTE);
	public static final CorruptFlower WILTED_FLOWER = createCorruptPlant("wilted_flower", StatusEffects.POISON);
	public static final FrostedFlower MISTBLOOM = createFrostedPlant("mistbloom", StatusEffects.ABSORPTION);

	// Tree Stuff

	public static final LintLeavesBlock CORRUPT_LEAVES = BlockBuilder.create()
			.material(LintMaterials.LEAVES
					.colour(MapColor.PURPLE))
			.model(Model.CUTOUT_CUBE_ALL)
			.customLootTable()
			.register("corrupt_leaves", LintLeavesBlock::new);

	public static final Block CORRUPT_LOG_STRIPPED = BlockBuilder.create()
			.material(LintMaterials.log(MapColor.PURPLE, MapColor.PURPLE))
			.model(Model.NONE)
			.register("stripped_corrupt_log", PillarBlock::new);

	public static final Block CORRUPT_LOG = BlockBuilder.create()
			.material(LintMaterials.log(MapColor.PURPLE, MapColor.TERRACOTTA_BLACK))
			.model(Model.NONE)
			.register("corrupt_log", settings -> new StrippablePillarBlock(settings, CORRUPT_LOG_STRIPPED));

	public static final Block CORRUPT_PLANKS = BlockBuilder.create()
			.material(LintMaterials.PLANKS
					.colour(MapColor.PURPLE))
			.model(Model.SIMPLE_CUBE_ALL)
			.register("corrupt_planks");

	public static final LintLeavesBlock MYSTICAL_LEAVES = BlockBuilder.create()
			.material(LintMaterials.LEAVES
					.colour(MapColor.DIAMOND_BLUE))
			.model(Model.CUTOUT_CUBE_ALL)
			.customLootTable()
			.register("mystical_leaves", LintLeavesBlock::new);

	public static final Block STRIPPED_MYSTICAL_LOG = BlockBuilder.create()
			.material(LintMaterials.log(MapColor.DIAMOND_BLUE, MapColor.DIAMOND_BLUE))
			.model(Model.NONE)
			.register("stripped_mystical_log", PillarBlock::new);

	public static final Block MYSTICAL_LOG = BlockBuilder.create()
			.material(LintMaterials.log(MapColor.PURPLE, MapColor.TERRACOTTA_BLACK))
			.model(Model.NONE)
			.register("mystical_log", settings -> new StrippablePillarBlock(settings, CORRUPT_LOG_STRIPPED));

	public static final DistantLeavesBlock CANOPY_LEAVES = BlockBuilder.create()
			.material(LintMaterials.LEAVES
					.colour(MapColor.CYAN))
			.model(Model.CUTOUT_CUBE_ALL)
			.customLootTable()
			.register("canopy_leaves", settings -> new DistantLeavesBlock(9, settings));

	public static final LintLeavesBlock FROZEN_LEAVES = BlockBuilder.create()
			.material(LintMaterials.LEAVES
					.colour(MapColor.WHITE))
			.model(Model.CUTOUT_CUBE_ALL)
			.customLootTable()
			.register("frozen_leaves", LintLeavesBlock::new);

	public static final Block MYSTICAL_PLANKS = BlockBuilder.create()
			.material(LintMaterials.PLANKS
					.colour(MapColor.DIAMOND_BLUE))
			.model(Model.SIMPLE_CUBE_ALL)
			.register("mystical_planks");

	// Soil and Sand

	public static final Block ASH = BlockBuilder.create()
			.material(LintMaterials.SAND
					.colour(MapColor.GRAY))
			.model(Model.SIMPLE_CUBE_ALL)
			.register("ash", FALLING_BLOCK);

	public static final Block CORRUPT_GRASS = BlockBuilder.create()
			.material(LintMaterials.GRASS_BLOCK
					.material(Material.SOLID_ORGANIC)
					.colour(MapColor.PURPLE))
			.model(Model.NONE)
			.customLootTable()
			.register("corrupt_grass", LintSpreadableBlock::new);

	public static final Block CORRUPT_SAND = BlockBuilder.create()
			.material(LintMaterials.SAND
					.colour(MapColor.PURPLE))
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

	public static final Block RICH_DIRT = BlockBuilder.create()
			.material(LintMaterials.DIRT)
			.model(Model.SIMPLE_CUBE_ALL)
			.register("rich_dirt", DirtLikeBlock::new);

	public static final Block RICH_SOIL = BlockBuilder.create()
			.material(LintMaterials.FARMLAND)
			.model(Model.SIMPLE_BLOCKSTATE_ONLY)
			.register("rich_soil", FarmlandBlock::new);

	public static final Block WHITE_SAND = BlockBuilder.create()
			.material(LintMaterials.SAND
					.colour(MapColor.OFF_WHITE))
			.model(Model.SIMPLE_CUBE_ALL)
			.register("white_sand", FALLING_BLOCK);

	// Underground


	public static final Block ALLOS_CRYSTAL = BlockBuilder.create()
			.material(LintMaterials.POWER_CRYSTAL.luminosity(7))
			.model(Model.SIMPLE_BLOCKSTATE_ONLY)
			.itemGroup(ItemGroups.DECORATIONS)
			.register("allos_crystal", settings -> new PowerCrystalBlock(settings, StatusEffects.GLOWING));

	public static final Block ALLOS_INFUSED_ASPHALT = BlockBuilder.create()
			.material(LintMaterials.COBBLESTONE)
			.model(Model.SIMPLE_CUBE_ALL)
			.register("allos_infused_asphalt", settings -> new InfusedBlock(settings, Power.ALLOS));

	public static final Block ASPHALT = BlockBuilder.create()
			.material(LintMaterials.COBBLESTONE)
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
					.colour(MapColor.TERRACOTTA_PURPLE))
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

	public static final Block MANOS_CRYSTAL = BlockBuilder.create()
			.material(LintMaterials.POWER_CRYSTAL.luminosity(4))
			.model(Model.SIMPLE_BLOCKSTATE_ONLY)
			.itemGroup(ItemGroups.DECORATIONS)
			.register("manos_crystal", settings -> new PowerCrystalBlock(settings, StatusEffects.NAUSEA));

	public static final Block MANOS_INFUSED_ASPHALT = BlockBuilder.create() // TODO this should generate if asphalt is above a generating manos crystal
			.material(LintMaterials.COBBLESTONE)
			.model(Model.SIMPLE_CUBE_ALL)
			.register("manos_infused_asphalt", settings -> new InfusedBlock(settings, Power.MANOS));

	public static final Block PEARLESCENT_STONE = BlockBuilder.create()
			.material(LintMaterials.STONE
					.colour(MapColor.TERRACOTTA_WHITE))
			.model(Model.SIMPLE_CUBE_ALL)
			.customLootTable()
			.register("pearlescent_stone");

	public static final Block PEARLESCENT_COBBLESTONE = BlockBuilder.create()
			.material(LintMaterials.COBBLESTONE
					.colour(MapColor.TERRACOTTA_WHITE))
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

	// Structures
	public static final Block DUNGEON_BRICKS = BlockBuilder.create()
			.material(LintMaterials.DUNGEON)
			.model(Model.SIMPLE_CUBE_ALL)
			.register("dungeon_bricks");

	public static final Block MOSSY_DUNGEON_BRICKS = BlockBuilder.create()
			.material(LintMaterials.DUNGEON
					.hardness(4.0f))
			.model(Model.SIMPLE_CUBE_ALL)
			.register("mossy_dungeon_bricks");

	public static final ReturnHomeBlock RETURN_HOME = BlockBuilder.create()
			.material(BlockMaterial.builder()
					.material(Material.STONE)
					.colour(MapColor.TERRACOTTA_WHITE)
					.hardness(-1.0f)
					.resistance(3600000.0f)
					.sounds(BlockSoundGroup.METAL))
			.model(Model.SIMPLE_CUBE_ALL)
			.itemGroup(ItemGroups.DECORATIONS)
			.register("return_home", ReturnHomeBlock::new);

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
					.colour(MapColor.ORANGE)
					.hardness(1.25f)
					.resistance(4.2f)
					.miningLevel(FabricToolTags.PICKAXES, 0))
			.model(Model.SIMPLE_CUBE_ALL)
			.register("ceramic");

	// The Portal

	public static final Block HAYKAMIUM_PORTAL = BlockBuilder.create()
			.material(BlockMaterial.builder()
					.material(Material.PORTAL)
					.colour(MapColor.CYAN)
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
								serverPlayerEntity.networkHandler.sendPacket(new StopSoundS2CPacket(null, null));
							}
							if (entity instanceof LivingEntity) {
								TeleportUtils.teleport(((LivingEntity) entity), fraiya, new BlockPos(((pos.getX() + 0xFF) & 0x1FF) - 0x7F, 80, ((pos.getZ() + 0xFF) & 0x1FF) - 0xFF));
							}
						}
					}));

	// Misc and Crafted Blocks

	public static final Block COOKIE = BlockBuilder.create()
			.material(BlockMaterial.copy(Blocks.CAKE))
			.model(Model.SIMPLE_CUBE_ALL)
			.itemGroup(ItemGroups.FOOD)
			.register("cookie");

	public static final Block MYSTICAL_DOOR = Blocks.WARPED_DOOR; // TODO

	// Slabs and Saplings

	private static Block registerSlab(String id, String planksId, BlockMaterial material) {
		Block block = BlockBuilder.create()
				.material(material)
				.model(Model.slab(planksId))
				.register(id, SlabBlock::new);

		Identifier identifier = Lint.id(id);

		RESOURCE_PACK.addRecipe(identifier, JRecipe.shaped(
				JPattern.pattern("###"),
				JKeys.keys()
						.key("#", JIngredient
								.ingredient()
								.item(id(planksId).toString())),
				JResult.stackedResult(identifier.toString(), 6)));

		return block;
	}

	public static final Block MYSTICAL_SLAB = registerSlab("mystical_slab", "mystical_planks", LintMaterials.PLANKS);
	public static final Block CORRUPT_SLAB = registerSlab("corrupt_slab", "corrupt_planks", LintMaterials.PLANKS);
	public static final Block DUNGEON_BRICK_SLAB = registerSlab("dungeon_brick_slab", "dungeon_bricks", LintMaterials.DUNGEON);

	public static final SaplingBlock MYSTICAL_SAPLING = createSapling("mystical_sapling", new MysticalTree(), LIVELY_GRASS);
	public static final SaplingBlock FROZEN_SAPLING = createSapling("frozen_sapling", new FrozenTree(), FROSTED_GRASS);
	public static final SaplingBlock CORRUPT_SAPLING = createSapling("corrupt_sapling", new CorruptTree(), CORRUPT_GRASS);
	public static final SaplingBlock CANOPY_SAPLING = createSapling("canopy_sapling", new CanopyTree(), LIVELY_GRASS);

	public static void initialise() {
	}

	private static final class FarmlandBlock extends net.minecraft.block.FarmlandBlock {
		protected FarmlandBlock(Settings settings) {
			super(settings);
		}
	}
}
