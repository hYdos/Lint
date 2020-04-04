package me.hydos.lint.blocks;

import io.netty.buffer.Unpooled;
import me.hydos.lint.core.Packets;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.PacketByteBuf;
import reborncore.client.containerBuilder.builder.BuiltContainer;
import reborncore.client.gui.builder.GuiBase;

public class AdventureTransformerGui extends GuiBase<BuiltContainer> {

    final AdventureTransformerBlockEntity blockEntity;

    public AdventureTransformerGui(int syncID, PlayerEntity player, AdventureTransformerBlockEntity blockEntity) {
        super(player, blockEntity, blockEntity.createContainer(syncID, player));
        this.blockEntity = blockEntity;
    }

    @Override
    public void init() {
        super.init();
        addButton(new ButtonWidget(x + 60, y + 40, 40, 20, "Enter", (widget) -> {
            PacketByteBuf data = new PacketByteBuf(Unpooled.buffer());
            data.writeString("HAYKAM");
            ClientSidePacketRegistry.INSTANCE.sendToServer(Packets.PLAY_DIMENSION_CHANGE_PACKET_ID, data);
        }));
    }

    protected void drawBackground(float f, int mouseX, int mouseY) {
        super.drawBackground(f, mouseX, mouseY);
        Layer layer = Layer.BACKGROUND;
        this.drawSlot(39, 72, layer);
        buttons.get(0).active = !(blockEntity.getEnergy() < 10);
    }

    protected void drawForeground(int mouseX, int mouseY) {
        super.drawForeground(mouseX, mouseY);
        Layer layer = Layer.FOREGROUND;
        this.builder.drawMultiEnergyBar(this, 40, 19, (int) this.blockEntity.getEnergy(), (int) this.blockEntity.getMaxPower(), mouseX, mouseY, 0, layer);
    }
}
