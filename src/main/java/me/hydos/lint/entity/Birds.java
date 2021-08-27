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
import me.hydos.lint.entity.aggressive.NightclawEntity;
import me.hydos.lint.entity.passive.bird.AbstractBirdEntity;
import me.hydos.lint.entity.passive.bird.EasternRosellaEntity;
import me.hydos.lint.entity.passive.bird.RedTailedTropicBirdEntity;
import me.hydos.lint.sound.Sounds;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;

public class Birds {

	public static final AbstractBirdEntity.BirdData EASTERN_ROSELLA_DATA = new AbstractBirdEntity.BirdData(
			Sounds.EASTERN_ROSELLA_IDLE,
			"Eastern Rosella",
			"They chatter through the ethereal groves of fraiya");
	public static final AbstractBirdEntity.BirdData NIGHTCLAW_DATA = new AbstractBirdEntity.BirdData(
			Sounds.EASTERN_ROSELLA_IDLE,
			"Nightclaw",
			"When Manos' touch reached the rosellas");
	public static final AbstractBirdEntity.BirdData RED_TAILED_TROPICBIRD_DATA = new AbstractBirdEntity.BirdData(
			Sounds.EASTERN_ROSELLA_IDLE,
			"Red Tailed Tropicbird",
			"Fraiya's primary Seabird");

	/**
	 * Birds
	 */
	public static final EntityType<EasternRosellaEntity> EASTERN_ROSELLA =
			Registry.register(Registry.ENTITY_TYPE, Lint.id("eastern_rosella"), FabricEntityTypeBuilder.create(SpawnGroup.AMBIENT, EasternRosellaEntity::new)
					.dimensions(EntityDimensions.fixed(0.4f, 0.8f))
					.build());

	public static final EntityType<NightclawEntity> NIGHTCLAW =
			Registry.register(Registry.ENTITY_TYPE, Lint.id("nightclaw"), FabricEntityTypeBuilder.create(SpawnGroup.AMBIENT, NightclawEntity::new)
					.dimensions(EntityDimensions.fixed(0.4f, 0.8f))
					.build());

	public static final EntityType<RedTailedTropicBirdEntity> RED_TAILED_TROPICBIRD =
			Registry.register(Registry.ENTITY_TYPE, Lint.id("red_tailed_tropicbird"), FabricEntityTypeBuilder.create(SpawnGroup.AMBIENT, RedTailedTropicBirdEntity::new)
					.dimensions(EntityDimensions.fixed(0.4f, 0.8f))
					.build());

	public static void register() {
		FabricDefaultAttributeRegistry.register(EASTERN_ROSELLA, EasternRosellaEntity.createBirdAttributes(0.4D, 6.0D));
		FabricDefaultAttributeRegistry.register(NIGHTCLAW, EasternRosellaEntity.createBirdAttributes(0.4D, 6.0D, 1.0D));
		FabricDefaultAttributeRegistry.register(RED_TAILED_TROPICBIRD, RedTailedTropicBirdEntity.createBirdAttributes(1.0D, 8.0D));
	}
}
