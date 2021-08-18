package ru.job4j.ref;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class UserCache {
    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger();

    public void add(User user) {
        users.put(id.incrementAndGet(), User.of(user.getName()));
    }

    public synchronized User findById(int id) {
        return User.of(users.get(id).getName());
    }

    public synchronized List<User> findAll() {
        List<User> newList = new ArrayList<>();
        for (var i : users.entrySet()) {
            newList.add(User.of(i.getValue().getName()));
        }
        return newList;
    }
}