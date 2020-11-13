package io.github.hydos.lint.spirit;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SpiritsComponent implements AutoSyncedComponent {
    private final PlayerEntity player;
    private final Set<Spirit> spirits = new HashSet<>();

    public SpiritsComponent(PlayerEntity player) {
        this.player = player;
    }

    public Set<Spirit> getSpirits() {
        return this.spirits;
    }

    public boolean hasSpirit(Spirit spirit) {
        return this.spirits.contains(spirit);
    }

    public boolean addSpirit(Spirit spirit) {
        if (this.spirits.add(spirit)) {
            SpiritComponentInitializer.SPIRITS.sync(this.player);
            return true;
        }
        return false;
    }

    @Override
    public boolean shouldSyncWith(ServerPlayerEntity player) {
       return player.equals(this.player);
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        Registry<Spirit> registry = this.player.getEntityWorld().getRegistryManager().get(SpiritRegistry.SPIRIT_KEY);

        ListTag spiritsTag = new ListTag();
        for (Spirit spirit : this.spirits) {
            spiritsTag.add(StringTag.of(registry.getId(spirit).toString()));
        }

        tag.put("Spirits", spiritsTag);
    }

    @Override
    public void readFromNbt(CompoundTag tag) {
        Registry<Spirit> registry = this.player.getEntityWorld().getRegistryManager().get(SpiritRegistry.SPIRIT_KEY);
        this.spirits.clear();

        ListTag spiritsTag = tag.getList("Spirits", NbtType.STRING);
        for (int index = 0; index < spiritsTag.size(); index++) {
            Identifier id = Identifier.tryParse(spiritsTag.getString(index));
            if (id != null) {
                Spirit spirit = registry.get(id);
                if (spirit != null) {
                    this.spirits.add(spirit);
                }
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof SpiritsComponent)) return false;
        SpiritsComponent otherComponent = (SpiritsComponent) other;

        return this.player.equals(otherComponent.player) && this.spirits.equals(otherComponent.spirits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.player, this.spirits);
    }

    @Override
    public String toString() {
        return "SpiritsComponent{player=" + this.player + ", spirits=" + this.spirits + "}";
    }
}
