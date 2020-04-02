package me.hydos.lint.containers;

import net.minecraft.container.Container;
import net.minecraft.container.ContainerType;
import net.minecraft.entity.player.PlayerEntity;

public class LilTaterInteractContainer extends Container {

    public LilTaterInteractContainer(ContainerType<?> type, int syncId) {
        super(type, syncId);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}
