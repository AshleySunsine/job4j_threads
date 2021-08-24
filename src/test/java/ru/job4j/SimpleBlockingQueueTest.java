package ru.job4j;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SimpleBlockingQueueTest {

    @Test
    public void test() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(2);
        List<Integer> list = new ArrayList<>(List.of(4, 6, 54, 76, 98, 1000));
        List<Integer> except = new ArrayList<>();
        Thread master = new Thread(() -> {
            for (var i : list) {
                queue.offer(i);
            }
        });
        Thread slave = new Thread(() ->{
            except.add(queue.poll());
            except.add(queue.poll());
            except.add(queue.poll());
            except.add(queue.poll());
            except.add(queue.poll());
            except.add(queue.poll());
        });
        master.start();
        slave.start();
        master.join();
        slave.join();
        Assert.assertEquals(except, list);
    }
}