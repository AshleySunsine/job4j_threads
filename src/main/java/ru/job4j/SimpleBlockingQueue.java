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

    public synchronized void offer(T value) throws InterruptedException {
            while (queue.size() >= size) {
                this.wait();
            }
            this.notify();
            queue.offer(value);
        }

        public synchronized T poll() throws InterruptedException {
        while (queue.size() == 0) {
            this.wait();
        }
        this.notify();
        return queue.poll();

    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}
