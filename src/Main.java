
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    public static Scanner input = new Scanner(System.in);
    public static String userSelection = "";
    public static Menu menu = new Menu();
    public static Order order = new Order(menu);
    public static StaffManagement staffManagement = new StaffManagement();

    public static void main(String[] args) {
        logger.info("Application Started");
        Constants.printWelcome();
        Constants.clearOrderFile();
        logger.info("Order file cleared");
        while (true) {
            logger.info("Loading Main Menu");
            try {
                displayMenu();
            } catch (InvalidSelectionException e) {
                logger.error("Error, loading main menu");
                System.out.println(e.getMessage());
            }
        }
    }
    private static void printMenu(String[] options) {
        logger.info("Printing Menu");
        for (String option : options) {
            System.out.println(option);
        }
        System.out.print("Select a valid option: ");
    }

    private static String getUserSelection() {
        logger.debug("Getting user selection");
        return input.nextLine();
    }

    private static void displayMenu() throws InvalidSelectionException {
        String[] MainMenuOptions = {"MAIN MENU", "1: Employees", "2: Customers", "0: Quit"};
        printMenu(MainMenuOptions);
        userSelection = getUserSelection();
        logger.debug("User Selected: "+ userSelection);
        switch (userSelection) {
            case Constants.EMPLOYEE_MENU_OPTION:
                logger.info("Displaying Employee Menu");
                displayEmployeeMenu();
                break;

            case Constants.CUSTOMER_MENU_OPTION:
                logger.info("Displaying Customer Menu");
                displayCustomerMenu();
                break;

            case Constants.QUIT_MENU_OPTION:
                logger.info("terminating the application");
                System.exit(0);
                break;

            default:
                logger.warn("Invalid selection: " + userSelection);
                throw new InvalidSelectionException();
        }
    }

    private static void displayCustomerMenu() throws InvalidSelectionException{
        String[] customerMenuOptions = {
                "CUSTOMER MENU",
                "1: Display Restaurant Menu",
                "2: Place an Order",
                "3: Back to Main Menu",
                "0: Quit"
        };
        while (true) {
            try {
                printMenu(customerMenuOptions);
                userSelection = getUserSelection();
                logger.debug("User selected: " + userSelection);
                switch (userSelection) {
                    case "1":
                        logger.info("Displaying Restaurant Menu");
                        menu.currentMenu();
                        System.out.println();
                        break;

                    case "2":
                        logger.info("Placing an Order");
                        int orderType = 0;
                        boolean orderPlaced = false;
                        while (!orderPlaced) {
                            try {
                                orderType = placeOrderMenu();
                                orderPlaced = true;
                            } catch (InvalidSelectionException e) {
                                logger.error("Invalid selection while placing order", e);
                                System.out.println(e.getMessage());
                            }
                        }
                        order.printOrder(orderType);
                        order = new Order(menu); // Reset the order object
                        System.out.println();
                        break;

                    case "3":
                        logger.info("Returning to Main Menu");
                        return; // Go back logger.info("Terminating the application");to the previous menu
                    case Constants.QUIT_MENU_OPTION:
                        logger.info("Terminating the application");
                        System.exit(0);
                        break;
                    default:
                        logger.warn("Invalid selection: " + userSelection);
                        throw new InvalidSelectionException();

                }
            }catch (InvalidSelectionException e){
                logger.error("Invalid selection in customer menu");
                System.out.println(e.getMessage());
            }
        }
    }

    private static void displayEmployeeMenu() {
        String[] employeeMenuOptions = {
                "EMPLOYEE MENU",
                "1: Display Menu",
                "2: Add an Item",
                "3: Delete an Item by Name",
                "4: Edit Price by Name",
                "5: Staff Management",
                "6: Back to Main Menu",
                "0: Quit"
        };
        while (true) {
            try {
                printMenu(employeeMenuOptions);
                userSelection = getUserSelection();
                logger.debug("User selected: " + userSelection);
                switch (userSelection) {
                    case "1":
                        logger.info("Displaying Restaurant Menu");
                        menu.currentMenu();
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
                        return;

                    case Constants.QUIT_MENU_OPTION:
                        logger.info("Terminating the application");
                        System.exit(0);
                        break;

                    default:
                        logger.warn("Invalid selection: " + userSelection);
                        throw new InvalidSelectionException();
                }
            }catch (InvalidSelectionException e){
                logger.error("Invalid selection in employee menu");
                System.out.println(e.getMessage());
            }
        }
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
                double itemPrice = input.nextDouble();
                input.nextLine();
                logger.debug("User entered new price: " + itemPrice);
                if (itemPrice <= 0) {
                    logger.warn("Invalid price entered: " + itemPrice);
                    throw new InvalidPriceException();
                }
                menu.editingItemPrice(itemName, itemPrice);
            }
        } catch (DataNotFoundException | InvalidPriceException e) {
            logger.error("Error while editing item price: " + e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    private static void deletingItem() {
        System.out.println("Enter the name for the item to delete:");
        String itemName = input.nextLine();
        logger.info("User entered a name to delete: " + itemName);
        menu.deletingItem(itemName);
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
            double itemPrice = input.nextDouble();
            logger.info("User entered the price for the item: " + itemPrice);
            input.nextLine();
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

    private static void displayStaffManagementMenu() throws InvalidSelectionException {
        String[] staffManagementMenuOptions = {
                "STAFF MANAGEMENT MENU",
                "1: Display Staff",
                "2: Hire a Staff Member",
                "3: Fire a Staff Member",
                "4: Update Staff Information",
                "5: Back to Employee Menu",
                "0: Quit"
        };
        while (true) {
            try {
                printMenu(staffManagementMenuOptions);
                userSelection = getUserSelection();
                logger.info("User selected: " + userSelection);

                switch (userSelection) {
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
                        return;

                    case Constants.QUIT_MENU_OPTION:
                        logger.info("Terminating the application");
                        System.exit(0);
                        break;

                    default:
                        logger.warn("Invalid selection: " + userSelection);
                        throw new InvalidSelectionException();
                }
            }catch (InvalidSelectionException e){
                logger.error("Invalid selection in staff menu");
                System.out.println(e.getMessage());
            }
        }

    }

    private static void handleUpdateStaffInfo() {
        try {
            System.out.println("Enter the Id for the staff member to update:");
            int staffId = input.nextInt();
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
                double staffSalary = input.nextDouble();
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

    private static void handleFireStaffMember() {
        System.out.println("Enter the Id for the staff to fire");
        int staffId = input.nextInt();
        logger.info("User entered: " + staffId);
        staffManagement.handleFireStaffMember(staffId);
        input.nextLine();
    }

    private static void handleHireStaffMember() {
        try {
            System.out.println("Enter the new Staff Id:");
            int staffId = input.nextInt();
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
                String staffRoll = input.nextLine();
                logger.info("Role entered: " + staffRoll);
                System.out.println("Enter the new Staff Salary:");
                double staffSalary = input.nextDouble();
                logger.info("Salary entered: " + staffSalary);
                if (staffSalary <= 0) {
                    logger.warn("Invalid salary entered: " + staffSalary);
                    throw new InvalidSalaryException();
                }

                staffManagement.handleHireStaffMember(staffName, staffId, staffRoll, staffSalary);
                input.nextLine();
            }
        } catch (DuplicateNameException | InvalidSalaryException e) {
            System.out.println(e.getMessage());
            logger.error("Error while hiring a staff: " + e.getMessage());
            input.nextLine();
        }
    }
    private static int placeOrderMenu() throws InvalidSelectionException {
        String[] orderTypeOptions = {
                "Please select an option:",
                "For dining in select: 1",
                "For pick-up select: 2",
                "For delivery select: 3"
        };
        printMenu(orderTypeOptions);
        logger.info("validating order type");
        int orderType = getValidOrderType();
        logger.info("Order type selected: " + orderType);

        while (true) {
            logger.info("displaying current menu");
            menu.currentMenu();
            try {
                logger.info("Adding item to order");
                addItemToOrder();
            } catch (DataNotFoundException e) {
                //throw new RuntimeException(e);
                logger.error("error while Adding item to order: " + e.getMessage());
                System.out.println(e.getMessage());
            }

            if (!continueOrdering()) {
                logger.info("User has chosen to stop ordering");
                break;
            }
        }

        return orderType;
    }

    private static int getValidOrderType() throws InvalidSelectionException {
        if (input.hasNextInt()) {
            int orderType = input.nextInt();
            input.nextLine(); // Consume newline

            if (orderType == Constants.DINE_IN || orderType == Constants.PICK_UP || orderType == Constants.DELIVERY) {
                logger.info("User selected a valid order type: " + orderType);
                return orderType;
            }else{
                logger.warn("User selected an invalid order type: " + orderType);
            }
        } else {
            logger.warn("User entered non-integer input for order type");
            input.nextLine(); // Consume invalid input
        }
        logger.error("Invalid selection made");
        throw new InvalidSelectionException("Invalid Selection Exception");
    }

    private static void addItemToOrder() throws DataNotFoundException {
        System.out.println("Enter the name of the item to add to your order:");
        String itemName = input.nextLine();
        logger.info("Name entered: " + itemName);

        if (menu.checkIfItemExists(itemName)) {
            logger.info("Item found in the menu: " + itemName);
            System.out.println("Enter the quantity:");
            int quantity = input.nextInt();
            logger.info("quantity entered: " + quantity);
            input.nextLine(); // Consume newline
            order.placeOrder(itemName, quantity);
        } else {
            logger.warn("Item not found in the menu: " + itemName);
            System.out.println("Item not found in the menu.");
            throw new DataNotFoundException();
        }
    }

    private static boolean continueOrdering() {
        String[] continueOrderingMenu = {
                "Press 1 to continue adding to your order",
                "Press 2 to check out"
        };
        logger.info("Prompting user to continue ordering or check out.");
        do {
            printMenu(continueOrderingMenu);
            userSelection = getUserSelection();
            if (userSelection.equals("2")){
                logger.info("User chose to check out.");
                return false;
            }
        }while (!userSelection.equals("1"));
        logger.info("User chose to continue ordering.");
        return true;
    }
}
