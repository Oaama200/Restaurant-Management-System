package org.example;

import org.example.staff.StaffManagement;

public class HireStaffMemberThread extends Thread{
    private final String name;
    private final int id;
    private final String role;
    private final double salary;

    public HireStaffMemberThread(String name, int id, String role, double salary) {
        this.name = name;
        this.id = id;
        this.role = role;
        this.salary = salary;

    }

    @Override
    public void run() {
        StaffManagement.handleHireStaffMember(name, id, role, salary);
    }
}

