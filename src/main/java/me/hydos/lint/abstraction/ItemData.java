package me.hydos.lint.abstraction;

import java.util.function.Function;

import me.hydos.lint.abstraction.impl.LintAbstractionComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;

public interface ItemData {
	void write(CompoundTag tag);
	void read(CompoundTag tag);

	public static <T extends ItemData> Key<T> register(Identifier id, Function<ItemStack, T> data) {
		LintAbstractionComponent.register(id, data);
		return new Key<>(id);
	}

	public static class Key<T extends ItemData> {
		public Key(Identifier id) {
			this.id = id;
		}
		
		private final Identifier id;

		public T get(ItemStack stack) {
			
		}
	}
}

