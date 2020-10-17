package io.github.hydos.lint.mixin;

import com.mojang.serialization.Lifecycle;
import io.github.hydos.lint.world.dimension.Dimensions;
import io.github.hydos.lint.world.gen.HaykamChunkGenerator;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DimensionType.class)
public class DimensionTypeMixin {

    @Inject(method = "addRegistryDefaults", at = @At("TAIL"))
    private static void addAdditionalDefaults(DynamicRegistryManager.Impl registryManager, CallbackInfoReturnable<DynamicRegistryManager.Impl> cir) {
        Registry.register(registryManager.getDimensionTypes(), Dimensions.HAYKAM_DIM.getValue(), Dimensions.HAYKAM);
    }

    @Inject(method = "createDefaultDimensionOptions", at = @At("TAIL"))
    private static void addAdditionalDefaultDimensionOptions(Registry<DimensionType> dimensionRegistry, Registry<Biome> biomeRegistry, Registry<ChunkGeneratorSettings> chunkGeneratorSettingsRegistry, long seed, CallbackInfoReturnable<SimpleRegistry<DimensionOptions>> cir) {
        cir.getReturnValue().add(Dimensions.HAYKAM_DIM_OPTIONS, new DimensionOptions(() -> dimensionRegistry.getOrThrow(Dimensions.HAYKAM_DIM), new HaykamChunkGenerator(seed, biomeRegistry)), Lifecycle.stable());
    }
}
