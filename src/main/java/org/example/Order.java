package org.example;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.List;

public class Order implements OrderOperations, FeeCalculable {
    private final List<OrderItem> orderItems;
    public final CustomLinkedList<StringBuilder> orderHistory;
    private final Menu menu;
    private static int orderIdCounter = 1;

    //private int currentOrderId;
    public Order(Menu menu) {
        this.orderItems = new ArrayList<>();
        this.orderHistory = new CustomLinkedList<>();
        this.menu = menu;
        System.out.println("Order instance created");
    }

    @Override
    public void placeOrder(String itemName, int quantity) {
        MenuItem menuItem = menu.findItemByName(itemName);
        if (menuItem != null) {
            OrderItem orderItem = new OrderItem(menuItem, quantity);
            orderItems.add(orderItem);
            System.out.printf("%d %s(s) added to the order.%n", quantity, itemName);
        }
    }

    public void printOrderHistory() {
        System.out.println("Total orders in history: " + orderHistory.size());
        if (orderHistory.isEmpty()) {
            System.out.println("Order history is empty.");
        } else {
            for (StringBuilder order : orderHistory) {
                System.out.println(order);
                System.out.println("=============================================");
            }
        }
    }

    public void getOrderHistoryItemById(int id) {
        StringBuilder order = orderHistory.getById(id);
        if (order != null) {
            System.out.println(order);
        } else {
            System.out.println("Order with ID " + id + " not found in the order history.");
        }
    }

    public void removeOrderHistoryItemById(int id) {
        boolean removed = orderHistory.removeById(id);
        if (removed) {
            System.out.println("Order with ID " + id + " has been removed from the order history.");
        } else {
            System.out.println("Order with ID " + id + " not found in the order history.");
        }
    }

    public void getOrderHistorySize() {
        System.out.println("Number of order(s): " + orderHistory.size());
    }

    @Override
    public double calculateTotal() {
        double total = 0;
        for (OrderItem orderItem : orderItems) {
            total += orderItem.getPrice() * orderItem.getQuantity();
        }
        return total;
    }

    @Override
    public double calculateTax() {
        double total = calculateTotal();
        return total * Constants.TAX_RATE;
    }

    @Override
    public double calculateDeliveryFee() {
        return calculateTotal() * Constants.DELIVERY_FEE;
    }

    @Override
    public double calculateServiceCharge() {
        return calculateTotal() * Constants.SERVICE_FEE;
    }

    @Override
    public void printOrder(int orderType) {
        double deliveryFee = 0;
        double serviceFee = 0;
        double tax = calculateTax();
        double total;
        String orderTypeString = getOrderTypeString(orderType);
        StringBuilder orderSummary = new StringBuilder();
        appendOrderHeader(orderSummary, orderTypeString);
        appendOrderItems(orderSummary);
        appendFees(orderSummary, orderType, deliveryFee, serviceFee);
        total = calculateTotal() + tax + deliveryFee + serviceFee;
        appendTotalSummary(orderSummary, tax, total);
        appendFooter(orderSummary);

        printToConsole(orderSummary);
        orderHistory.add(orderSummary, orderIdCounter);
        orderIdCounter++;

        printOrderHistory();
        writeToFile(orderSummary);
        orderItems.clear();
    }

    private String getOrderTypeString(int orderType) {
        switch (orderType) {
            case 3:
                return Constants.DELIVERY_NAME;
            case 2:
                return Constants.PICK_UP_NAME;
            default:
                return Constants.DINE_IN_NAME;
        }
    }

    private void appendOrderHeader(StringBuilder orderSummary, String orderTypeString) {
        orderSummary.append("Order Summary:\n")
                .append("Order Type: ").append(orderTypeString).append("\n")
                .append("Order ID: ").append(orderIdCounter).append("\n")
                .append("__________________________________\n")
                .append(String.format("%-10s | %-10s | %-10s\n", "Quantity", "Item", "Price"))
                .append("___________|____________|_________\n");
    }

    private void appendOrderItems(StringBuilder orderSummary) {
        for (OrderItem orderItem : orderItems) {
            orderSummary.append(orderItem).append("\n");
        }
    }

    private void appendFees(StringBuilder orderSummary, int orderType, double deliveryFee, double serviceFee) {
        if (orderType == 3) {
            deliveryFee = calculateDeliveryFee();
            orderSummary.append("Delivery Fee: $").append(String.format("%.2f", deliveryFee)).append("\n");
        } else if (orderType == 1) {
            serviceFee = calculateServiceCharge();
            orderSummary.append("Service Fee: $").append(String.format("%.2f", serviceFee)).append("\n");
        }
    }

    private void appendTotalSummary(StringBuilder orderSummary, double tax, double total) {
        orderSummary.append("Tax: $").append(String.format("%.2f", tax)).append("\n")
                .append("Total: $").append(String.format("%.2f", total)).append("\n");
    }

    private void appendFooter(StringBuilder orderSummary) {
        orderSummary.append(Constants.printCurrentDateTime()).append("\n")
                .append("Thank You, Come Again\n");
    }

    private void printToConsole(StringBuilder orderSummary) {
        System.out.println(orderSummary.toString());
    }

    private void writeToFile(StringBuilder orderSummary) {
        try (FileWriter fileWriter = new FileWriter("order_summary.txt", true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            printWriter.println(orderSummary.toString());
            printWriter.println("=============================================");
        } catch (IOException e) {
            System.out.println("An error occurred while writing the order summary to the file.");
            e.printStackTrace();
        }
    }
}
