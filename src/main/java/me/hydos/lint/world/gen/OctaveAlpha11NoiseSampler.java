package me.hydos.lint.world.gen;

import java.util.Arrays;
import java.util.Random;

public class OctaveAlpha11NoiseSampler {

    private final Alpha11NoiseSampler[] generators;
    private final int octaves;

    public OctaveAlpha11NoiseSampler(Random rand, int octaveCount) {
        this.octaves = octaveCount;
        this.generators = new Alpha11NoiseSampler[octaveCount];
        for (int i = 0; i < octaveCount; ++i) {
            this.generators[i] = new Alpha11NoiseSampler(rand);
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

    public double[] sample(double[] arrayToReuse, double double3, double double5, double double7, int integer9, int integer10, int integer11, double double12, double double14, double double16) {
        if (arrayToReuse == null) {
            arrayToReuse = new double[integer9 * integer10 * integer11];
        } else {
            Arrays.fill(arrayToReuse, 0.0);
        }
        double double18 = 1.0;
        for (int j = 0; j < this.octaves; ++j) {
            this.generators[j].sample(arrayToReuse, double3, double5, double7, integer9, integer10, integer11, double12 * double18, double14 * double18, double16 * double18, double18);
            double18 /= 2.0;
        }
        return arrayToReuse;
    }
}