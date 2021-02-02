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

package me.hydos.lint.npc;

import net.minecraft.util.Identifier;

/**
 * All the data neccesary for NPCs is specified here.
 */
public class NPC {
	public NPC(Settings settings) {
		this.name = settings.name;
		this.texture = new Identifier(settings.texture.getNamespace(), "textures/npc/" + settings.texture.getPath() + ".png");
	}

	private final String name;
	private final Identifier texture;

	public String getName() {
		return this.name;
	}

	public Identifier getTextureLocation() {
		return this.texture;
	}

	public static class Settings {
		private String name = "Missingno";
		private Identifier texture = TEXTURE_MISSINGNO;

		public Settings name(String name) {
			this.name = name;
			return this;
		}
		
		public Settings texture(Identifier texture) {
			this.texture = texture;
			return this;
		}

		private static final Identifier TEXTURE_MISSINGNO = new Identifier("missingno");
	}

	private static final NPC MISSINGNO = new NPC(new NPC.Settings().name("missingno").texture(new Identifier("missing_texture")));
}
