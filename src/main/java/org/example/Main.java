package org.example;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        main.startDatabase();
    }

    public void startDatabase() {
        for (int i = 0; i < 5; i++) {
            Thread read = new Thread(() -> {
                try {
                    Thread.sleep(new Random().nextInt(100));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                    Reader reader = new Reader();
                    reader.connection();
            });
            read.setDaemon(true);
            read.start();
        }

        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep(new Random().nextInt(200));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Writer writer = new Writer();
                    writer.connection();
            }).start();
        }
    }
}