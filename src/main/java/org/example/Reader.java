package org.example;


public class Reader {
    public void connection() {
        Database.getInstance().getRead(this);
    }
}
