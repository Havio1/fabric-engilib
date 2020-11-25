package io.engi.fabricmc.lib.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;

import java.util.function.Predicate;

public class NonStackableInventory extends VirtualInventory {
    private final Predicate<ItemStack> filter;

    public NonStackableInventory(CompoundTag tag, int defaultSize, Predicate<ItemStack> filter) {
        super(tag, defaultSize);
        this.filter = filter;
    }

    @Override
    public boolean isValid(int slot, ItemStack stack) {
        return filter.test(stack);
    }

    @Override
    public int getMaxCountPerStack() {
        return 1;
    }
}
