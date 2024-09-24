package org.example.menu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Main;
import org.example.utilities.SetupDefaults;
import org.example.ConnectionPool;
import org.example.Connection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class Menu implements MenuOperations, SetupDefaults {
    public static final Logger logger = LogManager.getLogger(Main.class);
    private final Set<MenuItem> uniqueItems;

    public Menu() {
        uniqueItems = new HashSet<>();
        initializeDefaultItems();
        ConnectionPool.initializeConnectionPool();
        initializeAndPerformSimpleOperations();
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
        Connection connection = ConnectionPool.acquireConnection();
        if (connection != null) {
            try {
                connection.open();
                MenuItem currentItem = new MenuItem(itemName, price);
                addMenuItem(currentItem);
                logger.info(itemName + " has been added to the menu using connection " + connection.getId());
            } finally {
                connection.close();
                ConnectionPool.releaseConnection(connection);
            }
        } else {
            logger.warn("Unable to add item. No connection available.");
        }
    }


    @Override
    public void removeItem(String itemName) {
        Connection connection = ConnectionPool.acquireConnection();
        if (connection != null) {
            try {
                connection.open();
                boolean removed = uniqueItems.removeIf(item -> item.getItemName().equalsIgnoreCase(itemName));
                if (removed) {
                    logger.info(itemName + " has been removed from the menu using connection " + connection.getId());
                } else {
                    logger.warn("Item " + itemName + " not found in the menu. No removal performed.");
                }
            } finally {
                connection.close();
                ConnectionPool.releaseConnection(connection);
            }
        } else {
            logger.warn("Unable to remove item. No connection available.");
        }
    }

    @Override
    public void updateItemPrice(String itemName, double newPrice) {
        Connection connection = ConnectionPool.acquireConnection();
        if (connection != null) {
            try {
                connection.open();
                BiConsumer<MenuItem, Double> updatePriceLog = (item, price) ->
                        logger.info(itemName + " price updated from " + item.getPrice() + " to " + price +
                                " using connection " + connection.getId());

                MenuItem itemToUpdate = findItemByName(itemName);
                if (itemToUpdate != null) {
                    updatePriceLog.accept(itemToUpdate, newPrice);
                    itemToUpdate.setPrice(newPrice);
                } else {
                    logger.warn("Attempt to update price of non-existent item: " + itemName);
                }
            } finally {
                connection.close();
                ConnectionPool.releaseConnection(connection);
            }
        } else {
            logger.warn("Unable to update item price. No connection available.");
        }
    }

    public MenuItem findItemByName(String itemName) {
        Connection connection = ConnectionPool.acquireConnection();
        if (connection != null) {
            try {
                connection.open();
                MenuItem foundItem = uniqueItems.stream()
                        .filter(item -> item.getItemName().equalsIgnoreCase(itemName))
                        .findFirst()
                        .orElse(null);
                if (foundItem != null) {
                    logger.info("Item " + itemName + " found using connection " + connection.getId());
                } else {
                    logger.info("Item " + itemName + " not found using connection " + connection.getId());
                }
                return foundItem;
            } finally {
                connection.close();
                ConnectionPool.releaseConnection(connection);
            }
        } else {
            logger.warn("Unable to find item. No connection available.");
            return null;
        }
    }

    public Boolean checkIfItemExists(String itemName) {
        Connection connection = ConnectionPool.acquireConnection();
        if (connection != null) {
            try {
                connection.open();
                boolean exists = uniqueItems.stream()
                        .anyMatch(item -> item.getItemName().equalsIgnoreCase(itemName));
                logger.info("Checked if item " + itemName + " exists using connection " + connection.getId() +
                        ". Result: " + exists);
                return exists;
            } finally {
                connection.close();
                ConnectionPool.releaseConnection(connection);
            }
        } else {
            logger.warn("Unable to check if item exists. No connection available.");
            return false;
        }
    }

    public void initializeAndPerformSimpleOperations() {
        ConnectionPool.initializeConnectionPoolWithFutures();

        CompletableFuture<Void> addItem = CompletableFuture.runAsync(() -> addingItem("Salad", 7.99));
        CompletableFuture<Void> removeItem = CompletableFuture.runAsync(() -> removeItem("Burger"));
        CompletableFuture<Void> updatePrice = CompletableFuture.runAsync(() -> updateItemPrice("Pizza", 16.99));

        CompletableFuture.allOf(addItem, removeItem, updatePrice).join();

        logger.info("Simple operations completed.");
    }

}