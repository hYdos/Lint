/*
 * Lint
 * Copyright (C) 2020 hYdos, Valoeghese, ramidzkh
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

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
