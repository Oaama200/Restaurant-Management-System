package org.example;

import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class StaffMenuHandler {
    static Scanner input;
    static Logger logger;
    private static Order order;
    static Order currentOrder;
    static CustomLinkedList<Order> orderHistory = null;
    static Menu menu;
    public static String userSelection = "";
    static StaffManagement staffManagement;

    public StaffMenuHandler(Scanner input, Logger logger, Menu menu, Order order,
                            StaffManagement staffManagement, CustomLinkedList<Order> orderHistory) {
        StaffMenuHandler.logger = logger;
        StaffMenuHandler.input = input;
        StaffMenuHandler.currentOrder = order;
        StaffMenuHandler.menu = menu;
        StaffMenuHandler.orderHistory = new CustomLinkedList<>();
        StaffMenuHandler.staffManagement = staffManagement;
    }


    static void displayStaffManagementMenu() {
        List<String> staffManagementMenuOptions = Arrays.asList(
                "STAFF MANAGEMENT MENU",
                "1: Display Staff",
                "2: Hire a Staff Member",
                "3: Fire a Staff Member",
                "4: Update Staff Information",
                "5: Back to Employee Menu",
                "0: Quit"
        );
        boolean continueMenu = true;
        while (continueMenu) {
            try {
                userSelection = MenuHandler.displayMenuAndGetSelection(staffManagementMenuOptions);
                continueMenu = handleStaffManagementMenuSelection(userSelection);
            } catch (InvalidSelectionException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    private static boolean handleStaffManagementMenuSelection(String selection) throws InvalidSelectionException {
        switch (selection) {
            case "1":
                logger.info("Displaying current staff Menu");
                staffManagement.currentStaff();
                break;

            case "2":
                logger.info("Hiring a staff");
                handleHireStaffMember();
                break;

            case "3":
                logger.info("Firing a staff");
                handleFireStaffMember();
                break;

            case "4":
                logger.info("Updating staff info");
                handleUpdateStaffInfo();
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


    private static void handleHireStaffMember() {
        try {
            System.out.println("Enter the new Staff Id:");
            int staffId = MenuHandler.readIntInput(input);
            input.nextLine();
            logger.info("User entered: " + staffId);
            if (staffManagement.checkIfIdExists(staffId)) {
                logger.warn("Staff Already hired: " + staffId);
                throw new DuplicateNameException("Staff Already Hired Exception");
            } else {

                System.out.println("Enter the new Staff Name:");
                String staffName = input.nextLine();
                logger.info("Name entered: " + staffName);
                System.out.println("Enter the new Staff Role:");
                String staffRole = input.nextLine();
                logger.info("Role entered: " + staffRole);
                System.out.println("Enter the new Staff Salary:");
                double staffSalary = MenuHandler.readDoubleInput(input);
                logger.info("Salary entered: " + staffSalary);
                if (staffSalary <= 0) {
                    logger.warn("Invalid salary entered: " + staffSalary);
                    throw new InvalidSalaryException();
                }

                staffManagement.handleHireStaffMember(staffName, staffId, staffRole, staffSalary);
                input.nextLine();
            }
        } catch (DuplicateNameException | InvalidSalaryException e) {
            System.out.println(e.getMessage());
            logger.error("Error while hiring a staff: " + e.getMessage());
            input.nextLine();
        }
    }

    private static void handleFireStaffMember() {
        System.out.println("Enter the Id for the staff to fire");
        int staffId = MenuHandler.readIntInput(input);
        logger.info("User entered: " + staffId);
        staffManagement.handleFireStaffMember(staffId);
        input.nextLine();
    }

    private static void handleUpdateStaffInfo() {
        try {
            System.out.println("Enter the Id for the staff member to update:");
            int staffId = MenuHandler.readIntInput(input);
            input.nextLine();
            logger.info("User entered: " + staffId);
            if (!staffManagement.checkIfIdExists(staffId)) {
                logger.warn("Id entered not found: " + staffId);
                throw new DataNotFoundException("ID not found Exception");
            } else {
                System.out.println("Enter the new Role for the staff member:");
                String staffRole = input.nextLine();
                logger.info("Role entered: " + staffRole);

                System.out.println("Enter the new Salary for the staff member:");
                double staffSalary = MenuHandler.readDoubleInput(input);
                logger.info("Salary entered: " + staffSalary);
                if (staffSalary <= 0) {
                    input.nextLine();
                    logger.warn("Invalid salary entered: " + staffSalary);
                    throw new InvalidSalaryException();
                }
                input.nextLine();
                staffManagement.handleUpdateStaffInfo(staffId, staffRole, staffSalary);
            }
        } catch (DataNotFoundException | InvalidSalaryException e) {
            logger.error("Error while updating staff info: " + e.getMessage());
            System.out.println(e.getMessage());
        }
    }
}