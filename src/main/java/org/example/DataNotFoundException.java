package org.example;

public class DataNotFoundException extends Exception{
    public DataNotFoundException(String message) {
        super(message);
    }
    public DataNotFoundException() {
        super("Name Not found Exception");
    }
}
