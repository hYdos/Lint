package me.hydos.lint.containers;

import com.mojang.blaze3d.systems.RenderSystem;
import me.hydos.lint.entities.liltaterbattery.LilTaterBattery;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Quaternion;

public class LilTaterContainerScreen extends AbstractInventoryScreen<LilTaterInteractContainer> {
    public int mouseX, mouseY;

    public LilTaterInteractContainer container;

    public Identifier backgroundIdentifier = new Identifier("lint", "textures/gui/container/lil_tater_inventory.png");

    public LilTaterContainerScreen(LilTaterInteractContainer container, PlayerInventory playerInventory, Text name) {
        super(container, playerInventory, name);
        this.container = container;
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        super.render(mouseX, mouseY, delta);
        this.mouseX = mouseX;
        this.mouseY = mouseY;


    }

    public void renderBackground(){
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        assert this.minecraft != null;
        this.minecraft.getTextureManager().bindTexture(backgroundIdentifier);
        int i = this.x;
        int j = this.y;
        this.blit(i, j, 0, 0, this.containerWidth, this.containerHeight);
        assert this.minecraft.player != null;

        LilTaterBattery tater = (LilTaterBattery) this.minecraft.world.getEntityById(container.taterId);
        if(tater == null){
            onClose();
            return;
        }
        PlayerEntity plr = minecraft.player;
        drawTater(i + 51, j + 65, 60, (float)(i + 51) - this.mouseX, (float)(j + 75 - 50) - this.mouseY, tater);
    }

    @Override
    protected void drawBackground(float delta, int mouseX, int mouseY) {
        super.renderBackground();
        renderBackground();

    }

    public static void drawTater(int x, int y, int size, float mouseX, float mouseY, LilTaterBattery entity) {
        RenderSystem.pushMatrix();
        RenderSystem.translatef((float)x, (float)y, 1050.0F);
        RenderSystem.scalef(1.0F, 1.0F, -1.0F);
        MatrixStack matrixStack = new MatrixStack();
        matrixStack.translate(0.0D, 0.0D, 1000.0D);
        matrixStack.scale((float)size, (float)size, (float)size);
        Quaternion quaternion = Vector3f.POSITIVE_Z.getDegreesQuaternion(180.0F);
        matrixStack.multiply(quaternion);
        EntityRenderDispatcher entityRenderDispatcher = MinecraftClient.getInstance().getEntityRenderManager();
        entityRenderDispatcher.setRenderShadows(false);
        VertexConsumerProvider.Immediate immediate = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
        entityRenderDispatcher.render(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, matrixStack, immediate, 15728880);
        immediate.draw();
        entityRenderDispatcher.setRenderShadows(true);
        RenderSystem.popMatrix();
    }
}
