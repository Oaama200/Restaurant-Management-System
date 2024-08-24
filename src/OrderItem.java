import java.text.DecimalFormat;
import java.util.Objects;

public class OrderItem extends BaseMenuItem{
    private final int quantity;

    public OrderItem(MenuItem menuItem, int quantity) {
        super(menuItem.getItemName(), menuItem.getPrice() );
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
    @Override
    public String getItemDetails(){
        DecimalFormat currencyFormat = new DecimalFormat("$#,##0.00");
        return String.format("%-10d | %-15s | %-10s",
                quantity,itemName, currencyFormat.format(price));
    }

    @Override
    public String toString() {
        return getItemDetails();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItem orderItem)) return false;
        return getQuantity() == orderItem.getQuantity() && itemName.equals(orderItem.itemName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemName, getQuantity());
    }
}
