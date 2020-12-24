package me.hydos.lint.world.gen;

import java.util.Random;

import net.minecraft.util.math.MathHelper;

public class HaykamNoiseSampler {

    public final double offsetX;
    public final double offsetY;
    public final double offsetZ;
    private final int[] p;

    public HaykamNoiseSampler() {
        this(new Random());
    }

    public HaykamNoiseSampler(Random rand) {
        this.p = new int[512];
        this.offsetX = rand.nextDouble() * 256.0;
        this.offsetY = rand.nextDouble() * 256.0;
        this.offsetZ = rand.nextDouble() * 256.0;
        for (int index = 0; index < 256; ++index) {
            this.p[index] = index;
        }
        for (int index = 0; index < 256; ++index) {
            int newIndex = rand.nextInt(256 - index) + index;
            int value = this.p[index];
            this.p[index] = this.p[newIndex];
            this.p[newIndex] = value;
            this.p[index + 256] = this.p[index];
        }
    }

    public double sample(double x, double y, double z) {
        double sampleX = x + this.offsetX;
        double sampleY = y + this.offsetY;
        double sampleZ = z + this.offsetZ;
        int floorX = (int) sampleX;
        int floorY = (int) sampleY;
        int floorZ = (int) sampleZ;
        if (sampleX < floorX) {
            --floorX;
        }
        if (sampleY < floorY) {
            --floorY;
        }
        if (sampleZ < floorZ) {
            --floorZ;
        }
        int integer17 = floorX & 0xFF;
        int integer18 = floorY & 0xFF;
        int integer19 = floorZ & 0xFF;
        sampleX -= floorX;
        sampleY -= floorY;
        sampleZ -= floorZ;
        double fadeX = sampleX * sampleX * sampleX * (sampleX * (sampleX * 6.0 - 15.0) + 10.0);
        double fadeY = sampleY * sampleY * sampleY * (sampleY * (sampleY * 6.0 - 15.0) + 10.0);
        double fadeZ = sampleZ * sampleZ * sampleZ * (sampleZ * (sampleZ * 6.0 - 15.0) + 10.0);
        int integer26 = this.p[integer17] + integer18;
        int integer27 = this.p[integer26] + integer19;
        int integer28 = this.p[integer26 + 1] + integer19;
        int integer29 = this.p[integer17 + 1] + integer18;
        int integer30 = this.p[integer29] + integer19;
        int integer31 = this.p[integer29 + 1] + integer19;
        return this.lerp(fadeZ, this.lerp(fadeY, this.lerp(fadeX, this.gradient(this.p[integer27], sampleX, sampleY, sampleZ), this.gradient(this.p[integer30], sampleX - 1.0, sampleY, sampleZ)), this.lerp(fadeX, this.gradient(this.p[integer28], sampleX, sampleY - 1.0, sampleZ), this.gradient(this.p[integer31], sampleX - 1.0, sampleY - 1.0, sampleZ))), this.lerp(fadeY, this.lerp(fadeX, this.gradient(this.p[integer27 + 1], sampleX, sampleY, sampleZ - 1.0), this.gradient(this.p[integer30 + 1], sampleX - 1.0, sampleY, sampleZ - 1.0)), this.lerp(fadeX, this.gradient(this.p[integer28 + 1], sampleX, sampleY - 1.0, sampleZ - 1.0), this.gradient(this.p[integer31 + 1], sampleX - 1.0, sampleY - 1.0, sampleZ - 1.0))));
    }

    public double lerp(double progress, double low, double high) {
        return low + progress * (high - low);
    }

    public double gradient(int integer, double double3, double double5, double double7) {
        int integer9 = integer & 0xF;
        double double10 = (integer9 < 8) ? double3 : double5;
        double double12 = (integer9 < 4) ? double5 : ((integer9 == 12 || integer9 == 14) ? double3 : double7);
        return (((integer9 & 0x1) == 0x0) ? double10 : (-double10)) + (((integer9 & 0x2) == 0x0) ? double12 : (-double12));
    }

    public double sample(double x, double y) {
        return this.sample(x, y, 0.0);
    }

    public void sample(double[] arrayToReuse, double startX, double startY, double startZ, int xWidth, int yHeight, int zWidth, double xScale, double yScale, double zScale, double double18) {
        int indexCounter = 0;
        double double21 = 1.0 / double18;
        int integer23 = -1;
        double double30 = 0.0;
        double double32 = 0.0;
        double double34 = 0.0;
        double double36 = 0.0;

        for (int xPos = 0; xPos < xWidth; ++xPos) {
            double xSample = (startX + xPos) * xScale + this.offsetX;
            int x = MathHelper.floor(xSample);

            if (xSample < x) {
                --x;
            }

            int integer42 = x & 0xFF;
            xSample -= x;
            double double43 = xSample * xSample * xSample * (xSample * (xSample * 6.0 - 15.0) + 10.0);

            for (int zPos = 0; zPos < zWidth; ++zPos) {
                double zSample = (startZ + zPos) * zScale + this.offsetZ;
                int z = MathHelper.floor(zSample);

                if (zSample < z) {
                    --z;
                }

                int integer49 = z & 0xFF;
                zSample -= z;
                double double50 = zSample * zSample * zSample * (zSample * (zSample * 6.0 - 15.0) + 10.0);

                for (int yPos = 0; yPos < yHeight; ++yPos) {
                    double ySample = (startY + yPos) * yScale + this.offsetY;
                    int y = (int) ySample;

                    if (ySample < y) {
                        --y;
                    }

                    int integer56 = y & 0xFF;
                    ySample -= y;
                    double double57 = ySample * ySample * ySample * (ySample * (ySample * 6.0 - 15.0) + 10.0);

                    if (yPos == 0 || integer56 != integer23) {
                        integer23 = integer56;
                        int integer24 = this.p[integer42] + integer56;
                        int integer25 = this.p[integer24] + integer49;
                        int integer26 = this.p[integer24 + 1] + integer49;
                        int integer27 = this.p[integer42 + 1] + integer56;
                        int integer28 = this.p[integer27] + integer49;
                        int integer29 = this.p[integer27 + 1] + integer49;
                        double30 = this.lerp(double43, this.gradient(this.p[integer25], xSample, ySample, zSample), this.gradient(this.p[integer28], xSample - 1.0, ySample, zSample));
                        double32 = this.lerp(double43, this.gradient(this.p[integer26], xSample, ySample - 1.0, zSample), this.gradient(this.p[integer29], xSample - 1.0, ySample - 1.0, zSample));
                        double34 = this.lerp(double43, this.gradient(this.p[integer25 + 1], xSample, ySample, zSample - 1.0), this.gradient(this.p[integer28 + 1], xSample - 1.0, ySample, zSample - 1.0));
                        double36 = this.lerp(double43, this.gradient(this.p[integer26 + 1], xSample, ySample - 1.0, zSample - 1.0), this.gradient(this.p[integer29 + 1], xSample - 1.0, ySample - 1.0, zSample - 1.0));
                    }

                    double double63 = this.lerp(double50, this.lerp(double57, double30, double32), this.lerp(double57, double34, double36));
                    int index = indexCounter++;
                    arrayToReuse[index] += double63 * double21;
                }
            }
        }
    }
}
