package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>();

    /**
     *
     * @param start - стартовое значение счётчика
     */
    public CASCount(int start) {
        this.count.set(start);
    }

    public void increment() {
        boolean ok;
        do {
            int now = count.get();
            int next = now + 1;
            ok = this.count.compareAndSet(now, next);
        } while (!ok);
    }

    public int get() {
        return count.get();
    }

    /**
     * Вызовем инкремент по 5 раз из разных потоков
     */
    public static void main(String[] args) throws InterruptedException {
        CASCount casCount = new CASCount(0);
        Thread first = new Thread(() -> {
                    for (int i = 0; i < 5; i++) {
                        casCount.increment();
                    }
                }
        );
        first.start();
        Thread second = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                casCount.increment();
            }
        }
        );
        second.start();

        first.join();
        second.join();
        first.interrupt();
        second.interrupt();
        System.out.println(casCount.get());
    }
}