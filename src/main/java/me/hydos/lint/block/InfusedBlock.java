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
	public InfusedBlock(Settings settings, Power power) {
		super(settings);
		this.power = power;
	}

	private final Power power;

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
		if (world.random.nextBoolean()) {
			world.createExplosion(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 8.0F, this.power == Power.ALLOS ? DestructionType.BREAK : DestructionType.DESTROY);
		}
	}

	public void onSteppedOn(World world, BlockPos pos, Entity entity) {
		if (entity instanceof LivingEntity) {
			if (!entity.isFireImmune() && !EnchantmentHelper.hasFrostWalker((LivingEntity)entity)) {
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
