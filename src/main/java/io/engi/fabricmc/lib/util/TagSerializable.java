package io.engi.fabricmc.lib.util;

import com.mojang.datafixers.types.templates.Tag;
import net.minecraft.tag.TagKey;

public interface TagSerializable<T extends Tag> {
    T serializeNbt();
    void deserializeNbt(T tag);
}
