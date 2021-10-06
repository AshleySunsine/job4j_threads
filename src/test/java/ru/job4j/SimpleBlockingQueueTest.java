package ru.job4j;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;


public class SimpleBlockingQueueTest {

    @Test
    public void test() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(2);
        List<Integer> list = new ArrayList<>(List.of(4, 6, 54, 76, 98, 1000));
        List<Integer> except = new ArrayList<>();
        Thread master = new Thread(() -> {
            try {
                for (var i : list) {
                    queue.offer(i);
                }
            } catch (Exception e) {
            e.printStackTrace();
        }
        });
        Thread slave = new Thread(() -> {
            try {
            except.add(queue.poll());
            except.add(queue.poll());
            except.add(queue.poll());
            except.add(queue.poll());
            except.add(queue.poll());
            except.add(queue.poll());
        } catch (Exception e) {
                e.printStackTrace();
            }
        });
        master.start();
        slave.start();
        master.join();
        slave.join();
        Assert.assertEquals(except, list);
    }

    @Test
    public void whenFetchAllAndGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        Thread producer = new Thread(
                () -> {
                    IntStream.range(0, 5).forEach((v) -> {
                        try {
                            queue.offer(v);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                    );
                }
        );

        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer, is(Arrays.asList(0, 1, 2, 3, 4))
        );
    }

    @Test
    public void testPutAllAndGetAll() throws InterruptedException {
        final List<Integer> finishList = new ArrayList<>(10);
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        Thread putThread = new Thread(
                () -> {
                    try {
                        for (int i = 0; i < 5; i++) {  //for - для разнообразия
                            queue.offer(i);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                }
        );
        putThread.start();

        Thread getThread = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            finishList.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        getThread.start();
        putThread.join();
        getThread.interrupt();
        getThread.join();
        assertThat(finishList, is(Arrays.asList(0, 1, 2, 3, 4)));
    }

}