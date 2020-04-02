package me.hydos.lint.containers;

import net.minecraft.container.Container;
import net.minecraft.container.ContainerType;
import net.minecraft.entity.player.PlayerEntity;

import java.util.UUID;

public class LilTaterInteractContainer extends Container {

    public int taterId;

    public LilTaterInteractContainer(ContainerType<?> type, int syncId, int taterId) {
        super(type, syncId);
        this.taterId = taterId;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}
