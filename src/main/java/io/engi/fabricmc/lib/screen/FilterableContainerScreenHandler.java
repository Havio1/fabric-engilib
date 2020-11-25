package io.engi.fabricmc.lib.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;

import java.util.function.Predicate;

public class FilterableContainerScreenHandler extends ScreenHandler implements InventoryScreenHandler {
    private final Inventory inventory;
    private final int rows;

    public FilterableContainerScreenHandler(
            ScreenHandlerType<?> type, int syncId,
            PlayerInventory playerInventory, Inventory inventory,
            int rows
    ) {
        super(type, syncId);
        this.inventory = inventory;
        this.rows = rows;
        inventory.onOpen(playerInventory.player);
        int i = (this.rows - 4) * 18;

        int n;
        int m;
        for(n = 0; n < this.rows; ++n) {
            for(m = 0; m < 9; ++m) {
                this.addSlot(new FilterableSlot(inventory, m + n * 9, 8 + m * 18, 18 + n * 18));
            }
        }

        for(n = 0; n < 3; ++n) {
            for(m = 0; m < 9; ++m) {
                this.addSlot(new Slot(playerInventory, m + n * 9 + 9, 8 + m * 18, 103 + n * 18 + i));
            }
        }

        for(n = 0; n < 9; ++n) {
            this.addSlot(new Slot(playerInventory, n, 8 + n * 18, 161 + i));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (index < this.rows * 9) {
                if (!this.insertItem(itemStack2, this.rows * 9, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(itemStack2, 0, this.rows * 9, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return itemStack;
    }

    @Override
    public void close(PlayerEntity player) {
        super.close(player);
        this.inventory.onClose(player);
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public void setFilter(Predicate<ItemStack> filter) {
        for (Slot slot : this.slots) {
            if (slot instanceof FilterableSlot) {
                ((FilterableSlot) slot).setFilter(filter);
            }
        }
    }

    @Override
    public int getRows() {
        return this.rows;
    }
}
