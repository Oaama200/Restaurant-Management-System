import java.util.ArrayList;

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
            System.out.println(quantity + " " + itemName + "(s) added to the order.");
        } else {
            System.out.println("Item not found in the menu.");
        }
    }

    @Override
    public double calculateTotal() {
        double total = 0;
        for (OrderItem orderItem : orderItems) {
            total += orderItem.getMenuItem().getPrice() * orderItem.getQuantity();
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
    public void printOrder(int orderType) {

        double deliveryFee = 0;
        double serviceFee = 0;
        double tax = calculateTax();
        double total;
        String orderTypeString;
        switch (orderType) {
            case 3:
                orderTypeString = Constants.DELIVERY_NAME;
                break;
            case 2:
                orderTypeString = Constants.PICK_UP_NAME;
                break;
            default:
                orderTypeString = Constants.DINE_IN_NAME;
                break;
        }
        System.out.println("Order Summary:");
        System.out.printf("Order Type: " + orderTypeString + "\n");
        System.out.println("___________________________________________");
        System.out.println(String.format("%-10s | %-15s | %-10s", "Quantity", "Item", "Price"));
        System.out.println("___________|_________________|_____________");
        for (OrderItem orderItem : orderItems) {
            System.out.println(orderItem);
        }

        // Print delivery fee
        if (orderType == 3) {
            deliveryFee = calculateDeliveryFee();
            System.out.println("Delivery Fee: $" + String.format("%.2f", deliveryFee));
        } else if (orderType == 1) {
            serviceFee = calculateServiceCharge();
            System.out.println("Service Fee: $" + String.format("%.2f", serviceFee));

        }
        total = calculateTotal() + tax + deliveryFee + serviceFee;

        System.out.println("Tax: $" + String.format("%.2f", tax));
        System.out.println("Total: $" + String.format("%.2f", total));

        Constants.printCurrentDateTime();

        System.out.println("Thank You, Come Again");
    }


}
