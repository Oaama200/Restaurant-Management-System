package org.example.employee;

import java.util.ArrayList;
import java.util.List;

public enum EmployeeMenuOption {
    DISPLAY_MENU("1", "Display Menu"),
    ADD_ITEM("2", "Add an Item"),
    DELETE_ITEM("3", "Delete an Item by Name"),
    EDIT_PRICE("4", "Edit Price by Name"),
    STAFF_MANAGEMENT("5", "Staff Management"),
    ORDER_HISTORY("6", "Check Order History"),
    BACK_TO_MAIN("7", "Back to Main Menu"),
    QUIT("0", "Quit");

    private final String code;
    private final String description;

    EmployeeMenuOption(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static EmployeeMenuOption getByCode(String code) {
        for (EmployeeMenuOption option : values()) {
            if (option.code.equals(code)) {
                return option;
            }
        }
        throw new IllegalArgumentException("Invalid employee menu option code: " + code);
    }

    public static List<String> getMenuOptions() {
        List<String> options = new ArrayList<>();
        options.add("EMPLOYEE MENU");
        for (EmployeeMenuOption option : values()) {
            options.add(option.code + ": " + option.description);
        }
        return options;
    }
}