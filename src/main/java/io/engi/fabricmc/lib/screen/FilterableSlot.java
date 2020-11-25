package io.engi.fabricmc.lib.screen;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

import java.util.function.Predicate;

public class FilterableSlot extends Slot {
    private Predicate<ItemStack> filter;

    public FilterableSlot(Inventory inventory, int index, int x, int y, Predicate<ItemStack> filter) {
        this(inventory, index, x, y);
        this.filter = filter;
    }

    public FilterableSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    public void setFilter(Predicate<ItemStack> filter) {
        this.filter = filter;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        if (filter == null) return true;
        return filter.test(stack);
    }
}
