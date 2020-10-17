package io.github.hydos.lint.block;

import io.github.hydos.lint.block.Blocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class LintCorruptGrassBlock extends FlowerBlock {
    public LintCorruptGrassBlock(StatusEffect effect, Settings settings) {
        super(effect, 7, settings);

        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            BlockRenderLayerMap.INSTANCE.putBlock(this, RenderLayer.getTranslucent());
        }
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity) {
            entity.damage(DamageSource.CACTUS, 1.0F);
            ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 30));
            world.breakBlock(pos, false);
        }
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView view, BlockPos pos) {
        return floor == Blocks.CORRUPT_GRASS.getDefaultState();
    }
}
