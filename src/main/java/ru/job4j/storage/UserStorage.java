package ru.job4j.storage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ThreadSafe
public class UserStorage implements Storage {
    @GuardedBy("this")
    private volatile List<User> users = new ArrayList<>();

    @Override
    public synchronized boolean transfer(int fromId, int toId, int amount) {
        Optional<User> userFromOpt = searchUser(fromId);
        Optional<User> userToOpt = searchUser(toId);
        if (userFromOpt.isPresent() && userToOpt.isPresent()) {
            User userFrom = userFromOpt.get();
            User userTo = userToOpt.get();
            User newUserTo = new User(userTo.getId(), userTo.getAmount() + amount);
            User newUserFrom = new User(userFrom.getId(), userFrom.getAmount() - amount);
            return delete(userTo)
                    && delete(userFrom)
                    && add(newUserTo)
                    && add(newUserFrom);
        }
        return false;
    }

    @Override
    public synchronized boolean add(User user) {
        Optional<User> userItem = searchUser(user.getId());
        if (userItem.isEmpty()) {
            users.add(user);
            return true;
        }
        return false;
    }

    @Override
    public synchronized boolean update(User user) {
        Optional<User> userItem = searchUser(user.getId());
        if (userItem.isPresent()) {
            User item = userItem.get();
            users.remove(item);
            users.add(user);
            return true;
        }
        return false;
    }

    @Override
    public synchronized boolean delete(User user) {
        Optional<User> userItem = searchUser(user.getId());
        if (userItem.isPresent()) {
            users.remove(userItem.get());
            return true;
        }
        return false;
    }

    private synchronized Optional<User> searchUser(int id) {
        for (var i : users) {
            if (i.getId() == id) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }
}
