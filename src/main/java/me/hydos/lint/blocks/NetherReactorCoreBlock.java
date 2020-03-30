package me.hydos.lint.blocks;

import me.hydos.lint.dimensions.DimensionManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class NetherReactorCoreBlock extends Block {


    public NetherReactorCoreBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        super.onBlockBreakStart(state, world, pos, player);
        System.out.println(DimensionManager.HAYKAM);
        player.changeDimension(DimensionManager.HAYKAM);
    }



    }
