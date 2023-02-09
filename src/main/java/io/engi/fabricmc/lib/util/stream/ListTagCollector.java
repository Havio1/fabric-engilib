package io.engi.fabricmc.lib.util.stream;

import com.google.common.collect.ImmutableSet;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import com.mojang.datafixers.types.templates.Tag;

import java.util.ArrayList;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class ListTagCollector implements Collector<Tag, ArrayList<Tag>, NbtList> {
    @Override
    public Supplier<ArrayList<Tag>> supplier() {
        return ArrayList::new;
    }

    @Override
    public BiConsumer<ArrayList<Tag>, Tag> accumulator() {
        return ArrayList::add;
    }

    @Override
    public BinaryOperator<ArrayList<Tag>> combiner() {
        return (left, right) -> {
            left.addAll(right);
            return left;
        };
    }

    @Override
    public Function<ArrayList<Tag>, NbtList> finisher() {
        return tags -> {
            NbtList tag = new NbtList();
            tag.add((NbtElement) tags);
            return tag;
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return ImmutableSet.of();
    }

    public static ListTagCollector toListTag() {
        return new ListTagCollector();
    }
}
