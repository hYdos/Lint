package me.hydos.lint.dimensions;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.source.*;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.chunk.*;

public class HaykamDimension extends Dimension {

    public HaykamDimension(World world, DimensionType type) {
        super(world, type, 0.5f);
    }

    private static final Vec3d FOG_COLOR = new Vec3d(173/256, 178/256, 186/256);

    @Override
    public ChunkGenerator<?> createChunkGenerator() {
        // For now, we'll create a superflat world to get a basic dimension working.
        // We'll come back and change this later.
        FlatChunkGeneratorConfig generatorConfig = FlatChunkGeneratorConfig.getDefaultConfig();
        // The biome everywhere will be jungle
        FixedBiomeSourceConfig biomeConfig = BiomeSourceType.FIXED.getConfig(world.getLevelProperties()).setBiome(Biomes.JUNGLE);
        return ChunkGeneratorType.FLAT.create(world, BiomeSourceType.FIXED.applyConfig(biomeConfig), generatorConfig);
    }

    // The following 2 methods relate to the dimension's spawn point.
    // You can return null if you don't want the player to be able to respawn in these dimensions.

    @Override
    public BlockPos getSpawningBlockInChunk(ChunkPos chunkPos, boolean checkMobSpawnValidity) {
        return new BlockPos(0,60,0);
    }

    @Override
    public BlockPos getTopSpawningBlockPosition(int x, int z, boolean checkMobSpawnValidity) {
        return new BlockPos(0,60,0);
    }

    @Override
    public float getSkyAngle(long worldTime, float tickDelta) {
        // Returns a sky angle ranging between 0 and 1.
        // This is a very simple implementation that approximates the overworld sky angle, but is easier to understand.
        // In the overworld, the sky does not quite move at a constant rate, see the OverworldDimension code for details.
        final int dayLength = 24000;
        double daysPassed = ((double) worldTime + tickDelta) / dayLength;
        return (float) MathHelper.fractionalPart(daysPassed - 0.25);
    }

    @Override
    public boolean hasVisibleSky() {
        return true;
    }

    // Fog color RGB
    @Environment(EnvType.CLIENT)
    @Override
    public Vec3d getFogColor(float skyAngle, float tickDelta) {
        return FOG_COLOR;
    }

    @Override
    public boolean canPlayersSleep() {
        return true;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public boolean isFogThick(int x, int z) {
        return false;
    }

    @Override
    public DimensionType getType() {
        return DimensionManager.HAYKAM;
    }
}