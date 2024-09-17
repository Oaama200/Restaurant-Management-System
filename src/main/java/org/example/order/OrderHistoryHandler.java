package org.example.order;

import java.util.List;
import java.util.Scanner;
import org.apache.logging.log4j.Logger;
import org.example.exceptions.InvalidSelectionException;
import org.example.menu.MenuHandler;

public class OrderHistoryHandler {
    static Scanner input;
    static  Logger logger ;
    static Order order;
    public static String userSelection = "";

    public OrderHistoryHandler(Scanner input, Logger logger, Order order) {
        OrderHistoryHandler.logger = logger;
        OrderHistoryHandler.input = input;
        OrderHistoryHandler.order = order;
    }

    public static void displayOrderHistoryMenu() {
        List<String> orderHistoryMenuOptions = OrderHistoryOption.getMenuOptions();
        boolean continueMenu = true;
        while (continueMenu) {
            try {
                userSelection = MenuHandler.displayMenuAndGetSelection(orderHistoryMenuOptions);
                continueMenu = handleOrderHistoryMenuSelection(userSelection);
            } catch (InvalidSelectionException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    private static boolean handleOrderHistoryMenuSelection(String selection) throws InvalidSelectionException {
        try {
            OrderHistoryOption option = OrderHistoryOption.getByCode(selection);
            switch (option) {
                case CURRENT_ORDER -> {
                    logger.info("Printing order history");
                    order.printOrderHistory();
                }
                case PRINT_ORDER -> {
                    logger.info("Printing order by Id");
                    order.getOrderHistoryItemById(getOrderIDFromUser());
                }
                case REMOVE_ORDER -> {
                    logger.info("Removing order By Id");
                    order.removeOrderHistoryItemById(getOrderIDFromUser());
                }
                case NUMBER_OF_ORDERS -> {
                    logger.info("Displaying the number of orders");
                    order.getOrderHistorySize();
                }
                case RETURN -> {
                    logger.info("Returning to the employee menu");
                    return false;
                }
                case QUIT -> {
                    logger.info("Terminating the application");
                    System.exit(0);
                }
                default -> {
                    logger.warn("Invalid selection: " + selection);
                    throw new InvalidSelectionException();
                }
            }
            return true;
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid selection: " + selection);
            throw new InvalidSelectionException();
        }
    }

    public static int getOrderIDFromUser() {
        System.out.print("Enter Order ID: ");
        return MenuHandler.readIntInput(input);
    }

}