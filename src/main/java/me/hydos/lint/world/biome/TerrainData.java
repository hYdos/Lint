package me.hydos.lint.world.biome;

public interface TerrainData {
    double sampleTypeScale(int x, int z);
    double sampleTerrainScale(int x, int z);
    double sampleContinent(int x, int z);
}
