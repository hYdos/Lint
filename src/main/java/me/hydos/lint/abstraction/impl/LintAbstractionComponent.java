package me.hydos.lint.abstraction.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.jetbrains.annotations.Nullable;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import me.hydos.lint.abstraction.ItemData;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class LintAbstractionComponent implements AutoSyncedComponent {
	public LintAbstractionComponent(ItemStack stack) {
		this.stack = stack;
		CONSTRUCTORS.forEach((id, constructor) -> this.stored.put(id, constructor.apply(stack)));
	}

	private final ItemStack stack;
	private final Map<Identifier, ItemData> stored = new HashMap<>();
	private static final Object MAGIC = new Object();
	@Nullable
	private Identifier syncing;

	@Override
	public void readFromNbt(CompoundTag tag) {
		this.stored.forEach((id, data) -> data.read(tag.getCompound(id.toString()))); 
	}

	@Override
	public void writeToNbt(CompoundTag tag) {
		this.stored.forEach((id, data) -> {
			CompoundTag sdata = new CompoundTag();
			data.write(sdata);
			tag.put(id.toString(), sdata);
		});
	}

	@Override
	public void writeSyncPacket(PacketByteBuf buf, ServerPlayerEntity recipient) {
		String type;
		ItemData data;

		synchronized (MAGIC) {
			type = this.syncing == null ? "ALL" : this.syncing.toString();
			data = this.syncing == null ? null : this.stored.get(this.syncing);
			this.syncing = null;
		}

		buf.writeString(type);

		CompoundTag tag = new CompoundTag();

		if (data == null) {
			this.writeToNbt(tag);
		} else {
			data.write(tag);
		}

		buf.writeCompoundTag(tag);
	}

	@Override
	public void applySyncPacket(PacketByteBuf buf) {
		String identifier = buf.readString();

		CompoundTag tag = buf.readCompoundTag();
		if (tag != null) {
			this.readFromNbt(tag);
		}
	}

	@Override
	public boolean shouldSyncWith(ServerPlayerEntity player) {
		return player.inventory.contains(this.stack);
	}

	public static <T extends ItemData> void register(Identifier id, Function<ItemStack, T> data) {
		CONSTRUCTORS.put(id, data);
	}

	private static final Map<Identifier, Function<ItemStack, ? extends ItemData>> CONSTRUCTORS = new HashMap<>();
}
