package io.engi.fabricmc.lib.util.stream;

import io.engi.fabricmc.lib.inventory.VirtualInventory;
import lombok.RequiredArgsConstructor;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ItemStream {
    @RequiredArgsConstructor
    public static class VirtualInventoryCollector extends AbstractListAccumulatedCollector<ItemStack, VirtualInventory> {
        protected final int size;

        @Override
        public Function<ArrayList<ItemStack>, VirtualInventory> finisher() {
            return itemStacks -> new VirtualInventory(size, itemStacks);
        }
    }

    @RequiredArgsConstructor
    public static class DefaultedListCollector<T> extends AbstractListAccumulatedCollector<T, DefaultedList<T>> {
        protected final T defaultValue;

        @SuppressWarnings("unchecked")
        @Override
        public Function<ArrayList<T>, DefaultedList<T>> finisher() {
            return values -> DefaultedList.copyOf(defaultValue, (T[]) values.toArray());
        }
    }

    public static boolean isNotEmpty(ItemStack item) {
        return !item.isEmpty();
    }

    public static <T extends Item> Stream<T> ofIds(Collection<Identifier> ids, Class<T> itemClass) {
        return ids.stream().map(Registry.ITEM::get).map(itemClass::cast);
    }

    public static Stream<ItemStack> ofInv(Inventory inv) {
        return IntStream.range(0, inv.size()).mapToObj(inv::getStack);
    }

    public static <T> DefaultedListCollector<T> toList(T defaultValue) {
        return new DefaultedListCollector<>(defaultValue);
    }

    public static VirtualInventoryCollector toInv(int size) {
        return new VirtualInventoryCollector(size);
    }
}
