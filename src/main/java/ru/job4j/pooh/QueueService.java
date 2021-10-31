package ru.job4j.pooh;

import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {
    private static ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();
    @Override
    public Resp process(Req req) {
        if ("POST".equals(req.httpRequestType())) {
            return post(req);
        }
        if ("GET".equals(req.httpRequestType())) {
            return get(req);
        }
        return new Resp("type GET or POST", "500");
    }

    private Resp post(Req req) {
        try {
            var l = putIfAbsent(req);
            if (null != l) {
                put(req);
            }
        } catch (Exception e) {
            return new Resp(e.toString(), "500");
        }
        System.out.println(queue.get(req.getSourceName()));
        return new Resp(req.getParam() + " added to" + req.getSourceName(), "200");
    }

    private Resp get(Req req) {
        var l = extract(req);
        if (!l.isEmpty()) {
            System.out.println(l);
            return new Resp(l, "200");
        }
        return new Resp("", "500");
    }

    private ConcurrentLinkedQueue<String> putIfAbsent(Req req) {
        return queue.putIfAbsent(req.getSourceName(),
                new ConcurrentLinkedQueue<>(Collections.singleton(req.getParam())));
    }

    private void put(Req req) {
        queue.get(req.getSourceName()).add(req.getParam());
    }

    private String extract(Req req) {
        return queue.getOrDefault(req.getSourceName(), new ConcurrentLinkedQueue<>()).poll();
    }
}
/* Изменения для задачи 3.4. Временное хранение. git stash. [#309409]*/