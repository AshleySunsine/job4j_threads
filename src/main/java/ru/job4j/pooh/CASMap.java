package ru.job4j.pooh;

import java.util.Collections;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class CASMap {

   static ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();
    static ConcurrentHashMap<String,
            ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> queueTopic = new ConcurrentHashMap<>();

    protected static ConcurrentLinkedQueue<String> putIfAbsent(Req req) {
        return queue.putIfAbsent(req.getSourceName(),
                new ConcurrentLinkedQueue<>(Collections.singleton(req.getParam())));
    }

    protected static void put(Req req) {
        queue.get(req.getSourceName()).add(req.getParam());
    }

    protected static String extract(Req req) {
        return queue.getOrDefault(req.getSourceName(), new ConcurrentLinkedQueue<>()).poll();
    }

    protected static void topicPutIfAbsent(Req req) {
         queueTopic.entrySet().forEach(i -> {
            i.getValue().putIfAbsent(req.getSourceName(), new ConcurrentLinkedQueue<String>(Collections.singleton(req.getParam())));
        });

    }

    protected static String topicExtract(Req req) {
        return queueTopic.computeIfAbsent(req.getParam(),
                k -> createValue(req)).get(req.getSourceName()).poll();
    }

    private static ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> createValue(Req req) {
        ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> person = new ConcurrentHashMap<>();
        ConcurrentLinkedQueue<String> newQueue = new ConcurrentLinkedQueue<>();
        newQueue.add(req.getParam());
        person.putIfAbsent(req.getSourceName(), newQueue);
        return person;
    }

}