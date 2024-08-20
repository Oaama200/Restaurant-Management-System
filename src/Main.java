import java.util.Scanner;

public class Main {
    public static Scanner input = new Scanner(System.in);
    public static String userSelection = "";
    public static Menu menu = new Menu();
    public static Order order = new Order(menu);
    public static StaffManagement staffManagement = new StaffManagement();

    public static void main(String[] args) {
        Constants.printWelcome();
        while (true) {
            displayMenu();
        }
    }

    private static void displayMenu() {
        System.out.println("MAIN MENU");
        System.out.println("1: Employees");
        System.out.println("2: Customers");
        System.out.println("0: Quit");
        System.out.print("Select an option: ");
        userSelection = input.nextLine();
        switch (userSelection) {
            case "1":
                displayEmployeeMenu();
                break;

            case "2":
                displayCustomerMenu();
                break;

            case "0":
                System.exit(0);

            default:
                System.out.println("Invalid selection.");
        }
    }

    private static void displayCustomerMenu() {

        while (true) {
            System.out.println("CUSTOMER MENU");
            System.out.println("1: Display Restaurant Menu");
            System.out.println("2: Place an Order");
            System.out.println("3: Back to Main Menu");
            System.out.println("0: Quit");
            System.out.print("Select an option: ");

            userSelection = input.nextLine();
            switch (userSelection) {
                case "1":
                    menu.currentMenu();
                    System.out.println();
                    break;

                case "2":
                    int orderType = placeOrderMenu();
                    order.printOrder(orderType);
                    order = new Order(menu); // Reset the order object
                    System.out.println();
                    break;

                case "3":
                    return; // Go back to the previous menu

                case "0":
                    System.exit(0);

                default:
                    System.out.println("Invalid selection.");
            }
        }
    }

    private static void displayEmployeeMenu() {
        while (true) {

            System.out.println("EMPLOYEE MENU");
            System.out.println("1: Display Menu");
            System.out.println("2: Add an Item");
            System.out.println("3: Delete an Item by Name");
            System.out.println("4: Edit Price by Name");
            System.out.println("5: Staff Management");
            System.out.println("6: Back to Main Menu");
            System.out.println("0: Quit");
            System.out.print("Select an option: ");

            userSelection = input.nextLine();
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

                case "0":
                    System.exit(0);

                default:
                    System.out.println("Response not recognized.");
            }
        }
    }

    private static void editingItemPrice() {
        System.out.println("Enter the name for the item you want to edit:");
        String itemName = input.nextLine();
        if (!menu.checkIfItemExists(itemName)) {
            System.out.println("Item not found.");
        } else {
            System.out.println("Enter the new price for the item:");
            double itemPrice = input.nextDouble();
            input.nextLine();
            menu.editItemPrice(itemName, itemPrice);

        }
    }

    private static void deletingItem() {
        System.out.println("Enter the name for the item to delete:");
        String itemName = input.nextLine();
        menu.deleteItem(itemName);
    }

    private static void addingItem() {
        System.out.println("Enter the name for the item to add:");
        String itemName = input.nextLine();
        if (menu.checkIfItemExists(itemName)) {
            System.out.println(itemName + " already exists.");
        } else {
            System.out.println("Enter the price for the item:");
            double itemPrice = input.nextDouble();
            input.nextLine();
            menu.addItem(itemName, itemPrice);
        }
    }

    private static void displayStaffManagementMenu() {
        while (true) {
            System.out.println("STAFF MANAGEMENT MENU");
            System.out.println("1: Display Staff");
            System.out.println("2: Hire a Staff Member");
            System.out.println("3: Fire a Staff Member");
            System.out.println("4: Update Staff Information");
            System.out.println("5: Back to Employee Menu");
            System.out.println("0: Quit");
            System.out.print("Select an option: ");

            String userSelection = input.nextLine();

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

                case "0":
                    System.exit(0);

                default:
                    System.out.println("Response not recognized.");
            }
        }

    }

    private static void handleUpdateStaffInfo() {
        System.out.println("Enter the Id for the staff member to update:");
        int staffId = input.nextInt();
        input.nextLine();
        if (!staffManagement.checkIfIdExists(staffId)) {
            System.out.println("Id does not exist.");
        } else {
            System.out.println("Enter the new Role for the staff member:");
            String staffRole = input.nextLine();

            System.out.println("Enter the new Salary for the staff member:");
            String staffSalary = input.nextLine();

            staffManagement.updateStaffInfo(staffId, staffRole, staffSalary);

        }
    }

    private static void handleFireStaffMember() {
        System.out.println("Enter the Id for the staff to fire");
        int staffId = input.nextInt();
        staffManagement.fireStaffMember(staffId);
        input.nextLine();
    }

    private static void handleHireStaffMember() {
        System.out.println("Enter the new Staff Id:");
        int staffId = input.nextInt();
        input.nextLine();
        if (staffManagement.checkIfIdExists(staffId)) {
            System.out.println(staffId + " already exists.");
        } else {

            System.out.println("Enter the new Staff Name:");
            String staffName = input.nextLine();

            System.out.println("Enter the new Staff Role:");
            String staffRoll = input.nextLine();
            System.out.println("Enter the new Staff Salary:");
            String staffSalary = input.nextLine();


            staffManagement.hireStaffMember(staffName, staffId, staffRoll, staffSalary);

        }
    }

    private static int placeOrderMenu() {
        String itemName;
        int quantity;
        int selection;
        int orderType = 0;

        boolean validSelection = false;

        do {
            System.out.println("Please select an option:");
            System.out.println("For dining in select: 1");
            System.out.println("For pick-up select: 2");
            System.out.println("For delivery select: 3");

            if (input.hasNextInt()) {
                orderType = input.nextInt();
                input.nextLine();

                if (orderType == Constants.DINE_IN || orderType == Constants.PICK_UP  || orderType == Constants.DELIVERY ) {
                    validSelection = true;
                } else {
                    System.out.println("Invalid input. Please enter a valid number.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                input.nextLine();
            }
        } while (!validSelection);


        do {
            menu.currentMenu();
            System.out.println("Enter the name of the item to add to your order:");
            itemName = input.nextLine();
            if (menu.checkIfItemExists(itemName)) {
                System.out.println("Enter the quantity:");
                quantity = input.nextInt();
                input.nextLine();
                order.placeOrder(itemName, quantity);
            } else {
                System.out.println("Item not found.");
            }
            System.out.println("Press 1 to continue adding to your order \nPress 2 to check out");
            selection = input.nextInt();
            input.nextLine(); // Consume newline
        } while (selection == 1);
        return orderType;
    }
}
