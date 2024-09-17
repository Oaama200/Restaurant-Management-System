package org.example.customer;

import java.util.ArrayList;
import java.util.List;

public enum CustomerMenuOption {
    DISPLAY_MENU("1", "Displaying Restaurant Menu"),
    PLACE_ORDER("2", "Place an Order"),
    BACK_TO_MAIN("3", "Back to Main Menu"),
    QUIT("0", "Quit");

    private final String code;
    private final String description;
    CustomerMenuOption(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static CustomerMenuOption getByCode(String code) {
        for (CustomerMenuOption option : values()) {
            if (option.code.equals(code)) {
                return option;
            }
        }
        throw new IllegalArgumentException("Invalid employee menu option code: " + code);
    }

    public static List<String> getMenuOptions() {
        List<String> options = new ArrayList<>();
        options.add("CUSTOMER MENU");
        for (CustomerMenuOption option : values()) {
            options.add(option.code + ": " + option.description);
        }
        return options;
    }

}
