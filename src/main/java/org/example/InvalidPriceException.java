package org.example;

public class InvalidPriceException extends Exception {
    public InvalidPriceException() {
        super("Invalid price Exception. Price must be a positive number.");
    }
}
