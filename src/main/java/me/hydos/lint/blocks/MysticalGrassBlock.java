package me.hydos.lint.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.FlowerBlock;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.effect.StatusEffects;

public class MysticalGrassBlock extends FlowerBlock {

    public MysticalGrassBlock(Settings settings) {
        super(StatusEffects.BAD_OMEN, 7, settings);
        if(FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT){
            BlockRenderLayerMap.INSTANCE.putBlock(this, RenderLayer.getTranslucent());
        }
    }


}
