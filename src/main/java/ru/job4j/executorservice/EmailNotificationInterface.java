package ru.job4j.executorservice;

public interface EmailNotificationInterface {
    String emailTo(User user);
    void close();
    void send(String subject, String body, String email);
}
