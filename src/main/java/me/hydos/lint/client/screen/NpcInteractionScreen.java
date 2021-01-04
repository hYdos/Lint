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

package me.hydos.lint.client.screen;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class NpcInteractionScreen extends Screen {

	public Text text;
	public int entityId;

	public NpcInteractionScreen(PacketByteBuf byteBuf) {
		super(new LiteralText(byteBuf.readString()));
		text = byteBuf.readText();
		entityId = byteBuf.readInt();
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		super.render(matrices, mouseX, mouseY, delta);
		if(client != null) { // im not sure how or why, but this happened to me
			fill(matrices, 0, (int) (height / 1.5f), width, height, 0x99111111);
			client.textRenderer.draw(matrices, text, 10, height / 1.4f, 0xFFFFFFFF);
			LivingEntity entity = (LivingEntity) client.player.world.getEntityById(entityId);
			if (entity != null) {
				InventoryScreen.drawEntity(40, (int) (height / 1.5f), 80, -346, -8, entity);
			}
		}
	}
}
