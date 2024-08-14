import java.util.ArrayList;

public class Order {
    private ArrayList<OrderItem> orderItems;
    private Menu menu;

    public Order(Menu menu) {
        this.orderItems = new ArrayList<>();
        this.menu = menu;
    }

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
    public double calculateDeliveryFee(int orderType) {
        if (orderType == 3) { // Delivery order
            double total = calculateTotal();
            return total * 0.10; // 10% delivery fee
        } else if (orderType == 2) {
            return 1; // pick-up order

        }
        return 0; // dine-in order
    }

    public double calculateTotal() {
        double total = 0;
        for (OrderItem orderItem : orderItems) {
            total += orderItem.getMenuItem().getPrice() * orderItem.getQuantity();
        }
        return total;
    }
    private double calculateTax() {
        double total =calculateTotal();
        double tax =  total*0.06;
        return tax;
    }

    public void printOrder(int orderType) {
        System.out.println("Order Summary:");

        double tax = calculateTax();
        double deliveryFee = calculateDeliveryFee(orderType);
        double total = calculateTotal() + tax + deliveryFee;
        String orderTypeString;
        switch (orderType) {
            case 3:
                orderTypeString = "Delivery";
                break;
            case 2:
                orderTypeString = "Pick-up";
                break;
            default:
                orderTypeString = "Dine-in";
                break;
        }
        System.out.println("Order Type: " + orderTypeString);
        for (OrderItem orderItem : orderItems) {
            System.out.println(orderItem);
        }

        // Print delivery fee
        if (orderType == 3) {
            System.out.println("Delivery Fee: $" + String.format("%.2f", deliveryFee));
        }

        System.out.println("Tax: $" + String.format("%.2f", tax));
        System.out.println("Total: $" + String.format("%.2f", total));

        System.out.println("Thank You, Come Again");
    }


}
