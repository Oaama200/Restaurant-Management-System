package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class Main {
    public static final Logger logger = LogManager.getLogger(Main.class);
    private static final Scanner input = new Scanner(System.in);
    private static final Menu menu = new Menu();
    private static final Order order = new Order(menu);
    private static final StaffManagement staffManagement = new StaffManagement();
    private static final CustomLinkedList<Order> orderHistory = new CustomLinkedList<>();
    private static final MenuHandler menuHandler;
    private static final OrderHistoryHandler orderHistoryHandler;
    private static final CustomerMenuHandler customerMenuHandler;
    private static final StaffMenuHandler staffMenuHandler;
    private static final EmployeeMenuHandler employeeMenuHandler;

    static {
        menuHandler = new MenuHandler(input, logger);
        orderHistoryHandler = new OrderHistoryHandler(input, logger, order);
        customerMenuHandler = new CustomerMenuHandler(input, logger, menu, order);
        staffMenuHandler = new StaffMenuHandler(input, logger, menu, order, staffManagement, orderHistory);
        employeeMenuHandler = new EmployeeMenuHandler(input, logger, menu);
    }

    public static void main(String[] args) {
        logger.info("Application Started");
        Constants.printWelcome();
        Constants.clearOrderFile();
        logger.info("Order file cleared");
        while (true) {
            logger.info("Loading Main Menu");
            try {
                menuHandler.displayMainMenu();
            } catch (InvalidSelectionException e) {
                logger.error("Error, loading main menu");
                System.out.println(e.getMessage());
            }
        }
    }
}
