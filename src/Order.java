import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Order implements OrderOperations, FeeCalculable {
    private ArrayList<OrderItem> orderItems;
    private Menu menu;

    public Order(Menu menu) {
        this.orderItems = new ArrayList<>();
        this.menu = menu;
    }

    @Override
    public void placeOrder(String itemName, int quantity) {
            MenuItem menuItem = menu.findItemByName(itemName);
            if (menuItem != null) {
                OrderItem orderItem = new OrderItem(menuItem, quantity);
                orderItems.add(orderItem);
                System.out.println(String.format("%d %s(s) added to the order.", quantity, itemName));

            }
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
        double tax = total * Constants.TAX_RATE;
        return tax;
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
    public void printOrder(int orderType){
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
        writeToFile(orderSummary);
    }

    private String getOrderTypeString(int orderType) {
        switch (orderType){
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
                .append("___________________________________________\n")
                .append(String.format("%-10s | %-15s | %-10s\n", "Quantity", "Item", "Price"))
                .append("___________|_________________|_____________\n");
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
