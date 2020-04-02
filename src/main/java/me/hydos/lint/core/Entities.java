package me.hydos.lint.core;

import me.hydos.lint.entities.liltaterbattery.LilTaterBattery;
import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public interface Entities {

    EntityType<LilTaterBattery> LIL_TATER =
            Registry.register(Registry.ENTITY_TYPE, new Identifier("lint", "lil_tater"), FabricEntityTypeBuilder.<LilTaterBattery>create(EntityCategory.AMBIENT, ((type, world) -> new LilTaterBattery(world)))
                            .size(EntityDimensions.fixed(0.3f, 0.4f))
                            .build());

    static void onInitialize(){

    }
}
