package org.example.order;

import org.example.menu.Menu;
import org.example.menu.MenuItem;
import org.example.utilities.Constants;
import org.example.utilities.CustomLinkedList;
import org.example.utilities.FeeCalculable;
import org.example.utilities.FeeType;

import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.List;

@FunctionalInterface
interface DiscountCalculator<T> {
    T calculate(T total, T discountRate);
}

@FunctionalInterface
interface DiscountFormatter<T> {
    String format(T discount);
}
@FunctionalInterface
interface TotalCostCalculator<T> {
    T calculate(T subTotal, T tax, T deliveryFee, T serviceCharge);
}
public class Order implements OrderOperations, FeeCalculable {
    private final List<OrderItem> orderItems;
    public final CustomLinkedList<StringBuilder> orderHistory;
    private final Menu menu;
    private static int orderIdCounter = 1;

    public Order(Menu menu) {
        this.orderItems = new ArrayList<>();
        this.orderHistory = new CustomLinkedList<>();
        this.menu = menu;
    }
    DiscountCalculator<Double> applyDiscount = (total, discountRate) -> total * discountRate;

    DiscountFormatter<Double> formatDiscount = discount -> String.format("-$%.2f", discount);

    TotalCostCalculator<Double> calculateTotalCost = (subTotal, tax, deliveryFee, serviceCharge) ->
            subTotal + tax + deliveryFee + serviceCharge;

    public double processOrderWithDiscount() {
        double total = calculateTotal();

        return orderItems.stream()
                .filter(orderItem -> total >= Constants.DISCOUNT_THRESHOLD)
                .mapToDouble(orderItem -> applyDiscount.calculate(orderItem.getPrice() * orderItem.getQuantity(),
                        FeeType.DISCOUNT_RATE.getRate()))
                .sum();
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

    private String getOrderTypeString(int orderType) {
        return switch (orderType) {
            case 3 -> Constants.DELIVERY_NAME;
            case 2 -> Constants.PICK_UP_NAME;
            default -> Constants.DINE_IN_NAME;
        };
    }

    @Override
    public double calculateTotal() {
        return orderItems.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }
    public double calculateSubTotal() {
        return calculateTotal() - processOrderWithDiscount();
    }

    @Override
    public double calculateTax() {
        return calculateSubTotal() * FeeType.TAX_RATE.getRate();
    }

    @Override
    public double calculateDeliveryFee() {
        return calculateSubTotal() * FeeType.DELIVERY_FEE.getRate();
    }

    @Override
    public double calculateServiceCharge() {
        return calculateSubTotal() * FeeType.SERVICE_FEE.getRate();
    }

    @Override
    public void printOrder(int orderType) {
        double tax = calculateTax();
        double total;
        double discountedAmount = processOrderWithDiscount();
        double subTotal = calculateSubTotal();
        String orderTypeString = getOrderTypeString(orderType);
        double deliveryFee = (orderTypeString.equals(Constants.DELIVERY_NAME)) ? calculateDeliveryFee() : 0;
        double serviceCharge = (orderTypeString.equals(Constants.DINE_IN_NAME)) ? calculateServiceCharge() : 0;
        total = calculateTotalCost.calculate(subTotal, tax, deliveryFee, serviceCharge);
        StringBuilder orderSummary = new StringBuilder();
        appendOrderHeader(orderSummary, orderTypeString);
        appendOrderItems(orderSummary);
        appendSubTotal(orderSummary, discountedAmount, subTotal);
        appendFees(orderSummary, orderType);
        appendTotalSummary(orderSummary, tax, total);
        appendFooter(orderSummary);
        printToConsole(orderSummary);
        orderHistory.add(orderSummary, orderIdCounter);
        orderIdCounter++;

        writeToFile(orderSummary);
        orderItems.clear();
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

    private void appendFees(StringBuilder orderSummary, int orderType) {
        if (orderType == 3) {
             double deliveryFee = calculateDeliveryFee();
            orderSummary.append("Delivery Fee: $").append(String.format("%.2f", deliveryFee)).append("\n");
        } else if (orderType == 1) {
            double serviceFee = calculateServiceCharge();
            orderSummary.append("Service Fee: $").append(String.format("%.2f", serviceFee)).append("\n");
        }
    }
    private void appendSubTotal(StringBuilder orderSummary, double discount, double SubTotal) {
        orderSummary.append("Discount: ").append(formatDiscount.format(discount)).append("\n")
                .append("SubTotal: $").append(String.format("%.2f", SubTotal)).append("\n");
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
