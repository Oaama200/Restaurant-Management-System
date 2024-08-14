import java.util.Objects;

public class MenuItem {

    private String itemName;
    private double price;

    public MenuItem(String item, double price) {
        this.itemName = item;
        this.price = price;
    }

    public String getItem() {
        return itemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return itemName + ": $" + price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuItem item)) return false;
        return Double.compare(item.getPrice(), getPrice()) == 0 && itemName.equals(item.itemName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemName, getPrice());
    }
}
