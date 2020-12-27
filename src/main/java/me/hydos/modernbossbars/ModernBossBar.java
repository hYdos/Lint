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

package me.hydos.modernbossbars;

import io.netty.buffer.Unpooled;
import me.hydos.lint.network.Networking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ModernBossBar {
    private static ModernBossBar instance;

    private final List<ServerPlayerEntity> players;
    private Text title;
    private final int colour;
    private int endX;

    public ModernBossBar(Text title, int colour, int endX) {
        this.players = new ArrayList<>();
        this.title = title;
        this.colour = colour;
        this.endX = endX;
        instance = this;
    }

    public static int calculateEndX(float percent) {
        return  (int) (280 * percent) - 140;
    }

    public void clear() {
        setEndX(0);
    }

    public void addPlayer(ServerPlayerEntity player) {
        players.add(player);
        PacketByteBuf data = new PacketByteBuf(Unpooled.buffer());
        data.writeEnumConstant(PacketType.NEW);
        data.writeText(title);
        data.writeInt(colour);
        data.writeInt(endX);
        ServerPlayNetworking.send(player, Networking.SEND_BOSSBAR_INFO, data);
    }

    public void removePlayer(ServerPlayerEntity player){
        players.remove(player);
        PacketByteBuf data = new PacketByteBuf(Unpooled.buffer());
        data.writeEnumConstant(PacketType.SET_VALUE);
        data.writeInt(0);
        ServerPlayNetworking.send(player, Networking.SEND_BOSSBAR_INFO, data);
    }

    public void setEndX(int endX) {
        this.endX = endX;
        PacketByteBuf data = new PacketByteBuf(Unpooled.buffer());
        data.writeEnumConstant(PacketType.SET_VALUE);
        data.writeInt(endX);
        for (ServerPlayerEntity player : players) {
            ServerPlayNetworking.send(player, Networking.SEND_BOSSBAR_INFO, data);
        }
    }

    public static ModernBossBar getInstance() {
        if (instance == null)
            new ModernBossBar(new LiteralText("UNINITIALIZED"), 0xFF696969, 10);
        return instance;
    }

    public void setTitle(Text title) {
        this.title = title;
        PacketByteBuf data = new PacketByteBuf(Unpooled.buffer());
        data.writeEnumConstant(PacketType.SET_TITLE);
        data.writeText(title);
        for (ServerPlayerEntity player : players) {
            ServerPlayNetworking.send(player, Networking.SEND_BOSSBAR_INFO, data);
        }
    }

    public Text getTitle() {
        return title;
    }

    public int getColour() {
        return colour;
    }

    public int getEndX() {
        return endX;
    }

    public enum PacketType {
        NEW,
        SET_VALUE,
        SET_TITLE
    }
}
