package me.hydos.lint.entity;

import me.hydos.lint.Lint;
import me.hydos.lint.entity.passive.bird.AbstractBirdEntity;
import me.hydos.lint.entity.passive.bird.EasternRosellaEntity;
import me.hydos.lint.sound.Sounds;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;

public class Birds {

	/**
	 * Birds
	 */
	public static final EntityType<EasternRosellaEntity> EASTERN_ROSELLA =
			Registry.register(Registry.ENTITY_TYPE, Lint.id("eastern_rosella"), FabricEntityTypeBuilder.create(SpawnGroup.AMBIENT, EasternRosellaEntity::new)
					.dimensions(EntityDimensions.fixed(0.3f, 0.4f))
					.build());
	public static final AbstractBirdEntity.BirdData EASTERN_ROSELLA_DATA = new AbstractBirdEntity.BirdData(
			Sounds.KING_TATER,
			"Eastern Rosella",
			"Valoghese was here");

	public static void register() {
		FabricDefaultAttributeRegistry.register(EASTERN_ROSELLA, EasternRosellaEntity.createMobAttributes());
	}
}
