package me.hydos.lint.discord;

import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;

public class DiscordRpc {

	public static void register() {
		DiscordRPC.discordRunCallbacks();
		DiscordRPC.discordInitialize("793606312830959636", null, false);
		DiscordRPC.discordRegister("793606312830959636", "");
		DiscordRichPresence presence = new DiscordRichPresence.Builder("Lint").setDetails("Patting Taters").build();
		DiscordRPC.discordUpdatePresence(presence);
	}
}
