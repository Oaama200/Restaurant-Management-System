package org.example.staff;

import java.util.ArrayList;
import java.util.List;

public enum StaffManagementOption {
    CURRENT_STAFF("1", "Display current staff"),
    HIRE_STAFF("2", "Hire a staff member"),
    FIRE_STAFF("3", "Fire a staff member"),
    UPDATE_STAFF("4", "Update staff info"),
    RETURN("5", "Return to employee menu"),
    QUIT("0", "Quit");

    private final String code;
    private final String description;

    StaffManagementOption(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static StaffManagementOption getByCode(String code) {
        for (StaffManagementOption option : values()) {
            if (option.code.equals(code)) {
                return option;
            }
        }
        throw new IllegalArgumentException("Invalid staff management option code: " + code);
    }
    public static List<String> getMenuOptions() {
        List<String> options = new ArrayList<>();
        for (StaffManagementOption option : values()) {
            options.add(option.code + ": " + option.description);
        }
        return options;
    }
}
