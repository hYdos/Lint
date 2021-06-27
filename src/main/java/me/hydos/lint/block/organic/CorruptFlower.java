package me.hydos.lint.block.organic;

import me.hydos.lint.block.LintBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CorruptFlower extends LintFlowerBlock {
	public CorruptFlower(StatusEffect effect, Settings settings) {
		super(effect, settings);
	}

	@Override
	public BlockState getGrowsOn() {
		return LintBlocks.CORRUPT_GRASS.getDefaultState();
	}

	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if (entity instanceof LivingEntity livingEntity) {
			if (((entity instanceof PlayerEntity) && ((PlayerEntity) entity).isCreative()) || (entity instanceof HostileEntity))
				return;

			entity.damage(DamageSource.CACTUS, 1.0F); // TODO: use something like SWEET_BERRY_BUSH instead of CACTUS
			livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 30));
			world.breakBlock(pos, false);
		}
	}
}
