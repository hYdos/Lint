package me.hydos.techrebornApi.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import techreborn.blockentity.machine.GenericMachineBlockEntity;

/**
 *
 * used to make blocks which are machines which are compatible with the TR system
 *
 * @author hydos06
 */
public class TRMachineBlock extends GenericMachineBlockEntity{

    public TRMachineBlock(BlockEntityType<?> blockEntityType, String name, int maxInput, int maxEnergy, Block toolDrop, int energySlot) {
        super(blockEntityType, name, maxInput, maxEnergy, toolDrop, energySlot);
    }
}
