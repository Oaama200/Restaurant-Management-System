package org.example.menu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Main;
import org.example.utilities.SetupDefaults;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Consumer;
import java.util.function.BiConsumer;

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
        uniqueItems.add(item);
    }
    public void displayDefaultMenu() {
        Consumer<MenuItem> printItem = item -> System.out.println(item);
        uniqueItems.forEach(printItem);
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
        BiConsumer<MenuItem, Double> updatePriceLog = (item, price) -> logger.info(itemName + " price updated from " + item.getPrice() + " to " + price);

        MenuItem itemToUpdate = findItemByName(itemName);
        if (itemToUpdate != null) {
            updatePriceLog.accept(itemToUpdate, newPrice);
            itemToUpdate.setPrice(newPrice);
        } else {
            logger.warn("Attempt to update price of non-existent item: " + itemName);
        }
    }
    public MenuItem findItemByName(String itemName) {
        Predicate<MenuItem> isMatchingItem = item -> item.getItemName().equalsIgnoreCase(itemName);
        for (MenuItem item : uniqueItems) {
            if (isMatchingItem.test(item)) {
                return item;
            }
        }
        return null;
    }

    public Boolean checkIfItemExists(String itemName) {
            return findItemByName(itemName) != null;
    }
}
