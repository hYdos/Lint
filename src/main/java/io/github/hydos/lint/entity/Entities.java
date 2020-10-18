package io.github.hydos.lint.entity;

import io.github.hydos.lint.Lint;
import io.github.hydos.lint.entity.beetater.BeeTaterEntity;
import io.github.hydos.lint.entity.boss.kingtater.KingTater;
import io.github.hydos.lint.entity.boss.kingtater.TaterMinion;
import io.github.hydos.lint.entity.tater.LilTaterEntity;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

//TODO: port to new fabric entity type builder
public class Entities implements ModInitializer {

    public static final EntityType<BeeTaterEntity> BEE_TATER =
            Registry.register(Registry.ENTITY_TYPE, Lint.id("bee_tater"), FabricEntityTypeBuilder.create(SpawnGroup.AMBIENT, (BeeTaterEntity::new))
                    .size(EntityDimensions.fixed(0.3f, 0.4f))
                    .build());
    public static final EntityType<LilTaterEntity> LIL_TATER =
            Registry.register(Registry.ENTITY_TYPE, Lint.id("lil_tater"), FabricEntityTypeBuilder.create(SpawnGroup.AMBIENT, (LilTaterEntity::new))
                    .size(EntityDimensions.fixed(0.3f, 0.4f))
                    .build());
    public static final EntityType<KingTater> KING_TATER =
            Registry.register(Registry.ENTITY_TYPE, Lint.id("king_tater"), FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, KingTater::new)
                    .size(EntityDimensions.changing(2f, 2f))
                    .build());
    public static final EntityType<TaterMinion> MINION =
            Registry.register(Registry.ENTITY_TYPE, Lint.id("minion"), FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, (EntityType<TaterMinion> type, World world) -> new TaterMinion(type, world, null))
                    .size(LIL_TATER.getDimensions())
                    .build());

    @Override
    public void onInitialize() {
        FabricDefaultAttributeRegistry.register(Entities.LIL_TATER, LilTaterEntity.initAttributes());
        FabricDefaultAttributeRegistry.register(Entities.BEE_TATER, LilTaterEntity.initAttributes());
        FabricDefaultAttributeRegistry.register(Entities.MINION, TaterMinion.initAttributes());
        FabricDefaultAttributeRegistry.register(Entities.KING_TATER, KingTater.initAttributes());
    }
}
