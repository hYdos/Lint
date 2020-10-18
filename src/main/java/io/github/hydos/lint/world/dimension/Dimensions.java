package io.github.hydos.lint.world.dimension;

import io.github.hydos.lint.Lint;
import io.github.hydos.lint.mixin.DimensionTypeAccessor;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;

import java.util.OptionalLong;

import static net.minecraft.world.dimension.DimensionType.OVERWORLD_ID;

public class Dimensions {

    public static final RegistryKey<DimensionOptions> HAYKAM_DIM_OPTIONS = RegistryKey.of(Registry.DIMENSION_OPTIONS, Lint.id("haykam"));
    public static final RegistryKey<DimensionType> HAYKAM_DIM = RegistryKey.of(Registry.DIMENSION_TYPE_KEY, Lint.id("haykam"));
    public static final DimensionType HAYKAM;

    static {
        HAYKAM = DimensionTypeAccessor.create(
                OptionalLong.empty(),
                true,
                false,
                false,
                true,
                1.0D,
                true,
                true,
                false,
                false,
                256,
                BlockTags.INFINIBURN_OVERWORLD.getId(),
                OVERWORLD_ID,
                0.0F
        );
    }
}
