package me.hydos.lint.containers;

import me.hydos.lint.entities.liltaterbattery.LilTaterBattery;
import net.minecraft.client.MinecraftClient;
import net.minecraft.container.Container;
import net.minecraft.container.ContainerType;
import net.minecraft.container.Slot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.BasicInventory;
import net.minecraft.inventory.Inventory;

public class LilTaterInteractContainer extends Container {


    public int taterId;

    public LilTaterInteractContainer(ContainerType<?> type, int syncId, int taterId, PlayerInventory playerInventory) {
        super(type, syncId);
        this.taterId = taterId;
        LilTaterBattery tater = (LilTaterBattery) MinecraftClient.getInstance().world.getEntityById(taterId);
        Inventory inv = tater.inventory;
        //Armour slots
        this.addSlot(new Slot(inv, 0, 8, 8));
        this.addSlot(new Slot(inv, 2, 8, 26));
        this.addSlot(new Slot(inv, 3, 8, 44));
        this.addSlot(new Slot(inv, 4, 8, 62));
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}
