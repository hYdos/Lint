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

package lint.mana.item;

import lint.mana.IOBounds;
import lint.mana.ManaStorage;
import lint.mana.ManaType;
import lint.mana.SimpleManaStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

/**
 * Simple portable crystal which can store up to 100000 generic mana, with input and output rates of 100 per operation
 */
public class PortableCrystal extends Item {

	private static final String STORED_MANA_NBT = "StoredMana";

	public PortableCrystal(Settings settings) {
		super(settings);
		registerMana();
	}

	protected void registerMana() {
		ManaStorage.ITEM.registerForItems((itemStack, context) -> {
			if (itemStack.getItem() instanceof PortableCrystal crystal && context == ManaType.GENERIC) {
				return new SimpleManaStorage(ManaType.GENERIC, new IOBounds(100, 100), 100000, crystal.getMana(itemStack)) {
					@Override
					protected void onFinalCommit() {
						crystal.setMana(itemStack, getAmount());
					}
				};
			}

			return null;
		}, this);
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		if (!world.isClient()) {
			// 0.1% chance of loosing a mana per tick
			if (world.random.nextInt(1000) == 0) {
				try (Transaction transaction = Transaction.openOuter()) {
					Objects.requireNonNull(ManaStorage.ITEM.find(stack, ManaType.GENERIC)).extract(1, transaction);
					transaction.commit();
				}
			}
		}
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(new TranslatableText(getTranslationKey() + ".status", getMana(stack)));
	}

	@Override
	public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
		stacks.add(new ItemStack(this));

		ItemStack filled = new ItemStack(this);
		setMana(filled, 100000);
		stacks.add(filled);
	}

	public long getMana(ItemStack itemStack) {
		if (itemStack.hasNbt()) {
			return itemStack.getNbt().getLong(STORED_MANA_NBT);
		} else {
			return 0;
		}
	}

	public void setMana(ItemStack itemStack, long amount) {
		if (amount == 0) {
			itemStack.setNbt(null);
		} else {
			itemStack.getOrCreateNbt().putLong(STORED_MANA_NBT, amount);
		}
	}
}
