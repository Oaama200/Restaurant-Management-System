package org.example.staff;

import org.example.exceptions.DataNotFoundException;
import org.example.utilities.SetupDefaults;

import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

public class StaffManagement implements StaffOperations, SetupDefaults {
    private static Map<Integer, StaffMember> staffMembers = null;

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
        staffMembers.values().stream().forEach(staffMember -> System.out.println(staffMember.toString()));
    }

    public static void handleHireStaffMember(String name, int id, String role, double salary) {
        StaffMember newStaff = new StaffMember(name, id, role, salary);
        staffMembers.put(id, newStaff);
        System.out.println("Staff member hired successfully");
    }

    @Override
    public void handleFireStaffMember(int id) {
        try {
            boolean isRemoved = staffMembers.entrySet().stream()
                    .filter(staffMember -> staffMember.getValue().getId() == id)
                    .findFirst()
                    .map(staffMember -> staffMembers.remove(staffMember.getKey()) != null)
                    .orElse(false);
            if (isRemoved) {
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
        return staffMembers.values().stream()
                .filter(staffMember -> staffMember.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public boolean checkIfIdExists(int id) {
        return staffMembers.values().stream()
                .anyMatch(staffMember -> staffMember.getId() == id);

    }
}

