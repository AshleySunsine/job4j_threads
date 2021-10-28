package ru.job4j.pooh;

import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {

    private final ConcurrentHashMap<String,
            ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> queueTopic = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        if ("POST".equals(req.httpRequestType())) {
            topicPutIfAbsent(req);
            return new Resp("ok", "200");
        }
        if ("GET".equals(req.httpRequestType())) {
        String ex = topicExtract(req);
        if (null == ex || ex.equals(req.getParam())) {
            return new Resp("", "200");
        } else {
            return new Resp(ex, "200");
        }
        }
        return null;
    }

    private void topicPutIfAbsent(Req req) {
        queueTopic.forEach((key, value) -> value.put(req.getSourceName(), new ConcurrentLinkedQueue<>(Collections.singleton(req.getParam()))));
    }

    private String topicExtract(Req req) {
        return queueTopic.computeIfAbsent(req.getParam(),
                k -> createValue(req)).get(req.getSourceName()).poll();
    }

    private ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> createValue(Req req) {
        ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> person = new ConcurrentHashMap<>();
        ConcurrentLinkedQueue<String> newQueue = new ConcurrentLinkedQueue<>();
        newQueue.add(req.getParam());
        person.putIfAbsent(req.getSourceName(), newQueue);
        return person;
    }
}