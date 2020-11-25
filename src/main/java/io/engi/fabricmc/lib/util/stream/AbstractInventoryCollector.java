package io.engi.fabricmc.lib.util.stream;

import com.google.common.collect.ImmutableSet;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.stream.Collector;

public abstract class AbstractInventoryCollector<I extends Inventory> implements Collector<ItemStack, ArrayList<ItemStack>, I> {
    @Override
    public Supplier<ArrayList<ItemStack>> supplier() {
        return ArrayList::new;
    }

    @Override
    public BiConsumer<ArrayList<ItemStack>, ItemStack> accumulator() {
        return ArrayList::add;
    }

    @Override
    public BinaryOperator<ArrayList<ItemStack>> combiner() {
        return (left, right) -> {
            left.addAll(right);
            return left;
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return ImmutableSet.of();
    }
}
