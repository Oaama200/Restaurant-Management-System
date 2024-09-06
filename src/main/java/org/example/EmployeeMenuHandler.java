package org.example;

import org.apache.logging.log4j.Logger;
import java.util.Arrays;
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

    static void displayEmployeeMenu() {
        List<String> employeeMenuOptions = Arrays.asList(
                "EMPLOYEE MENU",
                "1: Display Menu",
                "2: Add an Item",
                "3: Delete an Item by Name",
                "4: Edit Price by Name",
                "5: Staff Management",
                "6: Check Order History",
                "7: Back to Main Menu",
                "0: Quit"
        );
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
        switch (selection) {
            case "1":
                logger.info("Displaying Restaurant Menu");
                menu.displayDefaultMenu();
                break;

            case "2":
                logger.info("Adding a menu item");
                addingItem();
                break;

            case "3":
                logger.info("Deleting a menu item");
                deletingItem();
                break;

            case "4":
                logger.info("Editing a menu item");
                editingItemPrice();
                break;

            case "5":
                logger.info("Displaying staff menu");
                StaffMenuHandler.displayStaffManagementMenu();
                break;

            case "6":
                logger.info("Displaying Order History menu");
                OrderHistoryHandler.displayOrderHistoryMenu();
                break;

            case "7":
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