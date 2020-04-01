package me.hydos.lint.blocks;

import me.hydos.lint.registers.DimensionRegister;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import reborncore.client.containerBuilder.builder.BuiltContainer;
import reborncore.client.gui.builder.GuiBase;
import reborncore.client.gui.builder.widget.GuiButtonExtended;
import reborncore.client.multiblock.Multiblock;

public class AdventureTransformerGui extends GuiBase<BuiltContainer> {
    AdventureTransformerBlockEntity blockEntity;

    public ButtonWidget dimensionButton;
    public AdventureTransformerGui(int syncID, PlayerEntity player, AdventureTransformerBlockEntity blockEntity) {
        super(player, blockEntity, blockEntity.createContainer(syncID, player));
        this.blockEntity = blockEntity;
        dimensionButton = new ButtonWidget(0,0,100,10,"Enter tater land", (widget) ->{
            System.out.println("This was run");
            MinecraftClient.getInstance().player.changeDimension(DimensionRegister.HAYKAM);
        });
        addButton(dimensionButton);

    }

    protected void drawBackground(float f, int mouseX, int mouseY) {
        super.drawBackground(f, mouseX, mouseY);
        Layer layer = Layer.BACKGROUND;
        this.drawSlot(8, 72, layer);
        dimensionButton.render(mouseX, mouseY, f);


    }

    protected void drawForeground(int mouseX, int mouseY) {
        super.drawForeground(mouseX, mouseY);
        Layer layer = Layer.FOREGROUND;
        this.addHologramButton(6, 4, 212, layer).clickHandler(this::onClick);

        this.builder.drawMultiEnergyBar(this, 9, 19, (int) this.blockEntity.getEnergy(), (int) this.blockEntity.getMaxPower(), mouseX, mouseY, 0, layer);
    }

    public void onClick(GuiButtonExtended button, double mouseX, double mouseY) {
        if (this.blockEntity.renderMultiblock == null) {
            Multiblock multiblock = new Multiblock();
            this.blockEntity.renderMultiblock = multiblock;
        } else {
            this.blockEntity.renderMultiblock = null;
        }

    }

    public void addComponent(int x, int y, int z, BlockState blockState, Multiblock multiblock) {
        multiblock.addComponent(new BlockPos(x - Direction.byId(this.blockEntity.getFacingInt()).getOffsetX() * 2, y, z - Direction.byId(this.blockEntity.getFacingInt()).getOffsetZ() * 2), blockState);
    }
}
