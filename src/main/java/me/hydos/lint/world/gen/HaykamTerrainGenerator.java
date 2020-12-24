package me.hydos.lint.world.gen;

import java.util.Random;

import io.github.hydos.lint.util.DoubleGridOperator;
import io.github.hydos.lint.util.LossyDoubleCache;
import io.github.hydos.lint.util.OpenSimplexNoise;
import me.hydos.lint.world.biome.TerrainData;
import net.minecraft.util.math.MathHelper;

public class HaykamTerrainGenerator implements TerrainData {
    private OpenSimplexNoise continentNoise;
    private OpenSimplexNoise mountainsNoise;
    private OpenSimplexNoise hillsNoise;
    private OpenSimplexNoise scaleNoise;
    private OpenSimplexNoise cliffsNoise;
    private OpenSimplexNoise riverNoise;
    private OpenSimplexNoise terrainDeterminerNoise;

    private DoubleGridOperator continentOperator;
    private DoubleGridOperator scaleOperator;

    HaykamTerrainGenerator(Random rand) {
        this.continentNoise = new OpenSimplexNoise(rand);
        this.mountainsNoise = new OpenSimplexNoise(rand);
        this.hillsNoise = new OpenSimplexNoise(rand);
        this.scaleNoise = new OpenSimplexNoise(rand);
        this.cliffsNoise = new OpenSimplexNoise(rand);
        this.riverNoise = new OpenSimplexNoise(rand);
        this.terrainDeterminerNoise = new OpenSimplexNoise(rand);

        this.continentOperator = new LossyDoubleCache(512, (x, z) -> Math.min(30, 30 * this.continentNoise.sample(x * 0.001, z * 0.001)
                + 18 * Math.max(0, (1.0 - 0.004 * manhattan(x, z, 0, 0))))); // make sure area around 0,0 is higher, but does not go higher than continent noise should go);
        this.scaleOperator = new LossyDoubleCache(512, (x, z) -> {
            double continent = Math.max(0, this.continentOperator.get(x, z)); // min 0
            double scale = (0.5 * this.scaleNoise.sample(x * 0.003, z * 0.003)) + 1.0; // 0 - 1
            scale = 30 * scale + continent; // continent 0-30, scaleNoise 0-30. Overall, 0-60.

            return scale; // should be range 0-60 on return
        });
    }

    public int getHeight(int x, int z) {
        return riverMod(x, z, this.getBaseHeight(x, z));
    }

    private int riverMod(int x, int z, int height) {
        double riverNoise = this.riverNoise.sample(x * 0.003, z * 0.003);

        if (riverNoise > -0.12 && riverNoise < 0.12) {
            int riverHeight = SEA_LEVEL - 3 - (int) (3 * this.sampleHillsNoise(x, z));

            // 1 / 0.12 = 8.333...
            height = (int) MathHelper.lerp(MathHelper.perlinFade((Math.abs(riverNoise) * 8.333333)), riverHeight, height);
        }

        return height;
    }

    private int getBaseHeight(int x, int z) {
        double continent = 3 + 1.2 * this.continentOperator.get(x, z);

        double typeScale = 0.0;
        double heightScale = 0.0;
        int count = 0;

        for (int xo = -SCALE_SMOOTH_RADIUS; xo <= SCALE_SMOOTH_RADIUS; ++xo) {
            int sx = x + xo; // shifted/sample x

            for (int zo = -SCALE_SMOOTH_RADIUS; zo <= SCALE_SMOOTH_RADIUS; ++zo) {
                int sz = z + zo; // shifted/sample z
                double current = this.scaleOperator.get(sx, sz);
                typeScale += current;

                current = applyScaleModifiers(sx, sz, current);

                heightScale += current;
                ++count;
            }
        }

        typeScale /= count;
        heightScale /= count;

        if (typeScale > 40) {
            double mountains = this.sampleMountainsNoise(x, z);

            if (mountains < 0) {
                heightScale /= 2;
            }

            return AVG_HEIGHT + (int) (continent + heightScale * mountains);
        } else if (typeScale < 35) {
            double hills = this.sampleHillsNoise(x, z);

            if (hills < 0) {
                heightScale /= 2;
            }

            return AVG_HEIGHT + (int) (continent + heightScale * hills);
        } else { // fade region from mountains to hills
            double mountainsScale = (typeScale - 35) * 0.2 * heightScale; // 35 -> 0. 40 -> full scale.
            double hillsScale = (40 - typeScale) * 0.2 * heightScale; // 40 -> 0. 35 -> full scale.

            double mountains = this.sampleMountainsNoise(x, z);
            double hills = this.sampleHillsNoise(x, z);

            if (mountains < 0) {
                mountainsScale /= 2;
            }   

            if (hills < 0) {
                hillsScale /= 2;
            }

            mountains = mountainsScale * mountains;
            hills = hillsScale * hills;

            return AVG_HEIGHT + (int) (continent + mountains + hills);
        }
    }

    private double applyScaleModifiers(int x, int z, double scale) {
        if (scale > 30 && this.terrainDeterminerNoise.sample(x * 0.0041, z * 0.0041) > 0.325) { // approx 240 blocks period
            scale -= 35;
            scale = Math.max(0, scale);
        }

        return scale;
    }

    private double sampleHillsNoise(int x, int z) {
        double sample1 = 0.67 * this.hillsNoise.sample(x * 0.0105, z * 0.0105); // period: ~95
        double sample2 = 0.33 * this.hillsNoise.sample(x * 0.025, z * 0.025); // period: 40
        return sample1 + sample2;
    }

    private double sampleMountainsNoise(int x, int z) {
        double bias = this.terrainDeterminerNoise.sample(1 + 0.001 * x, 0.001 * z) * 0.2;
        double sample1;

        if (bias > 0) {
            sample1 = this.mountainsNoise.sample(0.004 * x * (1.0 + bias), 0.004 * z);
        } else {
            sample1 = this.mountainsNoise.sample(0.004 * x, 0.004 * z * (1.0 - bias));
        }

        sample1 = 0.75 - 1.5 * Math.abs(sample1); // ridged +/-0.75
        double sample2 = 0.25 - 0.5 * Math.abs(this.mountainsNoise.sample(0.0076 * x, 1 + 0.0076 * z)); // ridged +/- 0.25
        return sample1 + sample2;
    }

    private static double manhattan(double x, double y, double x1, double y1) {
        double dx = Math.abs(x1 - x);
        double dy = Math.abs(y1 - y);
        return dx + dy;
    }

    private static final int AVG_HEIGHT = 65;
    private static final int SCALE_SMOOTH_RADIUS = 15;
    public static final int SEA_LEVEL = 63;

    @Override
    public double sampleTypeScale(int x, int z) {
        return this.scaleOperator.get(x, z);
    }

    @Override
    public double sampleTerrainScale(int x, int z) {
        double result = this.scaleOperator.get(x, z);
        return applyScaleModifiers(x, z, result);
    }

    @Override
    public int sampleBaseHeight(int x, int z) {
        return this.getBaseHeight(x, z);
    }
}
