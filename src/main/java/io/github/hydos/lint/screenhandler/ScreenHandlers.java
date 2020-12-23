package io.github.hydos.lint.screenhandler;

import io.github.hydos.lint.Lint;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;

public class ScreenHandlers implements ModInitializer {

	public static final ScreenHandlerType<SmelteryScreenHandler> SMELTERY = ScreenHandlerRegistry.registerSimple(Lint.id("smeltery"), SmelteryScreenHandler::new);

	@Override
	public void onInitialize() {
	}
}
