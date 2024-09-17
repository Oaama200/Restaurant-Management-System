package org.example.menu;
import org.apache.logging.log4j.Logger;
import org.example.customer.CustomerMenuHandler;
import org.example.employee.EmployeeMenuHandler;
import org.example.exceptions.InvalidSelectionException;

import java.util.ArrayList;
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

    public static String displayMenuAndGetSelection(List<String> menuOptions) {
        printMenu(menuOptions);
        return getUserSelection();
    }
    public void displayMainMenu() throws InvalidSelectionException {
        List<String> mainMenuOptions = new ArrayList<>();
        mainMenuOptions.add("MAIN MENU");
        for (MainMenuOption option : MainMenuOption.values()) {
            mainMenuOptions.add(option.getCode() + ": " + option.getDescription());
        }
        userSelection = displayMenuAndGetSelection(mainMenuOptions);
        handleMainMenuSelection(userSelection);
    }

private void handleMainMenuSelection(String selection) throws InvalidSelectionException {
    try {
        MainMenuOption option = MainMenuOption.getByCode(selection);
        switch (option) {
            case EMPLOYEE -> {
                logger.info("Displaying Employee Menu");
                EmployeeMenuHandler.displayEmployeeMenu();
            }
            case CUSTOMER -> {
                logger.info("Displaying Customer Menu");
                CustomerMenuHandler.displayCustomerMenu();
            }
            case QUIT -> {
                logger.info("Terminating the application");
                System.exit(0);
            }
        }
    } catch (IllegalArgumentException e) {
        logger.warn("Invalid selection: " + selection);
        throw new InvalidSelectionException();
    }
}
    public static void printMenu(List<String> options) {
        logger.info("Printing Menu");
        for (String option : options) {
            System.out.println(option);
        }
        System.out.print("Select a valid option: ");
    }

    public static String getUserSelection() {
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
