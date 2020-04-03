package me.hydos.lint.blocks;

import me.hydos.lint.core.Blocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class MysticalGrassBlock extends FlowerBlock {

    public MysticalGrassBlock(Settings settings) {
        super(StatusEffects.BAD_OMEN, 7, settings);
        if(FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT){
            BlockRenderLayerMap.INSTANCE.putBlock(this, RenderLayer.getTranslucent());
        }
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView view, BlockPos pos) {
        return floor == Blocks.LIVELY_GRASS.getDefaultState();
    }
}
