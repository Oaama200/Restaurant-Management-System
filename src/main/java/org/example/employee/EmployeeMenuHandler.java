package org.example.employee;

import org.apache.logging.log4j.Logger;
import org.example.menu.Menu;
import org.example.menu.MenuHandler;
import org.example.order.OrderHistoryHandler;
import org.example.staff.StaffMenuHandler;
import org.example.exceptions.DataNotFoundException;
import org.example.exceptions.DuplicateNameException;
import org.example.exceptions.InvalidPriceException;
import org.example.exceptions.InvalidSelectionException;

import java.util.List;
import java.util.Scanner;

public class EmployeeMenuHandler {
    private static Scanner input;
    private static Logger logger;
    private static Menu menu;
    public static String userSelection = "";

    public EmployeeMenuHandler(Scanner input, Logger logger, Menu menu) {
        EmployeeMenuHandler.logger = logger;
        EmployeeMenuHandler.input = input;
        EmployeeMenuHandler.menu = menu;
    }

    public static void displayEmployeeMenu() {
        List<String> employeeMenuOptions = EmployeeMenuOption.getMenuOptions();
        boolean continueMenu = true;
        while (continueMenu) {
            try {
                userSelection = MenuHandler.displayMenuAndGetSelection(employeeMenuOptions);
                continueMenu = handleEmployeeMenuSelection(userSelection);
            } catch (InvalidSelectionException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static boolean handleEmployeeMenuSelection(String selection) throws InvalidSelectionException {
        try {
            EmployeeMenuOption option = EmployeeMenuOption.getByCode(selection);
            switch (option) {
                case DISPLAY_MENU -> {
                    logger.info("Displaying Restaurant Menu");
                    menu.displayDefaultMenu();
                }
                case ADD_ITEM -> {
                    logger.info("Adding a menu item");
                    addingItem();
                }
                case DELETE_ITEM -> {
                    logger.info("Deleting a menu item");
                    deletingItem();
                }
                case EDIT_PRICE -> {
                    logger.info("Editing a menu item");
                    editingItemPrice();
                }
                case STAFF_MANAGEMENT -> {
                    logger.info("Displaying staff menu");
                    StaffMenuHandler.displayStaffManagementMenu();
                }
                case ORDER_HISTORY -> {
                    logger.info("Displaying Order History menu");
                    OrderHistoryHandler.displayOrderHistoryMenu();
                }
                case BACK_TO_MAIN -> {
                    logger.info("Returning to Main Menu");
                    return false;
                }
                case QUIT -> {
                    logger.info("Terminating the application");
                    System.exit(0);
                }
            }
            return true;
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid selection: " + selection);
            throw new InvalidSelectionException();
        }
    }

    private static void addingItem() {
        System.out.println("Enter the name for the item to add:");
        String itemName = input.nextLine();
        logger.info("User entered an item name to add: " + itemName);
        try {
            if (menu.checkIfItemExists(itemName)) {
                logger.warn("User entered a duplicate name: " + itemName);
                throw new DuplicateNameException();
            }

            System.out.println("Enter the price for the item: ");
            double itemPrice = MenuHandler.readDoubleInput(input);
            logger.info("User entered the price for the item: " + itemPrice);

            if (itemPrice <= 0) {
                logger.warn("Invalid price entered: " + itemPrice);
                throw new InvalidPriceException();

            }
            menu.addingItem(itemName, itemPrice);
        } catch (DuplicateNameException | InvalidPriceException e) {
            logger.error("Error while adding an item: " + e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    private static void deletingItem() {
        System.out.println("Enter the name for the item to delete:");
        String itemName = input.nextLine();
        logger.info("User entered a name to delete: " + itemName);
        menu.removeItem(itemName);
    }

    private static void editingItemPrice() {
        System.out.println("Enter the name for the item you want to edit:");
        String itemName = input.nextLine();
        logger.debug("User entered item name: " + itemName);
        try {
            if (!menu.checkIfItemExists(itemName)) {
                logger.warn("Item not found: " + itemName);
                throw new DataNotFoundException();

            } else {
                System.out.println("Enter the new price for the item:");
                double itemPrice = MenuHandler.readDoubleInput(input);
                logger.debug("User entered new price: " + itemPrice);
                if (itemPrice <= 0) {
                    logger.warn("Invalid price entered: " + itemPrice);
                    throw new InvalidPriceException();
                }
                menu.updateItemPrice(itemName, itemPrice);
            }
        } catch (DataNotFoundException | InvalidPriceException e) {
            logger.error("Error while editing item price: " + e.getMessage());
            System.out.println(e.getMessage());
        }
    }
}