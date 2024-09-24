package org.example.staff;

import org.apache.logging.log4j.Logger;
import org.example.HireStaffMemberThread;
import org.example.exceptions.DataNotFoundException;
import org.example.exceptions.DuplicateNameException;
import org.example.exceptions.InvalidSalaryException;
import org.example.exceptions.InvalidSelectionException;
import org.example.menu.MenuHandler;

import java.util.List;
import java.util.Scanner;

public class StaffMenuHandler {
    static Scanner input;
    static Logger logger;
    public static String userSelection = "";
    static StaffManagement staffManagement;

    public StaffMenuHandler(Scanner input, Logger logger, StaffManagement staffManagement) {
        StaffMenuHandler.logger = logger;
        StaffMenuHandler.input = input;
        StaffMenuHandler.staffManagement = staffManagement;
    }

    public static void displayStaffManagementMenu() {
        List<String> staffManagementMenuOptions = StaffManagementOption.getMenuOptions();
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
        try {
            StaffManagementOption option = StaffManagementOption.getByCode(selection);

            switch (option) {
                case CURRENT_STAFF -> {
                    logger.info("Displaying current staff Menu");
                    staffManagement.printCurrentStaff();
                }
                case HIRE_STAFF -> {
                    logger.info("Hiring a staff");
                    handleHireStaffMember();
                }
                case FIRE_STAFF -> {
                    logger.info("Firing a staff");
                    handleFireStaffMember();
                }
                case UPDATE_STAFF -> {
                    logger.info("Updating staff info");
                    handleUpdateStaffInfo();
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


    private static void handleHireStaffMember() {
        try {
            System.out.println("Enter the new Staff Id:");
            int staffId = MenuHandler.readIntInput(input);
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
                //staffManagement.handleHireStaffMember(staffName, staffId, staffRole, staffSalary);
                Thread HireStaffMemberThread = new Thread(new HireStaffMemberThread(staffName, staffId, staffRole, staffSalary));
                HireStaffMemberThread.start();
            }
        } catch (DuplicateNameException | InvalidSalaryException e) {
            System.out.println(e.getMessage());
            logger.error("Error while hiring a staff: " + e.getMessage());
        }
    }

    private static void handleFireStaffMember() {
        System.out.println("Enter the Id for the staff to fire");
        int staffId = MenuHandler.readIntInput(input);
        logger.info("User entered: " + staffId);
        staffManagement.handleFireStaffMember(staffId);
    }

    private static void handleUpdateStaffInfo() {
        try {
            System.out.println("Enter the Id for the staff member to update:");
            int staffId = MenuHandler.readIntInput(input);
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
                    logger.warn("Invalid salary entered: " + staffSalary);
                    throw new InvalidSalaryException();
                }
                staffManagement.handleUpdateStaffInfo(staffId, staffRole, staffSalary);
            }
        } catch (DataNotFoundException | InvalidSalaryException e) {
            logger.error("Error while updating staff info: " + e.getMessage());
            System.out.println(e.getMessage());
        }
    }
}