public abstract class BaseMenuItem {
    protected final String itemName;
    protected double price;

    public BaseMenuItem(String itemName, double price) {
        this.itemName = itemName;
        this.price = price;
    }
    public String getItemName(){
        return itemName;
    }
    public double getPrice() {
        return price;
    }
    public abstract String getItemDetails();
}
