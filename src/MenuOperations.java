interface MenuOperations {
    void addingItem(String itemName, double price);
    void deletingItem(String itemName);
    void editingItemPrice(String itemName, double itemPrice);
}