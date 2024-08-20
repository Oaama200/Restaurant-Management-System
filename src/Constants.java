import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class Constants {
    public static final double TAX_RATE;
    public static final double SERVICE_FEE;
    public static final double DELIVERY_FEE;
    public static final int DINE_IN;
    public static final int PICK_UP;
    public static final int DELIVERY;

    public static final String DINE_IN_NAME;
    public static final String PICK_UP_NAME;
    public static final String DELIVERY_NAME;
    public static final String WELCOME_MESSAGE;
    static {
        TAX_RATE = 0.06;
        SERVICE_FEE = 0.05;
        DELIVERY_FEE = 0.10;
        DINE_IN = 1;
        PICK_UP = 2;
        DELIVERY = 3;

        DINE_IN_NAME = "Dine-in";
        PICK_UP_NAME = "Pick-up";
        DELIVERY_NAME = "Delivery";
        WELCOME_MESSAGE = "****** Welcome to Restaurant Management System *******";
    }

    public static final void printCurrentDateTime() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        System.out.println("Date and Time: " + formattedDateTime);
    }

    public static final void printWelcome() {
        System.out.println(WELCOME_MESSAGE);
    }
}