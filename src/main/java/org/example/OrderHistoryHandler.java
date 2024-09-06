package org.example;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import org.apache.logging.log4j.Logger;
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
        List<String> staffManagementMenuOptions = Arrays.asList(
                "STAFF MANAGEMENT MENU",
                "1: Display Order History",
                "2: Print an Order by ID",
                "3: Remove Order History Item by ID",
                "4: Display the number of orders for this session",
                "5: Back to Employee Menu",
                "0: Quit"
        );
        boolean continueMenu = true;
        while (continueMenu) {
            try {
                userSelection = MenuHandler.displayMenuAndGetSelection(staffManagementMenuOptions);
                continueMenu = handleOrderHistoryMenuSelection(userSelection);
            } catch (InvalidSelectionException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    private static boolean handleOrderHistoryMenuSelection(String selection) throws InvalidSelectionException {
        switch (selection) {
            case "1":
                logger.info("Printing order history");
                order.printOrderHistory();
                break;

            case "2":
                logger.info("Printing order by Id");
                order.getOrderHistoryItemById(getOrderIDFromUser());
                break;

            case "3":
                logger.info("Removing order By Id");
                order.removeOrderHistoryItemById(getOrderIDFromUser());
                break;

            case "4":
                logger.info("Displaying the number of orders");
                order.getOrderHistorySize();
                break;
            case "5":
                logger.info("Returning to the employee menu");
                return false;

            case Constants.QUIT_MENU_OPTION:
                logger.info("Terminating the application");
                System.exit(0);
                break;

            default:
                logger.warn("Invalid selection: " + selection);
                throw new InvalidSelectionException();
        }
        return true;
    }

    public static int getOrderIDFromUser() {
        System.out.print("Enter Order ID: ");
        return MenuHandler.readIntInput(input);
    }

}