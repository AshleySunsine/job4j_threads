package ru.job4j.pool;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ThreadPool implements ThreadPoolInterface {

    private final List<Thread> threads = new CopyOnWriteArrayList<>();
    private final SimpleBlockingQueue<Runnable> tasks;

    public ThreadPool(int value) {
        this.tasks = new SimpleBlockingQueue<>(value);
        for (int i = 0; i < value; i++) {
            Thread thread = new Thread(() -> {
                while (true) {
                    try {
                        Runnable task = tasks.poll();
                        task.run();
                    } catch (InterruptedException e) {
                        break;
                    }
               }
            });
            thread.start();
            threads.add(thread);
        }
    }

    public void work(Runnable job) {
        try {
            tasks.offer(job);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool pool = new ThreadPool(Runtime.getRuntime().availableProcessors());
        for (int i = 0; i < 21; i++) {
            pool.work(new Task());
        }

    }
}