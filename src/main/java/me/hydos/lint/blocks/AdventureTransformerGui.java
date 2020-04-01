package me.hydos.lint.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import reborncore.client.containerBuilder.builder.BuiltContainer;
import reborncore.client.gui.builder.GuiBase;
import reborncore.client.gui.builder.widget.GuiButtonExtended;
import reborncore.client.gui.guibuilder.GuiBuilder.ProgressDirection;
import reborncore.client.multiblock.Multiblock;
import techreborn.init.TRContent.MachineBlocks;

public class AdventureTransformerGui extends GuiBase<BuiltContainer> {
    AdventureTransformerBlockEntity blockEntity;

    public AdventureTransformerGui(int syncID, PlayerEntity player, AdventureTransformerBlockEntity blockEntity) {
        super(player, blockEntity, blockEntity.createContainer(syncID, player));
        this.blockEntity = blockEntity;
    }

    protected void drawBackground(float f, int mouseX, int mouseY) {
        super.drawBackground(f, mouseX, mouseY);
        Layer layer = Layer.BACKGROUND;
        this.drawSlot(8, 72, layer);
        this.drawSlot(34, 35, layer);
        this.drawSlot(34, 55, layer);
        this.drawSlot(84, 43, layer);
        this.drawSlot(126, 18, layer);
        this.drawSlot(126, 36, layer);
        this.drawSlot(126, 54, layer);
        this.drawSlot(126, 72, layer);
        this.builder.drawJEIButton(this, 158, 5, layer);

    }

    protected void drawForeground(int mouseX, int mouseY) {
        super.drawForeground(mouseX, mouseY);
        Layer layer = Layer.FOREGROUND;
        this.builder.drawProgressBar(this, this.blockEntity.getProgressScaled(100), 100, 105, 47, mouseX, mouseY, ProgressDirection.RIGHT, layer);
        this.addHologramButton(6, 4, 212, layer).clickHandler(this::onClick);

        this.builder.drawMultiEnergyBar(this, 9, 19, (int) this.blockEntity.getEnergy(), (int) this.blockEntity.getMaxPower(), mouseX, mouseY, 0, layer);
    }

    public void onClick(GuiButtonExtended button, double mouseX, double mouseY) {
        if (this.blockEntity.renderMultiblock == null) {
            Multiblock multiblock = new Multiblock();
            BlockState standardCasing = MachineBlocks.BASIC.getCasing().getDefaultState();
            BlockState reinforcedCasing = MachineBlocks.ADVANCED.getCasing().getDefaultState();
            this.addComponent(0, -1, 0, standardCasing, multiblock);
            this.addComponent(1, -1, 0, standardCasing, multiblock);
            this.addComponent(0, -1, 1, standardCasing, multiblock);
            this.addComponent(-1, -1, 0, standardCasing, multiblock);
            this.addComponent(0, -1, -1, standardCasing, multiblock);
            this.addComponent(-1, -1, -1, standardCasing, multiblock);
            this.addComponent(-1, -1, 1, standardCasing, multiblock);
            this.addComponent(1, -1, -1, standardCasing, multiblock);
            this.addComponent(1, -1, 1, standardCasing, multiblock);
            this.addComponent(0, 0, 0, Blocks.WATER.getDefaultState(), multiblock);
            this.addComponent(1, 0, 0, reinforcedCasing, multiblock);
            this.addComponent(0, 0, 1, reinforcedCasing, multiblock);
            this.addComponent(-1, 0, 0, reinforcedCasing, multiblock);
            this.addComponent(0, 0, -1, reinforcedCasing, multiblock);
            this.addComponent(-1, 0, -1, reinforcedCasing, multiblock);
            this.addComponent(-1, 0, 1, reinforcedCasing, multiblock);
            this.addComponent(1, 0, -1, reinforcedCasing, multiblock);
            this.addComponent(1, 0, 1, reinforcedCasing, multiblock);
            this.addComponent(0, 1, 0, standardCasing, multiblock);
            this.addComponent(0, 1, 0, standardCasing, multiblock);
            this.addComponent(1, 1, 0, standardCasing, multiblock);
            this.addComponent(0, 1, 1, standardCasing, multiblock);
            this.addComponent(-1, 1, 0, standardCasing, multiblock);
            this.addComponent(0, 1, -1, standardCasing, multiblock);
            this.addComponent(-1, 1, -1, standardCasing, multiblock);
            this.addComponent(-1, 1, 1, standardCasing, multiblock);
            this.addComponent(1, 1, -1, standardCasing, multiblock);
            this.addComponent(1, 1, 1, standardCasing, multiblock);
            this.blockEntity.renderMultiblock = multiblock;
        } else {
            this.blockEntity.renderMultiblock = null;
        }

    }

    public void addComponent(int x, int y, int z, BlockState blockState, Multiblock multiblock) {
        multiblock.addComponent(new BlockPos(x - Direction.byId(this.blockEntity.getFacingInt()).getOffsetX() * 2, y, z - Direction.byId(this.blockEntity.getFacingInt()).getOffsetZ() * 2), blockState);
    }
}
