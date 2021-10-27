package ru.job4j.pooh;

public class TopicService implements Service {
    @Override
    public Resp process(Req req) {
        if ("POST".equals(req.httpRequestType())) {
            CASMap.topicPutIfAbsent(req);
            return new Resp("ok", "200");
        }
        if ("GET".equals(req.httpRequestType())) {
        String ex = CASMap.topicExtract(req);
        if (ex == null) {
            return new Resp("PODPISKA", "200");
        } else {
            return new Resp(ex, "200");
        }
        }
        return null;
    }
}