package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    @Override
    public void run() {
        String[] process = {"-", "\\", "|", "/"};
        try {
            while (!Thread.currentThread().isInterrupted()) {
                for (int i = 0; i < 4; i++) {
                    System.out.print("\r load: " + process[i]);
                    Thread.sleep(500);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(10000); /* симулируем выполнение параллельной задачи в течение 1 секунды. */
        progress.interrupt();
    }
}
