package org.example;

public class Connection {
    private final int id;

    public Connection(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void open() {
        System.out.println("Connection " + id + " opened");
    }

    public void close() {
        System.out.println("Connection " + id + " closed");
    }
}