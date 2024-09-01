import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Set;

public class Menu implements MenuOperations, SetupDefaults {
    public static final Logger logger = LogManager.getLogger(Main.class);
    private final Set<MenuItem> uniqueItems;

    public Menu() {
        uniqueItems = new HashSet<>();
        initializeDefaultItems();
    }

    @Override
    public void initializeDefaultItems() {
        addMenuItem(new MenuItem("Burger", 9.99));
        addMenuItem(new MenuItem("Pizza", 15.99));
        addMenuItem(new MenuItem("Fries", 5.99));
        addMenuItem(new MenuItem("Soda", 100));
    }
    private void addMenuItem(MenuItem item) {
        uniqueItems.add(item); // Add to the set for uniqueness
    }

    public void displayDefaultMenu() {
        for (MenuItem item : uniqueItems) {
            System.out.println(item);
        }
    }
    @Override
    public void addingItem(String itemName, double price) {
        MenuItem currentItem = new MenuItem(itemName, price);
        addMenuItem(currentItem);
        logger.info(itemName + " has been added to the menu.");
    }
    @Override
    public void removeItem(String itemName) {
        MenuItem itemToDelete = findItemByName(itemName.toLowerCase());
        if (itemToDelete != null) {
            uniqueItems.remove(itemToDelete);
            logger.info(itemName + " has been removed from the menu.");
        } else {
            logger.warn("Attempt to remove non-existent item: " + itemName);
        }
    }

    @Override
    public void updateItemPrice(String itemName, double newPrice) {
            MenuItem itemToUpdate = findItemByName(itemName);
            if (itemToUpdate != null) {
                itemToUpdate.setPrice(newPrice);
                logger.info(itemName + " price has been updated to " + newPrice);

            }
        logger.warn("Attempt to update price of non-existent item: " + itemName);
    }

    public MenuItem findItemByName(String itemName) {
        for (MenuItem item : uniqueItems) {
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
