package io.github.hydos.lint.entity;

import io.github.hydos.lint.Lint;
import io.github.hydos.lint.entity.beetater.BeeTaterEntity;
import io.github.hydos.lint.entity.boss.i5.I509VCB;
import io.github.hydos.lint.entity.boss.kingtater.KingTater;
import io.github.hydos.lint.entity.boss.kingtater.TaterMinion;
import io.github.hydos.lint.entity.tater.LilTaterEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public interface Entities {

    EntityType<KingTater> KING_TATER =
            Registry.register(Registry.ENTITY_TYPE, Lint.id("king_tater"), FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, KingTater::new)
                    .dimensions(EntityDimensions.changing(2f, 2f))
                    .build());

    EntityType<I509VCB> I5 =
            Registry.register(Registry.ENTITY_TYPE, Lint.id("i509vcb"), FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, I509VCB::new)
                    .dimensions(EntityDimensions.changing(2f, 2f))
                    .build());

    EntityType<BeeTaterEntity> BEE_TATER =
            Registry.register(Registry.ENTITY_TYPE, Lint.id("bee_tater"), FabricEntityTypeBuilder.create(SpawnGroup.AMBIENT, (BeeTaterEntity::new))
                    .dimensions(EntityDimensions.fixed(0.3f, 0.4f))
                    .build());
    EntityType<LilTaterEntity> LIL_TATER =
            Registry.register(Registry.ENTITY_TYPE, Lint.id("lil_tater"), FabricEntityTypeBuilder.create(SpawnGroup.AMBIENT, (LilTaterEntity::new))
                    .dimensions(EntityDimensions.fixed(0.3f, 0.4f))
                    .build());
    EntityType<TaterMinion> MINION =
            Registry.register(Registry.ENTITY_TYPE, Lint.id("minion"), FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, (EntityType<TaterMinion> type, World world) -> new TaterMinion(type, world, null))
                    .dimensions(LIL_TATER.getDimensions())
                    .build());

    static void initialize() {
        FabricDefaultAttributeRegistry.register(Entities.LIL_TATER, LilTaterEntity.initAttributes());
        FabricDefaultAttributeRegistry.register(Entities.BEE_TATER, LilTaterEntity.initAttributes());
        FabricDefaultAttributeRegistry.register(Entities.MINION, TaterMinion.initAttributes());

        FabricDefaultAttributeRegistry.register(Entities.KING_TATER, KingTater.initAttributes());
        FabricDefaultAttributeRegistry.register(Entities.I5, I509VCB.initAttributes());
    }
}
