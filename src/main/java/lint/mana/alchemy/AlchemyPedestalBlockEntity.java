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

package lint.mana.alchemy;

import lint.mana.*;
import me.hydos.lint.recipe.Recipes;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;

public class AlchemyPedestalBlockEntity extends BlockEntity implements BlockEntityClientSerializable {

	private static final String STORED_MANA_NBT = "StoredMana";

	private final SimpleManaStorage storage = new SimpleManaStorage(ManaType.GENERIC, new IOBounds(Long.MAX_VALUE, Long.MAX_VALUE), 0, 0) {
		@Override
		public long getCapacity() {
			return recipe == null ? 0 : recipe.mana().amount();
		}

		@Override
		protected void onFinalCommit() {
			sync();
		}
	};
	private final ManaStorage exposedStorage = DelegatingManaStorage.noExtraction(storage);
	private LinkedList<ItemStack> inventory = new LinkedList<>();
	private @Nullable AlchemyRecipe recipe;

	public AlchemyPedestalBlockEntity(BlockEntityType<? extends AlchemyPedestalBlockEntity> type, BlockPos blockPos, BlockState blockState) {
		super(type, blockPos, blockState);
	}

	public ManaStorage getStorage() {
		return exposedStorage;
	}

	public void tick(World world) {
		if (world.isClient) {
			return;
		}

		updateRecipe(world);

		if (recipe != null) {
			long required = recipe.mana().amount();

			try (Transaction transaction = Transaction.openOuter()) {
				if (storage.extract(required, transaction) == required) {
					transaction.commit();

					inventory.clear();
					inventory.add(recipe.getOutput());
					recipe = null;
					sync();
				}
			}
		}
	}

	public ActionResult onUse(PlayerEntity player, Hand hand) {
		ItemStack stack = player.getStackInHand(hand);

		if (stack.isEmpty()) {
			if (!inventory.isEmpty()) {
				player.setStackInHand(hand, inventory.remove());
				return ActionResult.SUCCESS;
			}
		} else {
			inventory.add(stack.split(1));
			return ActionResult.SUCCESS;
		}

		return ActionResult.PASS;
	}

	private void updateRecipe(World world) {
		for (AlchemyRecipe recipe : world.getRecipeManager().listAllOfType(Recipes.ALCHEMY)) {
			RecipeMatcher recipeMatcher = new RecipeMatcher();
			int count = 0;

			for (ItemStack itemStack : inventory) {
				if (!itemStack.isEmpty()) {
					count++;
					recipeMatcher.addInput(itemStack, 1);
				}
			}

			if (count == recipe.ingredients().size() && recipeMatcher.match(recipe, null)) {
				this.recipe = recipe;
				return;
			}
		}
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		Inventories.readNbt(nbt, new DefaultedList<>(inventory = new LinkedList<>(), ItemStack.EMPTY) {
		});
		storage.setStored(nbt.getLong(STORED_MANA_NBT));
	}

	@Override
	public NbtCompound writeNbt(NbtCompound nbt) {
		super.writeNbt(nbt);
		Inventories.writeNbt(nbt, new DefaultedList<>(inventory, ItemStack.EMPTY) {
		});
		nbt.putLong(STORED_MANA_NBT, storage.getAmount());
		return nbt;
	}

	@Override
	public void fromClientTag(NbtCompound tag) {
		readNbt(tag);
	}

	@Override
	public NbtCompound toClientTag(NbtCompound tag) {
		return writeNbt(tag);
	}
}
