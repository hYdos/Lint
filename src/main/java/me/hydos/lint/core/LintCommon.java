package me.hydos.lint.core;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import me.hydos.lint.util.TeleportUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.command.arguments.DimensionArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

public class LintCommon implements ModInitializer {
	@Override
	public void onInitialize() {
		CommandRegistry.INSTANCE.register(false, dispatcher -> {
			LiteralArgumentBuilder<ServerCommandSource> lab = CommandManager.literal("tpdim")
					.requires(src -> src.getEntity() instanceof PlayerEntity)
					.then(CommandManager.argument(
							"dimension_id",
							DimensionArgumentType.dimension())
							.executes(cmd -> {
								ServerPlayerEntity player = cmd.getSource().getPlayer();

								if (player != null) {
									TeleportUtils.teleport(player, DimensionArgumentType.getDimensionArgument(cmd, "dimension_id"), player.getBlockPos());
									return 1;
								}
								return 0;
							})
							);
			dispatcher.register(lab);
		});
	}
}
