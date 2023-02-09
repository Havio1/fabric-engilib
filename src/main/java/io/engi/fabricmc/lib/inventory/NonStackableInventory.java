package io.engi.fabricmc.lib.inventory;

import net.minecraft.nbt.NbtCompound;

public class NonStackableInventory extends VirtualInventory {
    public NonStackableInventory(NbtCompound tag, int defaultSize) {
        super(tag, defaultSize);
    }

    @Override
    public int getMaxCountPerStack() {
        return 1;
    }
}
