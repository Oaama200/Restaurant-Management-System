package org.example.exceptions;

public class InvalidSalaryException extends Exception {
    public InvalidSalaryException(){
        super("Invalid Salary Exception. Salary must be a positive number.");
    }

}
