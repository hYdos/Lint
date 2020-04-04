package me.hydos.lint.unprotect;

import net.minecraft.block.*;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class TrapdoorBlock extends HorizontalFacingBlock implements Waterloggable {
    public static final BooleanProperty OPEN;
    public static final EnumProperty<BlockHalf> HALF;
    public static final BooleanProperty POWERED;
    public static final BooleanProperty WATERLOGGED;
    protected static final VoxelShape EAST_SHAPE;
    protected static final VoxelShape WEST_SHAPE;
    protected static final VoxelShape SOUTH_SHAPE;
    protected static final VoxelShape NORTH_SHAPE;
    protected static final VoxelShape OPEN_BOTTOM_SHAPE;
    protected static final VoxelShape OPEN_TOP_SHAPE;

    public TrapdoorBlock(Block.Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FACING, Direction.NORTH)).with(OPEN, false)).with(HALF, BlockHalf.BOTTOM)).with(POWERED, false)).with(WATERLOGGED, false));
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext ePos) {
        if (!(Boolean)state.get(OPEN)) {
            return state.get(HALF) == BlockHalf.TOP ? OPEN_TOP_SHAPE : OPEN_BOTTOM_SHAPE;
        } else {
            switch((Direction)state.get(FACING)) {
                case NORTH:
                default:
                    return NORTH_SHAPE;
                case SOUTH:
                    return SOUTH_SHAPE;
                case WEST:
                    return WEST_SHAPE;
                case EAST:
                    return EAST_SHAPE;
            }
        }
    }

    public boolean canPlaceAtSide(BlockState world, BlockView view, BlockPos pos, BlockPlacementEnvironment env) {
        switch(env) {
            case LAND:
                return (Boolean)world.get(OPEN);
            case WATER:
                return (Boolean)world.get(WATERLOGGED);
            case AIR:
                return (Boolean)world.get(OPEN);
            default:
                return false;
        }
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (this.material == Material.METAL) {
            return ActionResult.PASS;
        } else {
            state = (BlockState)state.cycle(OPEN);
            world.setBlockState(pos, state, 2);
            if ((Boolean)state.get(WATERLOGGED)) {
                world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
            }

            this.playToggleSound(player, world, pos, (Boolean)state.get(OPEN));
            return ActionResult.SUCCESS;
        }
    }

    protected void playToggleSound(PlayerEntity player, World world, BlockPos pos, boolean open) {
        int i;
        if (open) {
            i = this.material == Material.METAL ? 1037 : 1007;
            world.playLevelEvent(player, i, pos, 0);
        } else {
            i = this.material == Material.METAL ? 1036 : 1013;
            world.playLevelEvent(player, i, pos, 0);
        }

    }

    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos neighborPos, boolean moved) {
        if (!world.isClient) {
            boolean bl = world.isReceivingRedstonePower(pos);
            if (bl != (Boolean)state.get(POWERED)) {
                if ((Boolean)state.get(OPEN) != bl) {
                    state = (BlockState)state.with(OPEN, bl);
                    this.playToggleSound((PlayerEntity)null, world, pos, bl);
                }

                world.setBlockState(pos, (BlockState)state.with(POWERED, bl), 2);
                if ((Boolean)state.get(WATERLOGGED)) {
                    world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
                }
            }

        }
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = this.getDefaultState();
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        Direction direction = ctx.getSide();
        if (!ctx.canReplaceExisting() && direction.getAxis().isHorizontal()) {
            blockState = (BlockState)((BlockState)blockState.with(FACING, direction)).with(HALF, ctx.getHitPos().y - (double)ctx.getBlockPos().getY() > 0.5D ? BlockHalf.TOP : BlockHalf.BOTTOM);
        } else {
            blockState = (BlockState)((BlockState)blockState.with(FACING, ctx.getPlayerFacing().getOpposite())).with(HALF, direction == Direction.UP ? BlockHalf.BOTTOM : BlockHalf.TOP);
        }

        if (ctx.getWorld().isReceivingRedstonePower(ctx.getBlockPos())) {
            blockState = (BlockState)((BlockState)blockState.with(OPEN, true)).with(POWERED, true);
        }

        return (BlockState)blockState.with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, OPEN, HALF, POWERED, WATERLOGGED);
    }

    public FluidState getFluidState(BlockState state) {
        return (Boolean)state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, IWorld world, BlockPos pos, BlockPos neighborPos) {
        if ((Boolean)state.get(WATERLOGGED)) {
            world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return super.getStateForNeighborUpdate(state, facing, neighborState, world, pos, neighborPos);
    }

    public boolean allowsSpawning(BlockState state, BlockView view, BlockPos pos, EntityType<?> type) {
        return false;
    }

    static {
        OPEN = Properties.OPEN;
        HALF = Properties.BLOCK_HALF;
        POWERED = Properties.POWERED;
        WATERLOGGED = Properties.WATERLOGGED;
        EAST_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 3.0D, 16.0D, 16.0D);
        WEST_SHAPE = Block.createCuboidShape(13.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
        SOUTH_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 3.0D);
        NORTH_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D);
        OPEN_BOTTOM_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D);
        OPEN_TOP_SHAPE = Block.createCuboidShape(0.0D, 13.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    }
}