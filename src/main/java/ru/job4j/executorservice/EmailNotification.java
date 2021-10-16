package ru.job4j.executorservice;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification implements EmailNotificationInterface {
    private final ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );
    @Override
    public String emailTo(User user) {
        return String.format("subject = Notification %s to email %s", user.getUserName(), user.geteMail())
                + System.lineSeparator()
                + String.format("body = Add a new event to %s", user.getUserName());
    }

    @Override
    public void close() {
        pool.shutdown();
    }

    @Override
    public void send(String subject, String body, String email) {
        System.out.println(body);
    }

    public static void main(String[] args) {
        User user1 = new User("User1", "user1@mail.ru");
        EmailNotification emailNotification = new EmailNotification();
        emailNotification.pool.submit(new Runnable() {
            @Override
            public void run() {
                String mailBody = emailNotification.emailTo(user1);
                String mailSubject = user1.getUserName();
                String mailEmail = user1.geteMail();
                emailNotification.send(mailSubject, mailBody, mailEmail);
            }
        });
        emailNotification.close();
        while (!emailNotification.pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
