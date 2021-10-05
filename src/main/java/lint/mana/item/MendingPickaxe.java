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

import lint.mana.ManaStorage;
import lint.mana.ManaType;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.world.World;

/**
 * Pickaxe which automatically repairs damage every tick, using mana on the player
 */
public class MendingPickaxe extends PickaxeItem {

	public MendingPickaxe(ToolMaterial toolMaterial, int attackBonus, float speedModifier, Settings settings) {
		super(toolMaterial, attackBonus, speedModifier, settings);
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		if (stack.isDamaged() && entity instanceof PlayerEntity player) {
			ManaStorage storage = ManaStorage.of(player, ManaType.GENERIC);

			try (Transaction transaction = Transaction.openOuter()) {
				stack.setDamage((int) (stack.getDamage() - storage.extract(stack.getDamage(), transaction)));
				transaction.commit();
			}
		}
	}
}
