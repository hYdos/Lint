package io.github.hydos.lint.block;

import io.github.hydos.lint.block.Blocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class LintGrassBlock extends FlowerBlock {
    public LintGrassBlock(StatusEffect effect, Settings settings) {
        super(effect, 7, settings);

        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            BlockRenderLayerMap.INSTANCE.putBlock(this, RenderLayer.getTranslucent());
        }
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView view, BlockPos pos) {
        return floor == Blocks.LIVELY_GRASS.getDefaultState();
    }
}
