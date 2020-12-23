package io.github.hydos.lint.entity.aggressive;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.world.World;

@SuppressWarnings({"EntityConstructor"})
public class GhostEntity extends ZombieEntity {

	public GhostEntity(EntityType<GhostEntity> type, World world) {
		super(type, world);
	}
}
