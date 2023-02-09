package io.engi.fabricmc.lib.util;

import io.engi.fabricmc.lib.util.stream.ItemStream;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;

public class InventoryHelper {
    public static DefaultedList<ItemStack> listFrom(Inventory inventory) {
        return ItemStream.ofInv(inventory).collect(ItemStream.toList(ItemStack.EMPTY));
    }

    public static <T extends Inventory> T invFrom(DefaultedList<ItemStack> list, T inventory) {
        for (int i = 0; i < list.size(); i++) {
            inventory.setStack(i, list.get(i));
        }
        return inventory;
    }

    public static <T> void replaceList(DefaultedList<T> target, DefaultedList<T> source) {
        for (int i = 0; i < target.size(); i++) {
            target.set(i, source.get(i));
        }
    }

    public static NbtCompound toTag(NbtCompound tag, Inventory inventory) {
        DefaultedList<ItemStack> items = DefaultedList.ofSize(inventory.size(), ItemStack.EMPTY);
        for (int i = 0; i < inventory.size(); i++) {
            items.set(i, inventory.getStack(i));
        }
        NbtCompound invTag = tag.getCompound("Inventory");
        invTag.putInt("Size", inventory.size());
        return Inventories.writeNbt(invTag, items);
    }
}
