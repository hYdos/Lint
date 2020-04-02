package me.hydos.lint.containers;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

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
        InventoryScreen.drawEntity(i + 51, j + 75, 30, (float)(i + 51) - this.mouseX, (float)(j + 75 - 50) - this.mouseY, (LivingEntity) this.minecraft.world.getEntityById(container.taterId));
    }

    @Override
    protected void drawBackground(float delta, int mouseX, int mouseY) {
        super.renderBackground();
        renderBackground();

    }
}
