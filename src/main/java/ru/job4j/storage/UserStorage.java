package ru.job4j.storage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.*;

@ThreadSafe
public class UserStorage implements Storage {
    @GuardedBy("this")
    private final Map<Integer, User> users = new HashMap<>();

    @Override
    public synchronized boolean transfer(int fromId, int toId, int amount) {
        Optional<User> userFromOpt = searchUser(fromId);
        Optional<User> userToOpt = searchUser(toId);
        if (userFromOpt.isPresent() && userToOpt.isPresent()) {
            userFromOpt.get().setAmount(userFromOpt.get().getAmount() - amount);
            userToOpt.get().setAmount(userToOpt.get().getAmount() + amount);
            return true;
        }
        return false;
    }

    @Override
    public synchronized boolean add(User user) {
        return users.putIfAbsent(user.getId(), user) != null;
    }

    @Override
    public synchronized boolean update(User user) {
        return users.replace(user.getId(), user) != null;
    }

    @Override
    public synchronized boolean delete(User user) {
        return users.remove(user.getId(), user);
    }

    private synchronized Optional<User> searchUser(int id) {
        for (var i : users.entrySet()) {
            if (i.getValue().getId() == id) {
                return Optional.of(i.getValue());
            }
        }
        return Optional.empty();
    }

    public void printState() {
        for (var u : users.entrySet()) {
            System.out.println("user: " + u.getValue().getId() + " " + "has amount: " + u.getValue().getAmount());
        }
    }

    public static void main(String[] args) {
       User user1 =  new User(1, 20000);
       User user2 = new User(2, 30000);
        UserStorage userStorage = new UserStorage();
        userStorage.add(user1);
        userStorage.add(user2);

        userStorage.transfer(1, 2, 5000);
        userStorage.printState();
    }
}
