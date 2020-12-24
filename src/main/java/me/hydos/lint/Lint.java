package me.hydos.lint;

import me.hydos.lint.entity.Entities;
import me.hydos.lint.fluid.Fluids;
import me.hydos.lint.item.Items;
import me.hydos.lint.network.Networking;
import me.hydos.lint.screenhandler.ScreenHandlers;
import me.hydos.lint.sound.Sounds;
import me.hydos.lint.block.Blocks;
import me.hydos.lint.world.biome.Biomes;
import me.hydos.lint.world.dimension.Dimensions;
import me.hydos.lint.world.feature.Features;
import me.hydos.lint.world.structure.Structures;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Lint implements ModInitializer {

	public static final Logger LOGGER = LogManager.getLogger("Lint");

	@Override
	public void onInitialize() {
		LOGGER.info("Lint is initializing");
		Sounds.register();
		Blocks.register();
		Items.register();
		Entities.register();
		Fluids.register();
		ScreenHandlers.register();
		Networking.register();
		registerDimensions();
		LOGGER.info("Lint initialization successful!");
	}

	private void registerDimensions() {
		Structures.register();
		Features.register();
		Biomes.register();
		Dimensions.register();
	}

	public static Identifier id(String path) {
		return new Identifier("lint", path);
	}
}
