package me.hydos.lint.screenhandler;

import me.hydos.lint.util.LintInventory;
import me.hydos.lint.entity.passive.TinyPotatoEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;

public class LilTaterInteractScreenHandler extends ScreenHandler {

    public final int taterId;

    public LilTaterInteractScreenHandler(ScreenHandlerType<?> type, int syncId, int taterId, PlayerInventory playerInventory) {
        super(type, syncId);
        this.taterId = taterId;

        World world = playerInventory.player.world;

        TinyPotatoEntity tater = (TinyPotatoEntity) world.getEntityById(taterId);
        LintInventory inv = tater.inventory;

        //Tater Armour slots
        this.addSlot(new Slot(inv, 0, 8, 10));
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

        int padding = 9;
        for (int j = 0; j != 3; j++) {
            for (int i = 0; i != 9; i++) {
                this.addSlot(new Slot(playerInventory, i + padding, 8 + i * 18, 96 + j * 18));
            }
            padding += 9;
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
