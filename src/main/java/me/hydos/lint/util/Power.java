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

package me.hydos.lint.util;

/**
 * Enum for the god-powers of this world.
 */
public enum Power {
	NONE,
	ALLOS, // light, creation, production, preservation
	MANOS; // darkness, destruction, consumption, corruption

	public boolean conflicts(Power other) {
		if (this == ALLOS) {
			return other == MANOS;
		} else if (this == MANOS) {
			return other == ALLOS;
		}

		return false;
	}
	
	public enum Broad {
		// The two great powers, as described above. They do not associate with any one gender, as they are inherently beyond such concepts, and can appear as any gender when in the world.
		ALLOS,
		MANOS,
		// The powers of the Caria, immortal beings created by Allos using both the powers of Allos and Manos - closer to people than gods - blessed with power.
		// There are two male Cariar and two female Carien.
		// Each Caria is associated with a town.
		THERIA, // Cariar of the mind. Admires wit, courage, strategy, cunning, and generally messing with people's heads in mind games
		AURIA, // Carien of War. Admires physical strength and combat.
		PAWERIA, // Cariar of order. Likes strict hierarchies and social structures, and those who follow such laws of their land. Although not fine with theft, will not disdain higher-ups if they take from lower-downs, if it follows the order of society.
		HERIA // Carien of emotions. Admires love, passion, fury, hate, and acting on one's emotions.
	}
}
