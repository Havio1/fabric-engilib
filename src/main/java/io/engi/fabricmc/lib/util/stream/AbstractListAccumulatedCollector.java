package io.engi.fabricmc.lib.util.stream;

import com.google.common.collect.ImmutableSet;

import java.util.ArrayList;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.stream.Collector;

public abstract class AbstractListAccumulatedCollector<I, O> implements Collector<I, ArrayList<I>, O> {
    @Override
    public Supplier<ArrayList<I>> supplier() {
        return ArrayList::new;
    }

    @Override
    public BiConsumer<ArrayList<I>, I> accumulator() {
        return ArrayList::add;
    }

    @Override
    public BinaryOperator<ArrayList<I>> combiner() {
        return (left, right) -> {
            left.addAll(right);
            return left;
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return ImmutableSet.of();
    }
}
