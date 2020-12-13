package io.github.hydos.lint.world.gen;

import java.util.Arrays;
import java.util.Random;

public class OctaveHaykamNoiseSampler {

    private final HaykamNoiseSampler[] generators;
    private final int octaves;

    public OctaveHaykamNoiseSampler(Random rand, int octaveCount) {
        this.octaves = octaveCount;
        this.generators = new HaykamNoiseSampler[octaveCount];
        for (int i = 0; i < octaveCount; ++i) {
            this.generators[i] = new HaykamNoiseSampler(rand);
        }
    }

    public double sample(double x, double y) {
        double double6 = 0.0;
        double double8 = 1.0;
        for (int i = 0; i < this.octaves; ++i) {
            double6 += this.generators[i].sample(x * double8, y * double8) / double8;
            double8 /= 2.0;
        }
        return double6;
    }

    public double[] sample(double[] arrayToReuse, double startX, double startY, double startZ, int xWidth, int yHeight, int zWidth, double xScale, double yScale, double zScale) {
        if (arrayToReuse == null) {
            arrayToReuse = new double[xWidth * yHeight * zWidth];
        } else {
            Arrays.fill(arrayToReuse, 0.0);
        }

        double scale = 1.0;

        for (int j = 0; j < this.octaves; ++j) {
            this.generators[j].sample(arrayToReuse, startX, startY, startZ, xWidth, yHeight, zWidth, xScale * scale, yScale * scale, zScale * scale, scale);
            scale /= 2.0;
        }

        return arrayToReuse;
    }
}
