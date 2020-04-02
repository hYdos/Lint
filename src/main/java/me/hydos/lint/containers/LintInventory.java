package me.hydos.lint.containers;

import com.google.common.collect.Lists;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryListener;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeFinder;
import net.minecraft.util.DefaultedList;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class LintInventory implements Inventory {
    private final int size;
    private final DefaultedList<ItemStack> stackList;
    private List<InventoryListener> listeners;

    public LintInventory(int size) {
        this.size = size;
        this.stackList = DefaultedList.ofSize(size, ItemStack.EMPTY);
    }

    public LintInventory(ItemStack... items) {
        this.size = items.length;
        this.stackList = DefaultedList.copyOf(ItemStack.EMPTY, items);
    }

    public DefaultedList<ItemStack> getRawList(){
        return stackList;
    }

    public void addListener(InventoryListener inventoryListener) {
        if (this.listeners == null) {
            this.listeners = Lists.newArrayList();
        }

        this.listeners.add(inventoryListener);
    }

    public void removeListener(InventoryListener inventoryListener) {
        this.listeners.remove(inventoryListener);
    }

    public ItemStack getInvStack(int slot) {
        return slot >= 0 && slot < this.stackList.size() ? (ItemStack)this.stackList.get(slot) : ItemStack.EMPTY;
    }

    public ItemStack takeInvStack(int slot, int amount) {
        ItemStack itemStack = Inventories.splitStack(this.stackList, slot, amount);
        if (!itemStack.isEmpty()) {
            this.markDirty();
        }

        return itemStack;
    }

    public ItemStack poll(Item item, int count) {
        ItemStack itemStack = new ItemStack(item, 0);

        for(int i = this.size - 1; i >= 0; --i) {
            ItemStack itemStack2 = this.getInvStack(i);
            if (itemStack2.getItem().equals(item)) {
                int j = count - itemStack.getCount();
                ItemStack itemStack3 = itemStack2.split(j);
                itemStack.increment(itemStack3.getCount());
                if (itemStack.getCount() == count) {
                    break;
                }
            }
        }

        if (!itemStack.isEmpty()) {
            this.markDirty();
        }

        return itemStack;
    }

    public ItemStack add(ItemStack itemStack) {
        ItemStack itemStack2 = itemStack.copy();
        this.addToExistingSlot(itemStack2);
        if (itemStack2.isEmpty()) {
            return ItemStack.EMPTY;
        } else {
            this.addToNewSlot(itemStack2);
            return itemStack2.isEmpty() ? ItemStack.EMPTY : itemStack2;
        }
    }

    public ItemStack removeInvStack(int slot) {
        ItemStack itemStack = (ItemStack)this.stackList.get(slot);
        if (itemStack.isEmpty()) {
            return ItemStack.EMPTY;
        } else {
            this.stackList.set(slot, ItemStack.EMPTY);
            return itemStack;
        }
    }

    public void setInvStack(int slot, ItemStack stack) {
        this.stackList.set(slot, stack);
        if (!stack.isEmpty() && stack.getCount() > this.getInvMaxStackAmount()) {
            stack.setCount(this.getInvMaxStackAmount());
        }

        this.markDirty();
    }

    public int getInvSize() {
        return this.size;
    }

    public boolean isInvEmpty() {
        Iterator var1 = this.stackList.iterator();

        ItemStack itemStack;
        do {
            if (!var1.hasNext()) {
                return true;
            }

            itemStack = (ItemStack)var1.next();
        } while(itemStack.isEmpty());

        return false;
    }

    public void markDirty() {
        if (this.listeners != null) {
            Iterator var1 = this.listeners.iterator();

            while(var1.hasNext()) {
                InventoryListener inventoryListener = (InventoryListener)var1.next();
                inventoryListener.onInvChange(this);
            }
        }

    }

    public boolean canPlayerUseInv(PlayerEntity player) {
        return true;
    }

    public void clear() {
        this.stackList.clear();
        this.markDirty();
    }

    public String toString() {
        return ((List)this.stackList.stream().filter((itemStack) -> {
            return !itemStack.isEmpty();
        }).collect(Collectors.toList())).toString();
    }

    private void addToNewSlot(ItemStack stack) {
        for(int i = 0; i < this.size; ++i) {
            ItemStack itemStack = this.getInvStack(i);
            if (itemStack.isEmpty()) {
                this.setInvStack(i, stack.copy());
                stack.setCount(0);
                return;
            }
        }

    }

    private void addToExistingSlot(ItemStack stack) {
        for(int i = 0; i < this.size; ++i) {
            ItemStack itemStack = this.getInvStack(i);
            if (ItemStack.areItemsEqualIgnoreDamage(itemStack, stack)) {
                this.transfer(stack, itemStack);
                if (stack.isEmpty()) {
                    return;
                }
            }
        }

    }

    private void transfer(ItemStack source, ItemStack target) {
        int i = Math.min(this.getInvMaxStackAmount(), target.getMaxCount());
        int j = Math.min(source.getCount(), i - target.getCount());
        if (j > 0) {
            target.increment(j);
            source.decrement(j);
            this.markDirty();
        }

    }

}
