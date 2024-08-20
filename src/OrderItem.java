import java.util.Objects;

public class OrderItem extends BaseMenuItem{
    private MenuItem menuItem;
    private int quantity;

    public OrderItem(MenuItem menuItem, int quantity) {
        super(menuItem.getItemName(), menuItem.getPrice() );
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
    public String getItemDetails(){
        return String.format("%-10d | %-15s | $%-10.2f",
                quantity,itemName, price);
    }

    @Override
    public String toString() {
        return getItemDetails();
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
