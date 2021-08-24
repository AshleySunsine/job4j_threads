package ru.job4j.synch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {

    @GuardedBy("this")
    private final List<T> list;

    public SingleLockList(List<T> list) {
        synchronized (this) {
            this.list = copy(list);
        }
    }

    public synchronized void add(T value) {
        this.list.add(value);
    }

    public synchronized T get(int index) {
        List<T> newCollect = new ArrayList<>(this.list);
        return newCollect.get(index);
    }

    public synchronized Iterator<T> iterator() {
        return copy(this.list).iterator();
    }

    private synchronized List<T> copy(List<T> collection) {
        return new ArrayList<>(collection);
    }

}