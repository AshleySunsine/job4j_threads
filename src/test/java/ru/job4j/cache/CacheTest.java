package ru.job4j.cache;

import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class CacheTest {

    @Test
    public void whenAddNewVersionOfSameModel() {
        Cache cache = new Cache();
        Base model1 = new Base(1, 1, "1");
        Base model2 = new Base(1, 2, "2");
        cache.add(model1);
        assertThat(cache.add(model2), is(false));
    }

    @Test
    public void update() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 0, "1");
        Base base2 = new Base(2, 0, "2");
        cache.add(base1);
        cache.add(base2);
        Base newBase = cache.get(1);
        newBase.setName("newBase1");
        cache.update(newBase);
        assertEquals(cache.get(1).getName(), "newBase1");
    }

    @Test
    public void delete() {
        Cache cache = new Cache();
        Base base = new Base(1, 0, "1");
        cache.add(base);
        cache.delete(base);
        assertNull(cache.get(1));
    }

    @Test
    public void get() {
        Cache cache = new Cache();
        Base base = new Base(1, 0, "1");
        cache.add(base);
        assertEquals(cache.get(1), base);
    }

    @Test
    public void updateWithException() throws OptimisticException, InterruptedException {
        Cache cache = new Cache();
        Base base = new Base(1, 0,"1");
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