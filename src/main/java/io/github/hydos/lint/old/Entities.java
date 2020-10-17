package io.github.hydos.lint.old;

import io.github.hydos.lint.entity.beetater.BeeTaterEntity;
import io.github.hydos.lint.entity.boss.KingTater;
import io.github.hydos.lint.entity.boss.TaterMinion;
import io.github.hydos.lint.entity.tater.LilTaterEntity;
import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public interface Entities {

    EntityType<BeeTaterEntity> BEE_TATER =
            Registry.register(Registry.ENTITY_TYPE, new Identifier("lint", "bee_tater"), FabricEntityTypeBuilder.create(SpawnGroup.AMBIENT, (BeeTaterEntity::new))
                    .size(EntityDimensions.fixed(0.3f, 0.4f))
                    .build());
    EntityType<LilTaterEntity> LIL_TATER =
            Registry.register(Registry.ENTITY_TYPE, new Identifier("lint", "lil_tater"), FabricEntityTypeBuilder.create(SpawnGroup.AMBIENT, (LilTaterEntity::new))
                    .size(EntityDimensions.fixed(0.3f, 0.4f))
                    .build());
    EntityType<KingTater> KING_TATER =
            Registry.register(Registry.ENTITY_TYPE, new Identifier("lint", "king_tater"), FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, KingTater::new)
                    .size(EntityDimensions.changing(2f, 2f))
                    .build());
    EntityType<TaterMinion> MINION =
            Registry.register(Registry.ENTITY_TYPE, new Identifier("lint", "minion"), FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, (EntityType<TaterMinion> type, World world) -> new TaterMinion(type, world, null))
                    .size(LIL_TATER.getDimensions())
                    .build());

    static void onInitialize() {
        FabricDefaultAttributeRegistry.register(Entities.LIL_TATER, LilTaterEntity.initAttributes());
        FabricDefaultAttributeRegistry.register(Entities.BEE_TATER, LilTaterEntity.initAttributes());
        FabricDefaultAttributeRegistry.register(Entities.MINION, TaterMinion.initAttributes());
        FabricDefaultAttributeRegistry.register(Entities.KING_TATER, KingTater.initAttributes());
    }
}
