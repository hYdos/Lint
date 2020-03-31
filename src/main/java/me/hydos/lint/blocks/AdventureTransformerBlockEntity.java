package me.hydos.lint.blocks;

import me.hydos.lint.Lint;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import reborncore.client.containerBuilder.IContainerProvider;
import reborncore.client.containerBuilder.builder.BuiltContainer;
import reborncore.client.containerBuilder.builder.ContainerBuilder;
import reborncore.common.fluid.FluidValue;
import reborncore.common.util.RebornInventory;
import reborncore.common.util.Tank;
import techreborn.blockentity.machine.GenericMachineBlockEntity;
import techreborn.blockentity.machine.multiblock.MultiblockChecker;
import techreborn.config.TechRebornConfig;
import techreborn.init.TRContent;
import techreborn.utils.FluidUtils;

public class AdventureTransformerBlockEntity extends GenericMachineBlockEntity implements IContainerProvider {
    public static final FluidValue TANK_CAPACITY;
    public Tank tank;
    public MultiblockChecker multiblockChecker;
    int ticksSinceLastChange;

    public AdventureTransformerBlockEntity() {
        super(Lint.ADVENTURE_TRANSFORMER_BLOCK_ENTITY, "Adventure Transformer", TechRebornConfig.industrialGrinderMaxInput, TechRebornConfig.industrialGrinderMaxEnergy, TRContent.Machine.INDUSTRIAL_GRINDER.block, 7);
        int[] inputs = new int[]{0, 1};
        int[] outputs = new int[]{2, 3, 4, 5};
        this.inventory = new RebornInventory(8, "AdventureTransformerBlockEntity", 64, this);
//        this.crafter = new RecipeCrafter(ModRecipes.INDUSTRIAL_GRINDER, this, 1, 4, this.inventory, inputs, outputs); //TODO: fix this
        this.tank = new Tank("AdventureTransformerBlockEntity", TANK_CAPACITY, this);
        this.ticksSinceLastChange = 0;
    }

    public boolean getMultiBlock() {
        if (this.multiblockChecker == null) {
            return false;
        } else {
            boolean down = this.multiblockChecker.checkRectY(1, 1, "standard", MultiblockChecker.ZERO_OFFSET);
            boolean up = this.multiblockChecker.checkRectY(1, 1, "standard", new BlockPos(0, 2, 0));
            boolean blade = this.multiblockChecker.checkRingY(1, 1, "advanced", new BlockPos(0, 1, 0));
            BlockState centerBlock = this.multiblockChecker.getBlock(0, 1, 0);
            boolean center = (centerBlock.getBlock() instanceof FluidBlock || centerBlock.getBlock() instanceof FluidBlock) && centerBlock.getMaterial() == Material.WATER;
            return down && center && blade && up;
        }
    }

    public void tick() {
        if (this.multiblockChecker == null) {
            BlockPos downCenter = this.pos.offset(this.getFacing().getOpposite(), 2).offset(Direction.DOWN, 1);
            this.multiblockChecker = new MultiblockChecker(this.world, downCenter);
        }

        ++this.ticksSinceLastChange;
        if (!this.world.isClient && this.ticksSinceLastChange >= 10) {
            if (!this.inventory.getInvStack(1).isEmpty()) {
                FluidUtils.drainContainers(this.tank, this.inventory, 1, 6);
                FluidUtils.fillContainers(this.tank, this.inventory, 1, 6, this.tank.getFluid());
            }

            this.ticksSinceLastChange = 0;
        }

        super.tick();
    }

    public void fromTag(CompoundTag tagCompound) {
        super.fromTag(tagCompound);
        this.tank.read(tagCompound);
    }

    public CompoundTag toTag(CompoundTag tagCompound) {
        super.toTag(tagCompound);
        this.tank.write(tagCompound);
        return tagCompound;
    }

    public Tank getTank() {
        return this.tank;
    }

    public BuiltContainer createContainer(int syncID, PlayerEntity player) {
        return (new ContainerBuilder("adventure_transformer")).player(player.inventory).inventory().hotbar().addInventory().blockEntity(this).fluidSlot(1, 34, 35).slot(0, 84, 43).outputSlot(2, 126, 18).outputSlot(3, 126, 36).outputSlot(4, 126, 54).outputSlot(5, 126, 72).outputSlot(6, 34, 55).energySlot(7, 8, 72).sync(this.tank).syncEnergyValue().syncCrafterValue().addInventory().create(this, syncID);
    }

    static {
        TANK_CAPACITY = FluidValue.BUCKET.multiply(16);
    }
}
