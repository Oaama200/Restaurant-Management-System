package org.example.menu;

public enum MainMenuOption {
    EMPLOYEE("1", "Employees"),
    CUSTOMER("2", "Customers"),
    QUIT("0", "Quit");

    private final String code;
    private final String description;

    MainMenuOption(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static MainMenuOption getByCode(String code) {
        for (MainMenuOption option : values()) {
            if (option.code.equals(code)) {
                return option;
            }
        }
        throw new IllegalArgumentException("Invalid menu option code: " + code);
    }

}