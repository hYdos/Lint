package me.hydos.lint.containers;

import me.hydos.lint.containers.util.LintInventory;
import me.hydos.lint.entities.liltaterbattery.LilTaterBattery;
import net.minecraft.container.Container;
import net.minecraft.container.ContainerType;
import net.minecraft.container.Slot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LilTaterInteractContainer extends Container {


    public final int taterId;

    public LilTaterInteractContainer(ContainerType<?> type, int syncId, int taterId, PlayerInventory playerInventory) {
        super(type, syncId);
        this.taterId = taterId;

        World world = playerInventory.player.world;

        LilTaterBattery tater = (LilTaterBattery) world.getEntityById(taterId);
        assert tater != null;
        LintInventory inv = tater.inventory;

        //Tater Armour slots
        this.addSlot(new Slot(inv, 0, 8, -12));
        this.addSlot(new Slot(inv, 1, 8, 6));
        this.addSlot(new Slot(inv, 2, 8, 24));
        this.addSlot(new Slot(inv, 3, 8, 42));

        //Tater hotbar slots
        for (int i = 0; i != 9; i++) {
            this.addSlot(new Slot(inv, i + 4, 8 + i * 18, 65));
        }

        //Player hotbar slots
        for (int i = 0; i != 9; i++) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 154));
        }


        int offset = 9;
        for (int j = 0; j != 3; j++) {
            for (int i = 0; i != 9; i++) {
                this.addSlot(new Slot(playerInventory, i + offset, 8 + i * 18, 96 + j * 18));
            }
            offset += 9;
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        return ItemStack.EMPTY;
    }
}
