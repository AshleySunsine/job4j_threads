package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String file;

    public Wget(String url, int speed, String file) {
        this.url = url;
        this.speed = speed;
        this.file = file;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead = 0;
            try {
                while (bytesRead != -1) {
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                    double speedNow;
                    long start = System.nanoTime();
                    bytesRead = in.read(dataBuffer, 0, 1024);
                    long finish = System.nanoTime();
                    long between = finish - start;
                    speedNow = (1024d / between);
                    System.out.println(between + "  " + speedNow + " = " + (speedNow / between));
                    if (speedNow < speed) {
                        Thread.sleep(999, 999999 - (int) speedNow);
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
       if ((args.length != 3)
               || (args[0].isEmpty())
               || (args[1].isEmpty())) {
           String url = args[0];
           int speed = Integer.parseInt(args[1]);
           String file = "pom_tmp.xml";
           if (!args[2].isEmpty()) {
               file = args[2];
           }
           Thread wget = new Thread(new Wget(url, speed, file));
           wget.start();
           wget.join();
       } else {
           System.out.println("Неверные входные параметры программы");
       }
    }
}