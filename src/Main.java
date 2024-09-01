import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class Main {
    public static final Logger logger = LogManager.getLogger(Main.class);
    public static Scanner input = new Scanner(System.in);
    public static Menu menu = new Menu();
    public static Order order = new Order(menu);
    public static StaffManagement staffManagement = new StaffManagement();
    public static MenuHandler menuHandler = new MenuHandler(input, logger, menu, order, staffManagement);

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
