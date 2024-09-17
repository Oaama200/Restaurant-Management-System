package org.example.menu;

interface MenuOperations {
    void addingItem(String itemName, double price);
    void removeItem(String itemName);
    void updateItemPrice(String itemName, double itemPrice);
}