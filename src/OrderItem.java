import java.util.Objects;

public class OrderItem {
    private MenuItem menuItem;
    private int quantity;

    public OrderItem(MenuItem menuItem, int quantity) {
        this.menuItem = menuItem;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
    public MenuItem getMenuItem() {
        return menuItem;
    }

    @Override
    public String toString() {

        String itemName = menuItem.getItem();
        double itemPrice = menuItem.getPrice();
        double totalItemCost = itemPrice * quantity;

        return String.format("Item: %-15s Quantity: %-10d Price: $%-10.2f Total: $%-10.2f",
                itemName, quantity, itemPrice, totalItemCost);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItem orderItem)) return false;
        return getQuantity() == orderItem.getQuantity() && getMenuItem().equals(orderItem.getMenuItem());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMenuItem(), getQuantity());
    }
}
