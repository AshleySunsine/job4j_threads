package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

        @GuardedBy("this")
        private final Queue<T> queue = new LinkedList<>();
        private final int size;

    public SimpleBlockingQueue(int value) {
        this.size = value;
    }

    public synchronized void offer(T value) {
            while (queue.size() >= size) {
                try {
                    this.wait();
                    System.out.println(Thread.currentThread() + " wait.");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            this.notify();
            System.out.println(Thread.currentThread() + " go.");
            queue.offer(value);
        }

        public synchronized T poll() {
        while (queue.size() == 0) {
        try {
            this.wait();
            System.out.println(Thread.currentThread() + " wait.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        }
        this.notify();
        System.out.println(Thread.currentThread() + " go.");
        return queue.poll();
    }
}
