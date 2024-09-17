package org.example.order;

import java.util.ArrayList;
import java.util.List;

public enum OrderHistoryOption {
    CURRENT_ORDER("1", "Display Order History"),
    PRINT_ORDER("2", "Print an Order by ID"),
    REMOVE_ORDER("3", "Remove Order History Item by ID"),
    NUMBER_OF_ORDERS("4", "Display the number of orders for this session"),
    RETURN("5", "Return to employee menu"),
    QUIT("0", "Quit");

    private final String code;
    private final String description;

    OrderHistoryOption(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static OrderHistoryOption getByCode(String code) {
        for (OrderHistoryOption option : values()) {
            if (option.code.equals(code)) {
                return option;
            }
        }
        throw new IllegalArgumentException("Invalid staff management option code: " + code);
    }
    public static List<String> getMenuOptions() {
        List<String> options = new ArrayList<>();
        options.add("Staff Menu");
        for (OrderHistoryOption option : values()) {
            options.add(option.code + ": " + option.description);
        }
        return options;
    }
}
