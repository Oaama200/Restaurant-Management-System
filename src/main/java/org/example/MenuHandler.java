package org.example;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MenuHandler {
    private static Scanner input;
    private static  Logger logger ;
    public static String userSelection = "";

    public MenuHandler(Scanner input, Logger logger) {
        MenuHandler.input = input;
        MenuHandler.logger = logger;
    }

    static String displayMenuAndGetSelection(List<String> menuOptions) {
        printMenu(menuOptions);
        return getUserSelection();
    }

    public void displayMainMenu() throws InvalidSelectionException {
        List<String> mainMenuOptions = Arrays.asList("MAIN MENU", "1: Employees", "2: Customers", "0: Quit");
        userSelection = displayMenuAndGetSelection(mainMenuOptions);
        handleMainMenuSelection(userSelection);
    }

    private void handleMainMenuSelection(String selection) throws InvalidSelectionException {
        switch (selection) {
            case Constants.EMPLOYEE_MENU_OPTION:
                logger.info("Displaying Employee Menu");
                EmployeeMenuHandler.displayEmployeeMenu();
                break;

            case Constants.CUSTOMER_MENU_OPTION:
                logger.info("Displaying Customer Menu");
                CustomerMenuHandler.displayCustomerMenu();
                break;

            case Constants.QUIT_MENU_OPTION:
                logger.info("Terminating the application");
                System.exit(0);
                break;

            default:
                logger.warn("Invalid selection: " + selection);
                throw new InvalidSelectionException();
        }
    }

    static void printMenu(List<String> options) {
        logger.info("Printing Menu");
        for (String option : options) {
            System.out.println(option);
        }
        System.out.print("Select a valid option: ");
    }

    static String getUserSelection() {
        logger.debug("Getting user selection");
        return input.nextLine();
    }

    public static int readIntInput(Scanner input) {
        while (!input.hasNextInt()) {
            System.out.println("Please enter a valid number.");
            input.next();
        }
        int value = input.nextInt();
        input.nextLine();
        return value;
    }

    public static double readDoubleInput(Scanner input) {
        while (!input.hasNextDouble()) {
            System.out.println("Please enter a valid number.");
            input.next();
        }
        double value = input.nextDouble();
        input.nextLine();
        return value;
    }
}
