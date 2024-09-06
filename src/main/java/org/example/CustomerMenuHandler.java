package org.example;

import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CustomerMenuHandler {
    private static Scanner input;
    private static Logger logger;
    private static Order currentOrder;
    //private static CustomLinkedList<Order> orderHistory;
    private static Menu menu;
    public static String userSelection = "";

    public CustomerMenuHandler(Scanner input, Logger logger, Menu menu, Order order) {
        CustomerMenuHandler.logger = logger;
        CustomerMenuHandler.input = input;
        CustomerMenuHandler.currentOrder = order;
        CustomerMenuHandler.menu = menu;
        //CustomerMenuHandler.orderHistory = new CustomLinkedList<>();
    }

    public static void displayCustomerMenu() {
        List<String> customerMenuOptions = Arrays.asList(
                "CUSTOMER MENU",
                "1: Display Restaurant Menu",
                "2: Place an Order",
                "3: Back to Main Menu",
                "0: Quit"
        );
        boolean continueMenu = true;
        while (continueMenu) {
            try {
                userSelection = MenuHandler.displayMenuAndGetSelection(customerMenuOptions);
                continueMenu = handleCustomerMenuSelection(userSelection);
            } catch (InvalidSelectionException e) {
                System.out.println(e.getMessage());
            }

        }
    }

    private static boolean handleCustomerMenuSelection(String selection) throws InvalidSelectionException {
        switch (selection) {
            case "1":
                logger.info("Displaying Restaurant Menu");
                menu.displayDefaultMenu();
                System.out.println();
                break;

            case "2":
                logger.info("Placing an Order");
                placeOrder();
                return true;

            case "3":
                logger.info("Returning to Main Menu");
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

    private static void placeOrder() throws InvalidSelectionException {
        List<String> orderTypeOptions = Arrays.asList(
                "Please select an option:",
                "For dining in select: 1",
                "For pick-up select: 2",
                "For delivery select: 3"
        );
        String orderTypeSelection = MenuHandler.displayMenuAndGetSelection(orderTypeOptions);
        int orderType = handleOrderTypeSelection(orderTypeSelection);

        boolean orderingComplete = false;
        while (!orderingComplete) {
            logger.info("Displaying current menu");
            menu.displayDefaultMenu();
            try {
                logger.info("Adding item to order");
                addItemToOrder();
                orderingComplete = !continueOrdering();
            } catch (DataNotFoundException e) {
                logger.error("Error while adding item to order: " + e.getMessage());
                System.out.println(e.getMessage());
            }
        }
        currentOrder.printOrder(orderType);
    }

    private static int handleOrderTypeSelection(String selection) throws InvalidSelectionException {
        int orderType;
        try {
            orderType = Integer.parseInt(selection);
        } catch (NumberFormatException e) {
            logger.warn("Invalid order type input: " + selection);
            throw new InvalidSelectionException("Invalid order type selection");
        }

        if (orderType == Constants.DINE_IN || orderType == Constants.PICK_UP || orderType == Constants.DELIVERY) {
            logger.info("User selected a valid order type: " + orderType);
            return orderType;
        } else {
            logger.warn("User selected an invalid order type: " + orderType);
            throw new InvalidSelectionException("Invalid order type selection");
        }
    }

    private static void addItemToOrder() throws DataNotFoundException {
        System.out.println("Enter the name of the item to add to your order:");
        String itemName = input.nextLine();
        logger.info("Name entered: " + itemName);

        if (menu.checkIfItemExists(itemName)) {
            logger.info("Item found in the menu: " + itemName);
            System.out.println("Enter the quantity:");
            int quantity = MenuHandler.readIntInput(input);
            logger.info("quantity entered: " + quantity);
            currentOrder.placeOrder(itemName, quantity);
        } else {
            logger.warn("Item not found in the menu: " + itemName);
            System.out.println("Item not found in the menu.");
            throw new DataNotFoundException();
        }
    }

    private static boolean continueOrdering() {
        List<String> continueOrderingMenu = Arrays.asList(
                "Press 1 to continue adding to your order",
                "Press 2 to check out"
        );
        logger.info("Prompting user to continue ordering or check out.");
        MenuHandler.printMenu(continueOrderingMenu);
        do {
            userSelection = MenuHandler.getUserSelection();
            if (userSelection.equals("2")) {
                logger.info("User chose to check out.");

                return false;
            }
        } while (!userSelection.equals("1"));
        logger.info("User chose to continue ordering.");
        return true;
    }
}
