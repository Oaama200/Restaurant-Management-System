package org.example;

import java.io.FileWriter;
import java.io.IOException;
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
    public static final String EMPLOYEE_MENU_OPTION = "1";
    public static final String CUSTOMER_MENU_OPTION = "2";
    public static final String QUIT_MENU_OPTION = "0";
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
    public static  void clearOrderFile() {
        try (FileWriter fileWriter = new FileWriter("order_summary.txt", false)) {
            // Writing an empty string to the file, which clears its contents
            fileWriter.write("");
        } catch (IOException e) {
            System.out.println("An error occurred while clearing the order summary file.");
            e.printStackTrace();
        }
    }

    public static String printCurrentDateTime() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        return "Date and Time: " + formattedDateTime;
    }

    public static void printWelcome() {
        System.out.println(WELCOME_MESSAGE);
    }
}

