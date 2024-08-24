import java.util.Scanner;

public class Main {
    public static Scanner input = new Scanner(System.in);
    public static String userSelection = "";
    public static Menu menu = new Menu();
    public static Order order = new Order(menu);
    public static StaffManagement staffManagement = new StaffManagement();

    public static void main(String[] args) {
        Constants.printWelcome();
        Constants.clearOrderFile();
        while (true) {
            try {
                displayMenu();
            } catch (InvalidSelectionException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    private static void printMenu(String[] options) {
        for (String option : options) {
            System.out.println(option);
        }
        System.out.print("Select a valid option: ");
    }

    private static String getUserSelection() {
        return input.nextLine();
    }

    private static void displayMenu() throws InvalidSelectionException {
        String[] MainMenuOptions = {"MAIN MENU", "1: Employees", "2: Customers", "0: Quit"};
        printMenu(MainMenuOptions);
        userSelection = getUserSelection();
        switch (userSelection) {
            case Constants.EMPLOYEE_MENU_OPTION:
                displayEmployeeMenu();
                break;

            case Constants.CUSTOMER_MENU_OPTION:
                displayCustomerMenu();
                break;

            case Constants.QUIT_MENU_OPTION:
                System.exit(0);
                break;
            default:
                throw new InvalidSelectionException();
        }
    }

    private static void displayCustomerMenu() {
        String[] customerMenuOptions = {
                "CUSTOMER MENU",
                "1: Display Restaurant Menu",
                "2: Place an Order",
                "3: Back to Main Menu",
                "0: Quit"
        };
        while (true) {
            printMenu(customerMenuOptions);
            userSelection = getUserSelection();
            switch (userSelection) {
                case "1":
                    menu.currentMenu();
                    System.out.println();
                    break;

                case "2":
                    int orderType = 0;
                    boolean orderPlaced = false;
                    while (!orderPlaced) {
                        try {
                            orderType = placeOrderMenu();
                            orderPlaced = true;
                        } catch (InvalidSelectionException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    order.printOrder(orderType);
                    order = new Order(menu); // Reset the order object
                    System.out.println();
                    break;

                case "3":
                    return; // Go back to the previous menu
                case Constants.QUIT_MENU_OPTION:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid selection.");
                    break;
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
            printMenu(employeeMenuOptions);
            userSelection = getUserSelection();
            switch (userSelection) {
                case "1":
                    menu.currentMenu();
                    break;

                case "2":
                    addingItem();
                    break;

                case "3":
                    deletingItem();
                    break;

                case "4":
                    editingItemPrice();
                    break;

                case "5":
                    displayStaffManagementMenu();
                    break;

                case "6":
                    return;

                case Constants.QUIT_MENU_OPTION:
                    System.exit(0);
                    break;

                default:
                    System.out.println("Response not recognized.");
                    break;
            }
        }
    }

    private static void editingItemPrice() {

        System.out.println("Enter the name for the item you want to edit:");
        String itemName = input.nextLine();
        try {
            if (!menu.checkIfItemExists(itemName)) {
                throw new DataNotFoundException();
                //System.out.println("NOT FOUND");
            } else {
                System.out.println("Enter the new price for the item:");
                double itemPrice = input.nextDouble();
                input.nextLine();
                menu.editingItemPrice(itemName, itemPrice);
            }
        } catch (DataNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void deletingItem() {
        System.out.println("Enter the name for the item to delete:");
        String itemName = input.nextLine();
        menu.deletingItem(itemName);
    }

    private static void addingItem() {
        System.out.println("Enter the name for the item to add:");
        String itemName = input.nextLine();
        try {
            if (menu.checkIfItemExists(itemName)) {
                throw new NameAlreadyExistsException();

            }

            System.out.println("Enter the price for the item:");
            double itemPrice = input.nextDouble();
            input.nextLine();
            menu.addingItem(itemName, itemPrice);
        } catch (NameAlreadyExistsException e) {
            System.out.println(e.getMessage());
        }

    }

    private static void displayStaffManagementMenu() {
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
            printMenu(staffManagementMenuOptions);
             userSelection = getUserSelection();

            switch (userSelection) {
                case "1":
                    staffManagement.currentStaff();
                    break;

                case "2":
                    handleHireStaffMember();
                    break;

                case "3":
                    handleFireStaffMember();
                    break;

                case "4":
                    handleUpdateStaffInfo();
                    break;

                case "5":
                    return;

                case Constants.QUIT_MENU_OPTION:
                    System.exit(0);
                    break;

                default:
                    System.out.println("Response not recognized.");
                    break;
            }
        }

    }

    private static void handleUpdateStaffInfo() {
        try {

            System.out.println("Enter the Id for the staff member to update:");
            int staffId = input.nextInt();
            input.nextLine();
            if (!staffManagement.checkIfIdExists(staffId)) {
                //System.out.println("Id does not exist.");
                throw new DataNotFoundException();
            } else {
                System.out.println("Enter the new Role for the staff member:");
                String staffRole = input.nextLine();

                System.out.println("Enter the new Salary for the staff member:");
                String staffSalary = input.nextLine();

                staffManagement.handleUpdateStaffInfo(staffId, staffRole, staffSalary);

            }
        } catch (DataNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void handleFireStaffMember() {
        System.out.println("Enter the Id for the staff to fire");
        int staffId = input.nextInt();
        staffManagement.handleFireStaffMember(staffId);
        input.nextLine();
    }

    private static void handleHireStaffMember() {
        try {

            System.out.println("Enter the new Staff Id:");
            int staffId = input.nextInt();
            input.nextLine();
            if (staffManagement.checkIfIdExists(staffId)) {
                //System.out.println(staffId + " already exists.");
                throw new NameAlreadyExistsException("Staff Already Hired Exception");
            } else {

                System.out.println("Enter the new Staff Name:");
                String staffName = input.nextLine();

                System.out.println("Enter the new Staff Role:");
                String staffRoll = input.nextLine();
                System.out.println("Enter the new Staff Salary:");
                String staffSalary = input.nextLine();

                staffManagement.handleHireStaffMember(staffName, staffId, staffRoll, staffSalary);

            }
        } catch (NameAlreadyExistsException e) {
            System.out.println(e.getMessage());
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

        int orderType = getValidOrderType();

        while (true) {
            menu.currentMenu();
            addItemToOrder();

            if (!continueOrdering()) {
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
                return orderType;
            }
        } else {
            input.nextLine(); // Consume invalid input
        }
        throw new InvalidSelectionException("Invalid input. Please enter a valid number.");
    }

    private static void addItemToOrder() {
        System.out.println("Enter the name of the item to add to your order:");
        String itemName = input.nextLine();

        if (menu.checkIfItemExists(itemName)) {
            System.out.println("Enter the quantity:");
            int quantity = input.nextInt();
            input.nextLine(); // Consume newline
            order.placeOrder(itemName, quantity);
        } else {
            System.out.println("Item not found in the menu.");
        }
    }

    private static boolean continueOrdering() {
        String[] continueOrderingMenu = {
                "Press 1 to continue adding to your order",
                "Press 2 to check out"
        };
        do {
            printMenu(continueOrderingMenu);
            userSelection = getUserSelection();
            if (userSelection.equals("2")){
                return false;
            }
        }while (!userSelection.equals("1"));
        return true;
    }
}
