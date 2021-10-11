package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(), (integer, base) -> {
            Integer key = base.getId();
            Base stored = memory.get(key);
            if (stored.getVersion() != base.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            return memory.replace(key, new Base(base.getId(), base.getVersion() + 1));
        }) != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId());
    }
    public Base get(Integer key) {
        return memory.getOrDefault(key, null);
    }
}