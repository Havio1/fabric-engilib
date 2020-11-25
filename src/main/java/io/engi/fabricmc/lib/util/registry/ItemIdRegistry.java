package io.engi.fabricmc.lib.util.registry;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import javax.annotation.Nonnull;

public class ItemIdRegistry<T extends Item> extends GenericIdRegistry<T> {
    @Override
    public T register(@Nonnull Identifier id, @Nonnull T value) {
        T item = super.register(id, value);
        Registry.register(Registry.ITEM, id, item);
        return item;
    }
}
