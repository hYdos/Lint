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

package me.hydos.lint.mixin;

import me.hydos.lint.item.LintItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.server.AbstractTagProvider;
import net.minecraft.data.server.ItemTagsProvider;
import net.minecraft.item.Item;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemTagsProvider.class)
public abstract class ItemTagsProviderMixin extends AbstractTagProvider<Item> {

	protected ItemTagsProviderMixin(DataGenerator root, Registry<Item> registry) {
		super(root, registry);
	}

	@Inject(method = "configure", at = @At("TAIL"))
	private void registerCustomMusicDiscs(CallbackInfo ci) {
		this.getOrCreateTagBuilder(ItemTags.MUSIC_DISCS).add(LintItems.GRIMACE_OBOE_DISC, LintItems.OCEAN_DISC, LintItems.CORRUPT_FOREST_DISC, LintItems.MYSTICAL_FOREST_DISC);
	}

}
