package ru.job4j.pool;

public interface ThreadPoolInterface {
    void work(Runnable job) throws InterruptedException;
    void shutdown();
}
