package me.hydos.lint.blocks;

import me.hydos.lint.core.Blocks;
import me.hydos.lint.core.Entities;
import me.hydos.lint.entities.boss.KingTater;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class KingTaterButton extends Block {

    public KingTaterButton(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(state != Blocks.GREEN_BUTTON.getDefaultState()){
            world.setBlockState(pos, Blocks.GREEN_BUTTON.getDefaultState());
            KingTater kingTater = new KingTater(Entities.KING_TATER, world);
            kingTater.refreshPositionAndAngles(pos, 0, 0);
            world.spawnEntity(kingTater);
            return ActionResult.SUCCESS;
        }else{
            return ActionResult.FAIL;
        }

    }
}
