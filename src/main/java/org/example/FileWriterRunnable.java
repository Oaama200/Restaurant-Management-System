package org.example;

import org.example.order.Order;

public class FileWriterRunnable implements Runnable {
    private final StringBuilder orderSummary;

    public FileWriterRunnable(StringBuilder orderSummary) {
        this.orderSummary = orderSummary;
    }

    @Override
    public void run() {
        Order.writeToFile(orderSummary);
    }
}