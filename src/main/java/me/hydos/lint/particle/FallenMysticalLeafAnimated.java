package me.hydos.lint.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.FabricSpriteProvider;
import net.minecraft.client.particle.AnimatedParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class FallenMysticalLeafAnimated {

    @Environment(EnvType.CLIENT)
    public static class FallenMysticalLeaf extends AnimatedParticle {

        protected FallenMysticalLeaf(World world, double x, double y, double z, SpriteProvider sprites) {
            super((ClientWorld) world, x, y, z, sprites, -3.0E-3F);
            this.age = 0;
            this.maxAge = 80;//
            setSprite(sprites.getSprite(world.random));
        }

        @Environment(EnvType.CLIENT)
        public static class Factory implements ParticleFactory<DefaultParticleType> {

            private final FabricSpriteProvider sprites;

            public Factory(FabricSpriteProvider sprites) {
                this.sprites = sprites;
            }

            @Nullable
            @Override
            public Particle createParticle(DefaultParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
                return new FallenMysticalLeaf(world, x, y, z, sprites);
            }
        }
    }
}
