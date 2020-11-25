package io.engi.fabricmc.lib.item;

import net.minecraft.item.ItemStack;

@FunctionalInterface
public interface StackColorProvider {
    int accept(ItemStack stack);
}
