import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MenuHandler {
    private final Scanner input;
    private final Logger logger;
    private final Menu menu;
    private Order order;
    private final StaffManagement staffManagement;
    public static String userSelection = "";

    public MenuHandler(Scanner input, Logger logger, Menu menu, Order order, StaffManagement staffManagement) {
        this.input = input;
        this.logger = logger;
        this.menu = menu;
        this.order = order;
        this.staffManagement = staffManagement;
    }

    private String displayMenuAndGetSelection(List<String> menuOptions) {
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
                displayEmployeeMenu();
                break;

            case Constants.CUSTOMER_MENU_OPTION:
                logger.info("Displaying Customer Menu");
                displayCustomerMenu();
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

    private void displayCustomerMenu() {
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
                userSelection = displayMenuAndGetSelection(customerMenuOptions);
                continueMenu = handleCustomerMenuSelection(userSelection);
            } catch (InvalidSelectionException e) {
                System.out.println(e.getMessage());
            }

        }
    }

    private boolean handleCustomerMenuSelection(String selection) throws InvalidSelectionException {
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

    private void displayEmployeeMenu() {
        List<String> employeeMenuOptions = Arrays.asList(
                "EMPLOYEE MENU",
                "1: Display Menu",
                "2: Add an Item",
                "3: Delete an Item by Name",
                "4: Edit Price by Name",
                "5: Staff Management",
                "6: Back to Main Menu",
                "0: Quit"
        );
        boolean continueMenu = true;
        while (continueMenu) {
            try {
                userSelection = displayMenuAndGetSelection(employeeMenuOptions);
                continueMenu = handleEmployeeMenuSelection(userSelection);
            } catch (InvalidSelectionException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    private boolean handleEmployeeMenuSelection(String selection) throws InvalidSelectionException {
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
                displayStaffManagementMenu();
                break;

            case "6":
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

    private void displayStaffManagementMenu() {
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
                userSelection = displayMenuAndGetSelection(staffManagementMenuOptions);
                continueMenu = handleStaffManagementMenuSelection(userSelection);
            } catch (InvalidSelectionException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private boolean handleStaffManagementMenuSelection(String selection) throws InvalidSelectionException {
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

    private void printMenu(List<String> options) {
        logger.info("Printing Menu");
        for (String option : options) {
            System.out.println(option);
        }
        System.out.print("Select a valid option: ");
    }

    private String getUserSelection() {
        logger.debug("Getting user selection");
        return input.nextLine();
    }
    private void placeOrder() throws InvalidSelectionException {
        List<String> orderTypeOptions = Arrays.asList(
                "Please select an option:",
                "For dining in select: 1",
                "For pick-up select: 2",
                "For delivery select: 3"
        );
        String orderTypeSelection = displayMenuAndGetSelection(orderTypeOptions);
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

        order.printOrder(orderType);
        //  Order completedOrder = this.order;
        this.order = new Order(menu); // Reset the order object
    }

    private int handleOrderTypeSelection(String selection) throws InvalidSelectionException {
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

    private void addingItem() {
        System.out.println("Enter the name for the item to add:");
        String itemName = input.nextLine();
        logger.info("User entered an item name to add: " + itemName);
        try {
            if (menu.checkIfItemExists(itemName)) {
                logger.warn("User entered a duplicate name: " + itemName);
                throw new DuplicateNameException();
            }

            System.out.println("Enter the price for the item: ");
           //double itemPrice = input.nextDouble();
            double itemPrice = readDoubleInput(input);

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

    private void deletingItem() {
        System.out.println("Enter the name for the item to delete:");
        String itemName = input.nextLine();
        logger.info("User entered a name to delete: " + itemName);
        menu.removeItem(itemName);
    }

    private void editingItemPrice() {
        System.out.println("Enter the name for the item you want to edit:");
        String itemName = input.nextLine();
        logger.debug("User entered item name: " + itemName);
        try {
            if (!menu.checkIfItemExists(itemName)) {
                logger.warn("Item not found: " + itemName);
                throw new DataNotFoundException();

            } else {
                System.out.println("Enter the new price for the item:");
                double itemPrice = readDoubleInput(input);
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

    private void handleHireStaffMember() {
        try {
            System.out.println("Enter the new Staff Id:");
            int staffId = readIntInput(input);
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
                double staffSalary = readDoubleInput(input);
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

    private void handleFireStaffMember() {
        System.out.println("Enter the Id for the staff to fire");
        int staffId = readIntInput(input);
        logger.info("User entered: " + staffId);
        staffManagement.handleFireStaffMember(staffId);
        input.nextLine();
    }

    private void handleUpdateStaffInfo() {
        try {
            System.out.println("Enter the Id for the staff member to update:");
            int staffId = readIntInput(input);
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
                double staffSalary = readDoubleInput(input);
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

    private void addItemToOrder() throws DataNotFoundException {
        System.out.println("Enter the name of the item to add to your order:");
        String itemName = input.nextLine();
        logger.info("Name entered: " + itemName);

        if (menu.checkIfItemExists(itemName)) {
            logger.info("Item found in the menu: " + itemName);
            System.out.println("Enter the quantity:");
            int quantity = readIntInput(input);
            logger.info("quantity entered: " + quantity);
            order.placeOrder(itemName, quantity);
        } else {
            logger.warn("Item not found in the menu: " + itemName);
            System.out.println("Item not found in the menu.");
            throw new DataNotFoundException();
        }
    }

    private boolean continueOrdering() {
        List<String> continueOrderingMenu = Arrays.asList(
                "Press 1 to continue adding to your order",
                "Press 2 to check out"
        );
        logger.info("Prompting user to continue ordering or check out.");
        printMenu(continueOrderingMenu);
        do {
            userSelection = getUserSelection();
            if (userSelection.equals("2")) {
                logger.info("User chose to check out.");

                return false;
            }
        } while (!userSelection.equals("1"));
        logger.info("User chose to continue ordering.");
        return true;
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
