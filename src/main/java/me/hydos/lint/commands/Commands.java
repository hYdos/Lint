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

package me.hydos.lint.commands;

import me.hydos.lint.entity.passive.human.NPCHumanEntity;
import me.hydos.lint.util.math.Vec2i;
import me.hydos.lint.world.dimension.Dimensions;
import me.hydos.lint.world.gen.terrain.TerrainChunkGenerator;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class Commands {
	public static void initialize() {
		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> dispatcher.register(
				CommandManager.literal("lint")
						.then(CommandManager.literal("debug")
								.requires(sc -> sc.hasPermissionLevel(2))
								.then(CommandManager.literal("towns")
										.executes(src -> {
											ServerWorld world = src.getSource().getMinecraftServer().getWorld(Dimensions.FRAIYA_WORLD);
											ChunkGenerator generator = world.getChunkManager().getChunkGenerator();

											if (generator instanceof TerrainChunkGenerator) {
												Vec2i[] towns = ((TerrainChunkGenerator) generator).getTownCentres();

												StringBuilder result = new StringBuilder();

												for (int i = 0; i < 4; ++i) {
													result.append(towns[i]);

													if (i < 3) {
														result.append("\n");
													}
												}

												src.getSource().sendFeedback(new LiteralText(result.toString()), false);
												return 1;
											}

											return 0;
										}))
								.then(CommandManager.literal("npc")
										.requires(src -> src.getEntity() != null && src.getEntity() instanceof PlayerEntity)
										.then(CommandManager.argument("id", IdentifierArgumentType.identifier())
												.executes(src -> {
													Identifier id = IdentifierArgumentType.getIdentifier(src, "id");
													ServerWorld world = src.getSource().getWorld();
													NPCHumanEntity npc = NPCHumanEntity.create(world, id);
													npc.refreshPositionAfterTeleport(src.getSource().getPlayer().getPos());
													world.spawnEntity(npc);
													return 1;
												}))))
		));
	}
}
