package ru.job4j.pooh;

public class QueueService implements Service {
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
            var l = CASMap.putIfAbsent(req);
            if (null != l) {
                CASMap.put(req);
            }
        } catch (Exception e) {
            return new Resp(e.toString(), "500");
        }
        System.out.println(CASMap.queue.get(req.getSourceName()));
        return new Resp(req.getParam() + " added to" + req.getSourceName(), "200");
    }

    private Resp get(Req req) {
        var l = CASMap.extract(req);
        if (!l.isEmpty()) {
            System.out.println(l);
            return new Resp(l, "200");
        }
        return new Resp("", "500");
    }
}