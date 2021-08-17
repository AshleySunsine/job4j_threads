package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    @Override
    public void run() {
        String[] process = {"-", "\\", "|", "/"};
        try {
            while (!Thread.currentThread().isInterrupted()) {
                for (String s : process) {
                    System.out.print("\r load: " + s);
                    Thread.sleep(500);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(5000); /* симулируем выполнение параллельной задачи в течение 1 секунды. */
        progress.interrupt();
    }
}
