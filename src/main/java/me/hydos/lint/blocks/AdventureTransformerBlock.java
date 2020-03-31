package me.hydos.lint.blocks;

import me.hydos.lint.dimensions.DimensionManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class AdventureTransformerBlock extends Block {


    public AdventureTransformerBlock(Settings settings) {
        super(settings);
        BlockRenderLayerMap.INSTANCE.putBlock(this, RenderLayer.getTranslucent());
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        player.changeDimension(DimensionManager.HAYKAM);
        return super.onUse(state, world, pos, player, hand, hit);
    }
}
