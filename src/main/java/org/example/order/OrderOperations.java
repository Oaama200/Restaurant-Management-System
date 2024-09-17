package org.example.order;

interface OrderOperations {
    void placeOrder(String itemName, int quantity);
    double calculateTotal();
    void  printOrder(int orderType);
}