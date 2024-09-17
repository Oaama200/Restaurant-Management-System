package org.example.exceptions;

public class DuplicateNameException extends Exception {
    public DuplicateNameException(String message) {
        super(message);
    }
    public DuplicateNameException() {
        super("Item Already Exists Exception");
    }
}
