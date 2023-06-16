package org.example;


public class Writer {
    public void connection() {
        Database.getInstance().getWrite(this);
    }
}
