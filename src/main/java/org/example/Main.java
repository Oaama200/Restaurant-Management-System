package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.customer.CustomerMenuHandler;
import org.example.employee.EmployeeMenuHandler;
import org.example.exceptions.InvalidSelectionException;
import org.example.menu.Menu;
import org.example.menu.MenuHandler;
import org.example.order.Order;
import org.example.order.OrderHistoryHandler;
import org.example.staff.StaffManagement;
import org.example.staff.StaffMenuHandler;
import org.example.utilities.Constants;
import org.example.utilities.CustomLinkedList;

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
        staffMenuHandler = new StaffMenuHandler(input, logger, staffManagement);
        employeeMenuHandler = new EmployeeMenuHandler(input, logger, menu);
    }

    public static void main(String[] args) {
        // Uncomment the following method call to test the reflection class
        // Reflection.executeReflectionDemo(StaffManagement.class);
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
