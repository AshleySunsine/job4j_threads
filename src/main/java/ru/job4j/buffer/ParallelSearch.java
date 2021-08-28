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
                    Thread.currentThread().interrupt();
                    System.out.println(Thread.currentThread().getName() + "  " + Thread.currentThread().getState());
                }
        );

        final Thread consumer = new Thread(
                () -> {
                    while (!(provider.isInterrupted())) {
                        try {
                            System.out.println(queue.poll());
                            System.out.println("AAA");
                            Thread.sleep(1);
                            System.out.println("bbb");
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            System.out.println("UUU");
                            break;
                        }
                    }
                }
        );
        provider.start();
        consumer.start();
        provider.join();
        consumer.join();

    }
}