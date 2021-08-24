package ru.job4j.buffer;

import ru.job4j.SimpleBlockingQueue;

public class ParallelSearch {

    public static void main(String[] args) throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(3);
        final Thread provider = new Thread(
                () -> {
                    for (int index = 0; index != 3; index++) {
                        queue.offer(index);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            e.printStackTrace();
                        }
                    }
                    try {
                        Thread.sleep(1000);
                        Thread.currentThread().interrupt();
                        System.out.println("STOPP provider");
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }

                }
        );

        final Thread consumer = new Thread(
                () -> {
                    while (!provider.isInterrupted()) {
                        System.out.println(queue.poll());
                        System.out.println("AAA");
                    }
                    try {
                        Thread.sleep(2000);
                        Thread.currentThread().interrupt();
                        System.out.println("STOPP costomer");
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
        );
        consumer.start();
        provider.start();
        consumer.join();
        provider.join();
    }
}