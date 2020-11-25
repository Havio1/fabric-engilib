package io.engi.fabricmc.lib.util;

import net.minecraft.nbt.Tag;

public interface TagSerializable<T extends Tag> {
    T serializeNbt();
    void deserializeNbt(T tag);
}
