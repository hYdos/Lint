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

package me.hydos.lint.block;

import me.hydos.lint.util.Power;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.Explosion.DestructionType;

public class InfusedBlock extends Block {
	private final Power power;

	public InfusedBlock(Settings settings, Power power) {
		super(settings);
		this.power = power;
	}

	@Override
	public void onPlaced(World world, BlockPos pos0, BlockState state, LivingEntity placer, ItemStack itemStack) {
		if (!world.isClient()) {
			for (Direction dir : Direction.values()) {
				Block b = world.getBlockState(pos0.add(dir.getOffsetX(), dir.getOffsetY(), dir.getOffsetZ())).getBlock();

				if (b instanceof InfusedBlock) {
					if (this.power.conflicts(((InfusedBlock) b).power)) {
						world.createExplosion(null, pos0.getX() + 0.5, pos0.getY() + 0.5, pos0.getZ() + 0.5, 8.0F, this.power == Power.ALLOS ? DestructionType.BREAK : DestructionType.DESTROY);
					}
				}
			}
		}
	}

	@Override
	public void onDestroyedByExplosion(World world, BlockPos pos, Explosion explosion) {
		if (!world.isClient()) {
			if (world.random.nextBoolean()) {
				world.createExplosion(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 8.0F, this.power == Power.ALLOS ? DestructionType.BREAK : DestructionType.DESTROY);
			}
		}
	}

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
		if (entity instanceof LivingEntity) {
			if (!entity.isFireImmune() && !EnchantmentHelper.hasFrostWalker((LivingEntity) entity)) {
				entity.damage(DamageSource.DRYOUT, 4.0F);
			}

			switch (this.power) {
				case ALLOS:
					((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 160));
					break;
				case MANOS:
					((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 160));
					break;
				case NONE:
				default:
					break;
			}
		}
	}
}
