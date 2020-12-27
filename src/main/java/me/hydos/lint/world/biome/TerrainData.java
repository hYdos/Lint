package me.hydos.lint.world.biome;

public interface TerrainData {
    double sampleTypeScale(int x, int z);
    double sampleTerrainScale(int x, int z);
    int sampleBaseHeight(int x, int z);
    int sampleTerraceMod(int x, int z);
}
