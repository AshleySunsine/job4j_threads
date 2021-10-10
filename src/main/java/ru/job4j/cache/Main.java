package ru.job4j.cache;

public class Main {
    public static void main(String[] args) {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        cache.add(base);

        Base user1 = cache.get(1);
        user1.setName("User 1");

        Base user2 = cache.get(1);
        user1.setName("User 2");

        Base user3 = cache.get(1);
        user3.setName("User 3");

        cache.add(user1);
        cache.add(user2);
        cache.update(user3);

        System.out.println(cache.get(1).getName() + System.lineSeparator());
    }
}