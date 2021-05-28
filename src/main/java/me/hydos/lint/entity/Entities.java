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

package me.hydos.lint.entity;

import me.hydos.lint.Lint;
import me.hydos.lint.entity.aggressive.CrabEntity;
import me.hydos.lint.entity.aggressive.GhostEntity;
import me.hydos.lint.entity.aggressive.I509VCBEntity;
import me.hydos.lint.entity.aggressive.KingTaterEntity;
import me.hydos.lint.entity.aggressive.TaterMinionEntity;
import me.hydos.lint.entity.passive.BeeTaterEntity;
import me.hydos.lint.entity.passive.TinyPotatoEntity;
import me.hydos.lint.entity.passive.TinyPotatoNpcEntity;
import me.hydos.lint.entity.passive.human.NPCHumanEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;

public class Entities {

	/**
	 * Passive entities
	 */
	public static final EntityType<BeeTaterEntity> BEE_TATER =
			Registry.register(Registry.ENTITY_TYPE, Lint.id("bee_tater"), FabricEntityTypeBuilder.create(SpawnGroup.AMBIENT, BeeTaterEntity::new)
					.dimensions(EntityDimensions.fixed(0.3f, 2f))
					.build());
	public static final EntityType<TinyPotatoEntity> TINY_POTATO =
			Registry.register(Registry.ENTITY_TYPE, Lint.id("tiny_potato"), FabricEntityTypeBuilder.create(SpawnGroup.AMBIENT, TinyPotatoEntity::new)
					.dimensions(EntityDimensions.fixed(0.3f, 0.4f))
					.build());

	/**
	 * Npc entities
	 */
	public static final EntityType<TinyPotatoNpcEntity> NPC_TINY_POTATO =
			Registry.register(Registry.ENTITY_TYPE, Lint.id("npc_tiny_potato"), FabricEntityTypeBuilder.create(SpawnGroup.MISC, TinyPotatoNpcEntity::new)
					.dimensions(EntityDimensions.fixed(0.3f, 0.4f))
					.build());

	public static final EntityType<NPCHumanEntity> NPC_HUMAN =
			Registry.register(Registry.ENTITY_TYPE, Lint.id("npc_human"), FabricEntityTypeBuilder.create(SpawnGroup.MISC, NPCHumanEntity::new)
					.dimensions(EntityDimensions.fixed(0.6f, 1.8f))
					.build());

	/**
	 * Aggressive entities
	 */
	public static final EntityType<GhostEntity> GHOST =
			Registry.register(Registry.ENTITY_TYPE, Lint.id("ghost"), FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, GhostEntity::new)
					.dimensions(EntityDimensions.fixed(1, 2f))
					.build());
	public static final EntityType<CrabEntity> CRAB = // crab crab crab crab crab crab crab crab crab crab crab crab crab crab crab crab crab crab crab crab crab crab crab crab crab crab crab crab crab crab crab crab crab crab crab
			Registry.register(Registry.ENTITY_TYPE, Lint.id("crab"), FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, CrabEntity::new)
					.dimensions(EntityDimensions.fixed(0.8F, 0.25F))
					.build());

	/**
	 * Bosses
	 */
	public static final EntityType<TaterMinionEntity> MINION =
			Registry.register(Registry.ENTITY_TYPE, Lint.id("minion"), FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, (EntityType<TaterMinionEntity> type, World world) -> new TaterMinionEntity(type, world, null))
					.dimensions(TINY_POTATO.getDimensions())
					.build());

	public static final EntityType<KingTaterEntity> KING_TATER =
			Registry.register(Registry.ENTITY_TYPE, Lint.id("king_tater"), FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, KingTaterEntity::new)
					.dimensions(EntityDimensions.changing(2f, 2f))
					.build());

	public static final EntityType<I509VCBEntity> I509VCB =
			Registry.register(Registry.ENTITY_TYPE, Lint.id("i509vcb"), FabricEntityTypeBuilder.createMob()
					.spawnGroup(SpawnGroup.MONSTER)
					.entityFactory(I509VCBEntity::new)
					.dimensions(EntityDimensions.changing(1f, 2.25f))
					.defaultAttributes(I509VCBEntity::createAttributes)
					.build());

	public static void initialize() {
		Birds.register();

		FabricDefaultAttributeRegistry.register(Entities.TINY_POTATO, TinyPotatoEntity.createAttributes());
		FabricDefaultAttributeRegistry.register(Entities.NPC_TINY_POTATO, TinyPotatoNpcEntity.createAttributes());
		FabricDefaultAttributeRegistry.register(Entities.BEE_TATER, TinyPotatoEntity.createAttributes());
		FabricDefaultAttributeRegistry.register(Entities.MINION, TaterMinionEntity.createAttributes());
		FabricDefaultAttributeRegistry.register(Entities.NPC_HUMAN, PlayerEntity.createPlayerAttributes()
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16));

		FabricDefaultAttributeRegistry.register(Entities.KING_TATER, KingTaterEntity.createAttributes());
		FabricDefaultAttributeRegistry.register(Entities.GHOST, GhostEntity.createHostileAttributes());
		FabricDefaultAttributeRegistry.register(Entities.CRAB, CrabEntity.createCrobAttributes());

		SpawnRestriction.register(Entities.TINY_POTATO, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, TinyPotatoEntity::canSpawn);
		SpawnRestriction.register(Entities.GHOST, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark);
//		SpawnRestriction.register(Entities.CRAB, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, TinyPotatoEntity::canSpawn);
	}
}
