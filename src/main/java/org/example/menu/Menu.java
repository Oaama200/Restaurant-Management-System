package org.example.menu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Main;
import org.example.utilities.SetupDefaults;

import java.util.HashSet;
import java.util.Set;
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
        uniqueItems.stream().forEach(item -> System.out.println(item));
    }

    @Override
    public void addingItem(String itemName, double price) {
        MenuItem currentItem = new MenuItem(itemName, price);
        addMenuItem(currentItem);
        logger.info(itemName + " has been added to the menu.");
    }

    @Override
    public void removeItem(String itemName) {
        uniqueItems.removeIf(item -> item.getItemName().equalsIgnoreCase(itemName));

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
        return uniqueItems.stream()
                .filter(item -> item.getItemName().equalsIgnoreCase(itemName))
                .findFirst()
                .orElse(null);
    }

    public Boolean checkIfItemExists(String itemName) {
        return findItemByName(itemName) != null;
    }
}
