import java.util.ArrayList;
public class Menu {
    private ArrayList<MenuItem> items;
    public Menu() {
        items = new ArrayList<>();
        addDefaultItems();
    }

    private void addDefaultItems() {
        items.add(new MenuItem("Burger", 9.99));
        items.add(new MenuItem("Pizza", 15.99));
        items.add(new MenuItem("Fries", 5.99));
        items.add(new MenuItem("Soda", 0.99));
    }

    public void currentMenu() {
        for (MenuItem item : items) {
            System.out.println(item);
        }
    }

    public void addItem(String itemName, double price){
        MenuItem currentItem = new MenuItem(itemName, price);
        items.add(currentItem);
        System.out.println(itemName + " has been added");
    }

    public void deleteItem(String itemName) {
        MenuItem itemToDelete = findItemByName(itemName);
        if (itemToDelete != null) {
            items.remove(itemToDelete);
            System.out.println(itemName + " has been deleted.");
        } else {
            System.out.println("Item not found.");
        }
    }

    public void editItemPrice(String itemName, double itemPrice) {
        MenuItem itemToEdit = findItemByName(itemName);
        if (itemToEdit != null) {
            itemToEdit.setPrice(itemPrice);
            System.out.println(itemName + " has been updated.");
        } else {
            System.out.println("Item not found.");
        }
    }

    public MenuItem findItemByName(String itemName) {
        for (MenuItem item : items) {
            if (item.getItem().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    public Boolean checkIfItemExists(String itemName) {
        for (MenuItem item : items) {
            if (item.getItem().equalsIgnoreCase(itemName)) {
                return true;
            }
        }
        return false;
    }

}
