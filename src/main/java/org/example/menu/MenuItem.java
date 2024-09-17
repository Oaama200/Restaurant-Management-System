package org.example.menu;

import java.text.DecimalFormat;
import java.util.Objects;

public class MenuItem extends BaseMenuItem {
    public MenuItem(String item, double price) {
        super(item, price);
    }
    public void setPrice(double price) {
        this.price = price;
    }
    @Override
    public String getItemDetails() {
        DecimalFormat currencyFormat = new DecimalFormat("$#,##0.00");
        return String.format("%-20s: %5s", itemName, currencyFormat.format(price));
    }

    @Override
    public String toString() {
        return getItemDetails();
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
