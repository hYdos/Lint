package me.hydos.lint.blocks;

import io.netty.buffer.Unpooled;
import me.hydos.lint.Lint;
import me.hydos.lint.registers.DimensionRegister;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.PacketByteBuf;
import reborncore.client.containerBuilder.builder.BuiltContainer;
import reborncore.client.gui.builder.GuiBase;

public class AdventureTransformerGui extends GuiBase<BuiltContainer> {
    AdventureTransformerBlockEntity blockEntity;

    public ButtonWidget dimensionButton;
    public AdventureTransformerGui(int syncID, PlayerEntity player, AdventureTransformerBlockEntity blockEntity) {
        super(player, blockEntity, blockEntity.createContainer(syncID, player));
        this.blockEntity = blockEntity;

    }

    @Override
    public void init() {
        super.init();
        dimensionButton = new ButtonWidget(0,0,100,10,"Enter tater land", (widget) ->{
            System.out.println("This was run");

            PacketByteBuf data = new PacketByteBuf(Unpooled.buffer());
            data.writeString("HAYKAM");

            ClientSidePacketRegistry.INSTANCE.sendToServer(Lint.PLAY_DIMENSION_CHANGE_PACKET_ID, data);
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
        this.builder.drawMultiEnergyBar(this, 9, 19, (int) this.blockEntity.getEnergy(), (int) this.blockEntity.getMaxPower(), mouseX, mouseY, 0, layer);
    }
}
