package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) throws OptimisticException {
        return memory.computeIfPresent(model.getId(), (integer, stored) -> {
            int key = stored.getId();
            int oldVersion = stored.getVersion();
            if (oldVersion != model.getVersion()) {
               throw new OptimisticException("Versions are not equal");
            }
            return new Base(key, oldVersion + 1, model.getName());
        }) != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId());
    }
    public Base get(Integer key) {
        return memory.getOrDefault(key, null);
    }
}