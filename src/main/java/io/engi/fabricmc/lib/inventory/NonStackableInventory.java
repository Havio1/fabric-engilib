package io.engi.fabricmc.lib.inventory;

import net.minecraft.nbt.CompoundTag;

public class NonStackableInventory extends VirtualInventory {
    public NonStackableInventory(CompoundTag tag, int defaultSize) {
        super(tag, defaultSize);
    }

    @Override
    public int getMaxCountPerStack() {
        return 1;
    }
}
