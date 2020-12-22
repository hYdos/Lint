package io.github.hydos.lint.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import io.github.hydos.lint.client.screen.TaterDownloadingTerrainScreen;
import io.github.hydos.lint.world.dimension.Dimensions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DownloadingTerrainScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
	@Redirect(method = "onPlayerRespawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;openScreen(Lnet/minecraft/client/gui/screen/Screen;)V"))
	private void openDownloadingTerrainScreen(MinecraftClient client, Screen screen, PlayerRespawnS2CPacket packet) {
		if (screen instanceof DownloadingTerrainScreen && packet.getDimension().equals(Dimensions.HAYKAM_WORLD)) {
			client.openScreen(new TaterDownloadingTerrainScreen());
		} else {
			client.openScreen(new DownloadingTerrainScreen());
		}
	}
}
