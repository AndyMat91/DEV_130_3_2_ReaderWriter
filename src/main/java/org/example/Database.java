package org.example;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Database {
    private static Database database;
    private static List<Reader> readers = new LinkedList<>();
    private static Writer writer = null;

    private Database() {
    }

    public static Database getInstance() {
        if (database == null) database = new Database();
        return database;
    }

    public synchronized void getRead(Reader reader) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Exception: " + e.getMessage());
        }
        System.out.println("ThreadId_" + Thread.currentThread().getId() + ": Читатель хочет подключиться к базе данных");
        while (writer != null) {
            try {
                System.out.println("ThreadId_" + Thread.currentThread().getId() + ": Читатель уснул в ожидании отключения писателя");
                wait();
                System.out.println("ThreadId_" + Thread.currentThread().getId() + ": Читатель проснулся и готов подключиться к базе данных");
            } catch (InterruptedException e) {
                System.out.println("Exception: " + e.getMessage());
            }
        }
        try {
            readers.add(reader);
            System.out.println("ThreadId_" + Thread.currentThread().getId() + ": Читатель подключился к базе данных, всего" +
                    " подключенных читателей: " + readers.size() + ", всего подключенных писателей: " + (writer == null? "0" : "1"));
        wait(new Random().nextInt(300));
        readers.remove(reader);
            System.out.println("ThreadId_" + Thread.currentThread().getId() + ": Читатель отключился от базы данных, всего" +
                    " подключенных читателей: " + readers.size() + ", всего подключенных писателей: " + (writer == null? "0" : "1"));
        } catch (InterruptedException e) {
            System.out.println("Exception: " + e.getMessage());
        }
        notify();
    }

    public synchronized void getWrite(Writer wr) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Exception: " + e.getMessage());
        }
        System.out.println("ThreadId_" + Thread.currentThread().getId() + ": Писатель хочет подключиться к базе данных");
        while (writer != null || readers.size() != 0) {
            try {
                System.out.println("ThreadId_" + Thread.currentThread().getId() + ": Писатель уснул в ожидании отключения " +
                        ((writer != null)? "писателя":"читателя."));
                wait();
                System.out.println("ThreadId_" + Thread.currentThread().getId() + ": Писатель проснулся и готов подключиться к базе данных");
            } catch (InterruptedException e) {
                System.out.println("Exception: " + e.getMessage());
            }
        }
        try {
            writer = wr;
            System.out.println("ThreadId_" + Thread.currentThread().getId() + ": Писатель подключился к базе данных, всего " +
                    "подключенных читателей: " + readers.size() + ", всего подключенных писателей: " + (writer == null? "0" : "1"));
            wait(new Random().nextInt(200));
            writer = null;
            System.out.println("ThreadId_" + Thread.currentThread().getId() + ": Писатель отключился от базы данных, всего " +
                    "подключенных читателей: " + readers.size() + ", всего подключенных писателей: " + (writer == null? "0" : "1"));
        } catch (InterruptedException e) {
            System.out.println("Exception: " + e.getMessage());
        }
        notify();
    }
}
