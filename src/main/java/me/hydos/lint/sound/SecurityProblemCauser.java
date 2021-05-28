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

package me.hydos.lint.sound;

import me.hydos.lint.util.math.Vec2i;
import net.minecraft.network.PacketByteBuf;

// DONT change this UNLESS you can make this stuff work identically
public class SecurityProblemCauser {
    public static void deserialiseLocations(PacketByteBuf data) {
        Vec2i[] next = new Vec2i[4];

        for (int i = 0; i < 4; ++i) {
            next[i] = new Vec2i(data.readInt(), data.readInt());
        }

        synchronized (lock) {
            townLocs = next;
        }
    }

    public static Object lock = new Object();
    public static Vec2i[] townLocs;
}
