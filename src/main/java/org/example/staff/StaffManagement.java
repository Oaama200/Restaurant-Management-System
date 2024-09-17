package org.example.staff;

import org.example.exceptions.DataNotFoundException;
import org.example.utilities.SetupDefaults;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class StaffManagement implements StaffOperations, SetupDefaults {
    private final Map<Integer, StaffMember> staffMembers;

    public StaffManagement() {
        staffMembers = new HashMap<>();
        initializeDefaultItems();
    }

    @Override
    public void initializeDefaultItems() {
        addStaffMember(new StaffMember("John", 111, "cook", 60000));
        addStaffMember(new StaffMember("Lis", 222, "Host", 40000));
        addStaffMember(new StaffMember("Sam", 333, "Cashier", 50000));
    }

    private void addStaffMember(StaffMember staff) {
        staffMembers.put(staff.getId(), staff);
    }

    public void printCurrentStaff() {
        Consumer<StaffMember> printStaffInfo = staff -> System.out.println(staff.toString());
        for (StaffMember staff : staffMembers.values()) {
            printStaffInfo.accept(staff);
        }
    }

    public void handleHireStaffMember(String name, int id, String role, double salary) {
        StaffMember newStaff = new StaffMember(name, id, role, salary);
        staffMembers.put(id, newStaff);
        System.out.println("Staff member hired successfully");
    }

    @Override
    public void handleFireStaffMember(int id) {
        try {
            if (staffMembers.remove(id) != null) {
                System.out.println("Staff with ID " + id + " has been fired.");
            } else {
                throw new DataNotFoundException("ID Not found Exception");
            }
        } catch (DataNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void handleUpdateStaffInfo(int id, String role, double salary) {
        StaffMember updateStaff = findStaffById(id);
        if (updateStaff != null) {
            UnaryOperator<StaffMember> updateInfo = staff -> {
                staff.setRole(role);
                staff.setSalary(salary);
                return staff;
            };
            updateInfo.apply(updateStaff);
            System.out.println("Staff with ID " + id + " has been Updated.");
        } else {
            System.out.println("Staff member not found.");
        }
    }

    public StaffMember findStaffById(int id) {
        for (StaffMember staff : staffMembers.values()) {
            if (staff.getId() == id) {
                return staff;
            }
        }
        return null;
    }

    public boolean checkIfIdExists(int id) {
        Function<Integer, Boolean> idExistsCheck = staffId -> findStaffById(staffId) != null;
        return idExistsCheck.apply(id);
    }
}
