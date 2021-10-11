package ru.job4j.cache;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class CacheTest {

    @Test
    public void add() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        cache.add(base);

        Base user1 = cache.get(1);
        user1.setName("User 1");

        Base user2 = cache.get(1);
        user1.setName("User 2");
        cache.add(user1);
        cache.add(user2);
        assertThat(cache.get(1), is(user2));
    }

    @Test
    public void update() {
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
        assertThat(cache.get(1), is(user3));

    }

    @Test
    public void delete() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        cache.add(base);
        cache.delete(base);
        assertNull(cache.get(1));
    }

    @Test
    public void get() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        cache.add(base);
        assertEquals(cache.get(1), base);
    }

    @Test
    public void updateWithException() throws OptimisticException, InterruptedException {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        cache.add(base);
        Thread first = new Thread(() -> {
            Base user1 = cache.get(1);
            user1.setName("User 1");
            cache.update(user1);
        });
        Thread second = new Thread(() -> {
            Base user1 = cache.get(1);
            user1.setName("User 222");
            cache.update(user1);
        });
       first.start();
       second.start();
       first.join();
       second.join();

    }
}