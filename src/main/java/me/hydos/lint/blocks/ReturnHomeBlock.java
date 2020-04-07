package me.hydos.lint.blocks;

import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class ReturnHomeBlock extends Block {
	public ReturnHomeBlock(Settings settings) {
		super(settings);

		this.setDefaultState(this.stateManager.getDefaultState().with(ACTIVATED, false));
	}

	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(ACTIVATED);
	}

	@Override
	public boolean onBlockAction(BlockState state, World world, BlockPos pos, int type, int data) {
		if (state.get(ACTIVATED)) {
			world.getEntities(LivingEntity.class, new Box(pos).expand(3.0), le -> true).forEach(le -> {
				if (world instanceof ServerWorld) {
					FabricDimensions.teleport(le, DimensionType.OVERWORLD);
					BlockPos aPos = world.getServer().getWorld(DimensionType.OVERWORLD).getTopPosition(Heightmap.Type.MOTION_BLOCKING, pos);
					le.setPos(aPos.getX() + 0.5, aPos.getY() + 0.5, aPos.getZ() + 0.5);
				}
			});
			return true;
		}

		return false;
	}

	public static final BooleanProperty ACTIVATED = BooleanProperty.of("activated");
}
