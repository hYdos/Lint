package me.hydos.lint.screenhandler;

import me.hydos.lint.Lint;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;

public class ScreenHandlers {

	public static final ScreenHandlerType<SmelteryScreenHandler> SMELTERY = ScreenHandlerRegistry.registerSimple(Lint.id("smeltery"), SmelteryScreenHandler::new);
	//	public static final ScreenHandlerType<SmelteryScreenHandler> TATER_INVENTORY = ScreenHandlerRegistry.registerSimple(Lint.id("tater_inv"), TaterScreenHandler::new);

	public static void register() {
	}
}
