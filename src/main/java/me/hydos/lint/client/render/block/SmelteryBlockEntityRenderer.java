package me.hydos.lint.client.render.block;

import me.hydos.lint.block.entity.SmelteryBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.fluid.FluidState;

public class SmelteryBlockEntityRenderer extends BlockEntityRenderer<SmelteryBlockEntity> {

	public SmelteryBlockEntityRenderer(BlockEntityRenderDispatcher dispatcher) {
		super(dispatcher);
	}

	@Override
	public void render(SmelteryBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		if(entity.center != null || !entity.isLit()){
			FluidState fluidState = entity.fluidData.get(0);
			MinecraftClient.getInstance().getBlockRenderManager().renderFluid(
					entity.center,
					entity.getWorld(),
					vertexConsumers.getBuffer(RenderLayers.getFluidLayer(fluidState)),
					fluidState
			);
		}
	}
}
