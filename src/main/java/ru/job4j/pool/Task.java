package ru.job4j.pool;

public class Task implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
        Thread.currentThread().interrupt();
    }
}
