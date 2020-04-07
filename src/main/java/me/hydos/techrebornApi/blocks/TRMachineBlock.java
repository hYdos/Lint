package me.hydos.techrebornApi.blocks;

import me.hydos.lint.core.Dimensions;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import reborncore.api.blockentity.IMachineGuiHandler;
import techreborn.blocks.GenericMachineBlock;

import java.util.function.Supplier;

/**
 * used to make blocks which are machines which are compatible with the TR system
 *
 * @author hydos06
 */
public class TRMachineBlock extends GenericMachineBlock {

    public TRMachineBlock(IMachineGuiHandler gui, Supplier<BlockEntity> blockEntityClass) {//TODO: make non-Opaque setting work
        super(FabricBlockSettings.of(Material.METAL).nonOpaque().build(), gui, blockEntityClass);

        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            BlockRenderLayerMap.INSTANCE.putBlock(this, RenderLayer.getTranslucent());
        }
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getDimension().getType() != Dimensions.HAYKAM;
    }
}
