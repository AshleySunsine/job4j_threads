package ru.job4j.pool;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ThreadPool implements ThreadPoolInterface {
    private final int availableProcessors = Runtime.getRuntime().availableProcessors();
    private final List<Thread> threads = new CopyOnWriteArrayList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(availableProcessors);

    public void work(Runnable job) {
        Thread jobThread = new Thread(job);
        try {
         tasks.offer(jobThread);
         if (threads.size() < availableProcessors) {
         while (!tasks.isEmpty() && availableProcessors > threads.size()) {
                 Thread thread = new Thread(tasks.poll());
                 threads.add(thread);
             }
         }
            if (!threads.isEmpty()) {
                for (var thread : threads) {
                    if (thread.getState().equals(Thread.State.NEW)) {
                        thread.start();
                    } else if (!thread.getState().equals(Thread.State.TERMINATED)) {
                        threads.remove(thread);
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool pool = new ThreadPool();
        for (int i = 0; i < 21; i++) {
            pool.work(new Task());
        }
        for (Thread thread : pool.threads) {
            thread.join();
        }
    }
}