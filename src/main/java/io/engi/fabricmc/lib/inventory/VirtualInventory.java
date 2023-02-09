package io.engi.fabricmc.lib.inventory;

import io.engi.fabricmc.lib.util.InventoryHelper;
import io.engi.fabricmc.lib.util.stream.ItemStream;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryChangedListener;
import net.minecraft.item.ItemStack;
// import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class VirtualInventory implements Inventory, Iterable<ItemStack> {
    private final DefaultedList<ItemStack> stacks;
    private final List<InventoryChangedListener> listeners = new ArrayList<>();

    public VirtualInventory(int size) {
        stacks = DefaultedList.ofSize(size, ItemStack.EMPTY);
    }

    public VirtualInventory(int size, List<ItemStack> stacks) {
        this(size);
        for (int i = 0; i < stacks.size(); i++) {
            stacks.set(i, stacks.get(i) == null ? ItemStack.EMPTY : stacks.get(i));
        }
    }

    public VirtualInventory(NbtCompound tag) {
        this(tag, 0);
    }

    public VirtualInventory(NbtCompound tag, int defaultSize) {
        int size = tag.getInt("Size");
        if (size == 0) size = defaultSize;
        stacks = DefaultedList.ofSize(size, ItemStack.EMPTY);
        Inventories.readNbt(tag, stacks);
    }

    @SuppressWarnings("UnusedReturnValue")
    public NbtCompound toTag(NbtCompound tag, Inventory inventory) {
        return InventoryHelper.toTag(tag, inventory);
    }

    public void addListener(InventoryChangedListener inventoryListener) {
        listeners.add(inventoryListener);
    }

    public void removeListener(InventoryChangedListener inventoryListener) {
        listeners.remove(inventoryListener);
    }

    @Override
    public int size() {
        return stacks.size();
    }

    @Override
    public boolean isEmpty() {
        return stacks.stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public ItemStack getStack(int slot) {
        return stacks.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack result = Inventories.splitStack(stacks, slot, amount);
        markDirty();
        return result;
    }

    @Override
    public ItemStack removeStack(int slot) {
        ItemStack result = Inventories.removeStack(stacks, slot);
        markDirty();
        return result;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        stacks.set(slot, stack);
        if (stack.getCount() > getMaxCountPerStack()) {
            stack.setCount(getMaxCountPerStack());
        }
        markDirty();
    }

    @Override
    public void markDirty() {
        for (InventoryChangedListener listener : listeners) {
            listener.onInventoryChanged(this);
        }
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void clear() {
        stacks.clear();
        markDirty();
    }

    @Override
    public String toString() {
        return stacks.stream()
                .filter(ItemStream::isNotEmpty)
                .collect(Collectors.toList())
                .toString();
    }

    public DefaultedList<ItemStack> getStacks() {
        return stacks;
    }

    public List<ItemStack> getPresentStacks() {
        return stacks.stream().filter(stack -> stack != ItemStack.EMPTY).collect(Collectors.toList());
    }

    @Override
    public Iterator<ItemStack> iterator() {
        return stacks.iterator();
    }

    @Override
    public void forEach(Consumer<? super ItemStack> action) {
        stacks.forEach(action);
    }

    @Override
    public Spliterator<ItemStack> spliterator() {
        return stacks.spliterator();
    }

    public Stream<ItemStack> stream() {
        return stacks.stream();
    }
}
