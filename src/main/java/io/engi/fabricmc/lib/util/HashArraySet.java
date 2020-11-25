package io.engi.fabricmc.lib.util;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * An implementation of a collection that implements both {@link Set} and {@link List},
 * backed by a {@link HashSet} and an {@link ArrayList}. In practice, this may be used
 * as an ordered list with element uniqueness, or an "ordered set" that follows
 * insertion order.
 * @param <E>
 */
public class HashArraySet<E> implements Set<E>, List<E> {
    private final HashSet<E> setDelegate = new HashSet<>();
    private final ArrayList<E> listDelegate = new ArrayList<>();

    public HashArraySet() {}

    public HashArraySet(Collection<E> c) {
        addAll(c);
    }

    @Override
    public int size() {
        return setDelegate.size();
    }

    @Override
    public boolean isEmpty() {
        return setDelegate.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return setDelegate.contains(o);
    }

    @Override
    @Nonnull
    public Iterator<E> iterator() {
        return listDelegate.iterator();
    }

    @Override
    @Nonnull
    public Object[] toArray() {
        return listDelegate.toArray();
    }

    @SuppressWarnings("SuspiciousToArrayCall")
    @Override
    @Nonnull
    public <T> T[] toArray(@Nonnull T[] a) {
        return listDelegate.toArray(a);
    }

    @Override
    public boolean add(E e) {
        if (setDelegate.add(e)) {
            listDelegate.add(e);
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return setDelegate.remove(o) && listDelegate.remove(o);
    }

    @Override
    public boolean containsAll(@Nonnull Collection<?> c) {
        return setDelegate.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false;
        for (E e : c) {
            if (add(e)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        ArrayList<E> toAdd = new ArrayList<>();
        boolean modified = false;
        for (E e : c) {
            if (setDelegate.add(e)) {
                toAdd.add(e);
                modified = true;
            }
        }
        if (toAdd.size() > 1) {
            listDelegate.addAll(index, toAdd);
        }
        return modified;
    }

    @Override
    public boolean retainAll(@Nonnull Collection<?> c) {
        Objects.requireNonNull(c);
        boolean modified = false;
        Iterator<E> it = setDelegate.iterator();
        while (it.hasNext()) {
            E e = it.next();
            if (!c.contains(e)) {
                it.remove();
                listDelegate.remove(e);
                modified = true;
            }
        }
        return modified;
    }

    @SuppressWarnings("ForLoopReplaceableByForEach")
    @Override
    public boolean removeAll(@Nonnull Collection<?> c) {
        Objects.requireNonNull(c);
        boolean modified = false;

        if (size() > c.size()) {
            for (Iterator<?> i = c.iterator(); i.hasNext(); ) {
                modified |= remove(i.next());
            }
        } else {
            for (Iterator<E> i = iterator(); i.hasNext(); ) {
                E e = i.next();
                if (c.contains(i.next())) {
                    remove(e);
                    modified = true;
                }
            }
        }
        return modified;
    }

    @Override
    public void clear() {
        listDelegate.clear();
        setDelegate.clear();
    }

    @Override
    public E get(int index) {
        return listDelegate.get(index);
    }

    @Override
    public E set(int index, E element) {
        setDelegate.add(element);
        return listDelegate.set(index, element);
    }

    @Override
    public void add(int index, E element) {
        if (setDelegate.add(element)) {
            listDelegate.add(index, element);
        }
    }

    @Override
    public E remove(int index) {
        E e = listDelegate.get(index);
        if (setDelegate.remove(e)) {
            return listDelegate.remove(index);
        }
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return listDelegate.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return listDelegate.lastIndexOf(o);
    }

    @Override
    @Nonnull
    public ListIterator<E> listIterator() {
        return listDelegate.listIterator();
    }

    @Override
    @Nonnull
    public ListIterator<E> listIterator(int index) {
        return listDelegate.listIterator(index);
    }

    @Override
    @Nonnull
    public List<E> subList(int fromIndex, int toIndex) {
        return new HashArraySet<>(listDelegate.subList(fromIndex, toIndex));
    }

    @Override
    public Spliterator<E> spliterator() {
        return listDelegate.spliterator();
    }
}
