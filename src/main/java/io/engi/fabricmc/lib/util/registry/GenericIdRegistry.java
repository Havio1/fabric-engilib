package io.engi.fabricmc.lib.util.registry;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.Collection;

public class GenericIdRegistry<T> {
    private static final Logger LOGGER = LogManager.getLogger();

    private final BiMap<Identifier, T> map = HashBiMap.create();

    public T register(@Nonnull Identifier id, @Nonnull T value) {
        Preconditions.checkNotNull(id);
        Preconditions.checkNotNull(value);
        map.put(id, value);
        return value;
    }

    public Collection<T> all() {
        return map.values();
    }

    public Collection<Identifier> ids() {
        return map.keySet();
    }

    public T get(Identifier word) {
        return map.get(word);
    }

    public Identifier getId(T value) {
        return map.inverse().get(value);
    }
}
