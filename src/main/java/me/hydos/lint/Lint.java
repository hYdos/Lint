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

package me.hydos.lint;

import me.hydos.lint.block.LintBlocks;
import me.hydos.lint.entity.Entities;
import me.hydos.lint.fluid.LintFluids;
import me.hydos.lint.item.LintItems;
import me.hydos.lint.network.Networking;
import me.hydos.lint.recipe.Recipes;
import me.hydos.lint.screenhandler.ScreenHandlers;
import me.hydos.lint.sound.Sounds;
import me.hydos.lint.tag.LintBlockTags;
import me.hydos.lint.world.biome.Biomes;
import me.hydos.lint.world.dimension.Dimensions;
import me.hydos.lint.world.feature.Features;
import me.hydos.lint.world.structure.Structures;
import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

public class Lint implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("Lint");

	public static final RuntimeResourcePack RESOURCE_PACK = RuntimeResourcePack.create("lint");

	public static Identifier id(String path) {
		return new Identifier("lint", path);
	}

	@Override
	public void onInitialize() {
		LOGGER.info("Lint is initializing");
		Recipes.register();
		GeckoLib.initialize();
		Sounds.register();
		LintBlockTags.register();
		ScreenHandlers.register();
		Networking.register();
		registerLintContent();
		registerLintWorld();
		RRPCallback.EVENT.register(resources -> resources.add(RESOURCE_PACK));
		LOGGER.info("Lint initialization successful!");

		// Datafixer nonsense
		// Someone help pls
//		DataFixerBuilder builder = new DataFixerBuilder(1);
//		builder.addSchema(Schemas.LINT_V0);
//		Schema schema1 = builder.addSchema(1, Lintv1::new);
//		builder.addFixer(DimensionNameFix.create(schema1, "Rename Lint Dimension", (string) -> Objects.equals(IdentifierNormalizingSchema.normalize(string), "lint:haykam") ? "lint:fraiya" : string));
//		builder.build(Util.getMainWorkerExecutor());

	}

	private void registerLintWorld() {
		Structures.register();
		Features.register();
		Biomes.register();
		Dimensions.register();
	}

	private void registerLintContent() {
		LintFluids.register();
		LintBlocks.register();
		LintItems.register();
		Entities.register();
	}
}
