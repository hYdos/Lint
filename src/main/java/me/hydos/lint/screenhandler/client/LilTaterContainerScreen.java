/*
 * Lint
 * Copyright (C) 2020 hYdos, Valoeghese, ramidzkh
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package me.hydos.lint.screenhandler.client;

import me.hydos.lint.entity.passive.TinyPotatoEntity;
import me.hydos.lint.screenhandler.LilTaterInteractScreenHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Quaternion;

import java.util.Objects;

public class LilTaterContainerScreen extends AbstractInventoryScreen<LilTaterInteractScreenHandler> {

    public final LilTaterInteractScreenHandler container;
    public final Identifier backgroundIdentifier = new Identifier("lint", "textures/gui/container/lil_tater_inventory.png");
    public int mouseX;
    public int mouseY;

    public LilTaterContainerScreen(LilTaterInteractScreenHandler container, PlayerInventory playerInventory, Text name) {
        super(container, playerInventory, name);
        this.container = container;
    }

    public static void drawTater(MatrixStack matrices, int x, int y, int size, TinyPotatoEntity entity) {
        matrices.push();
        matrices.translate((float) x, (float) y, 1050.0F);
        matrices.scale(1.0F, 1.0F, -1.0F);
        MatrixStack matrixStack = new MatrixStack();
        matrixStack.translate(0.0D, 0.0D, 1000.0D);
        matrixStack.scale((float) size, (float) size, (float) size);
        Quaternion quaternion = Vector3f.POSITIVE_Z.getDegreesQuaternion(180.0F);
        matrixStack.multiply(quaternion);
        EntityRenderDispatcher entityRenderDispatcher = MinecraftClient.getInstance().getEntityRenderDispatcher();
        entityRenderDispatcher.setRenderShadows(false);
        VertexConsumerProvider.Immediate immediate = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
        entityRenderDispatcher.render(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, matrixStack, immediate, 15728880);
        immediate.draw();
        entityRenderDispatcher.setRenderShadows(true);
        matrices.pop();
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        renderBackground(matrices);
    }

    @Override
    public void render(MatrixStack stack, int mouseX, int mouseY, float delta) {
        if (container != null) {
            super.render(stack, mouseX, mouseY, delta);
            this.mouseX = mouseX;
            this.mouseY = mouseY;
        } else {
            this.onClose();
            Objects.requireNonNull(client).openScreen(null);
        }
    }

    public void renderBackground(MatrixStack matrix) {
        matrix.push();
        this.client.getTextureManager().bindTexture(backgroundIdentifier);
        drawTexture(matrix, x, y - 32, 0, 0, this.backgroundWidth, this.backgroundHeight + 65);

        TinyPotatoEntity tater = (TinyPotatoEntity) Objects.requireNonNull(this.client.world).getEntityById(container.taterId);
        if (tater == null) {
            onClose();
            return;
        }
        drawTater(matrix, x + 51, y + 20, 60, tater);
        matrix.pop();
    }
}
