package me.hydos.techrebornApi.blocks;

import me.hydos.lint.Lint;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import reborncore.api.blockentity.IMachineGuiHandler;
import techreborn.blockentity.machine.GenericMachineBlockEntity;
import techreborn.blocks.GenericMachineBlock;

import java.util.function.Supplier;

/**
 *
 * used to make blocks which are machines which are compatible with the TR system
 *
 * @author hydos06
 */
public class TRMachineBlock extends GenericMachineBlock {

    public TRMachineBlock(boolean nonOpaque, IMachineGuiHandler gui, Supplier<BlockEntity> blockEntityClass) {//TODO: make non-Opaque setting work
        super(FabricBlockSettings.of(Material.METAL).nonOpaque().build(), gui, blockEntityClass);

        BlockRenderLayerMap.INSTANCE.putBlock(this, RenderLayer.getTranslucent());
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        if(world.getDimension().getType().getSuffix().equals("lint_haykam")){
            return false;
        }
        return true;
    }


}
