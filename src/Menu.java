import java.util.ArrayList;

public class Menu implements MenuOperations, SetupDefaults {
    private ArrayList<MenuItem> items;

    public Menu() {
        items = new ArrayList<>();
        initializeDefaultItems();
    }

    @Override
    public void initializeDefaultItems() {
        items.add(new MenuItem("Burger", 9.99));
        items.add(new MenuItem("Pizza", 15.99));
        items.add(new MenuItem("Fries", 5.99));
        items.add(new MenuItem("Soda", 100));
    }

    public void currentMenu() {
        for (MenuItem item : items) {
            System.out.println(item);
        }
    }
    @Override
    public void addingItem(String itemName, double price) {
        MenuItem currentItem = new MenuItem(itemName, price);
        items.add(currentItem);
        System.out.println(itemName + " has been added");
    }
    @Override
    public void deletingItem(String itemName) {
        try{
        MenuItem itemToDelete = findItemByName(itemName);
        if (itemToDelete != null) {
            items.remove(itemToDelete);
            System.out.println(itemName + " has been deleted.");
        }else{

            throw new DataNotFoundException();
        }
        }catch (DataNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void editingItemPrice(String itemName, double itemPrice) {
            MenuItem itemToEdit = findItemByName(itemName);
            if (itemToEdit != null) {
                itemToEdit.setPrice(itemPrice);
                System.out.println(itemName + " has been updated.");
            }
    }

    public MenuItem findItemByName(String itemName) {
        for (MenuItem item : items) {
            if (item.getItemName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    public Boolean checkIfItemExists(String itemName) {
            return findItemByName(itemName) != null;
    }
}
