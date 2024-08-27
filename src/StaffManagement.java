import java.util.ArrayList;
import java.util.List;

public class StaffManagement implements StaffOperations, SetupDefaults{
    private final List<StaffMember> staffMembers;

    public StaffManagement(){
        staffMembers = new ArrayList<>();
        initializeDefaultItems();
    }

    @Override
    public void initializeDefaultItems() {
        staffMembers.add(new StaffMember("John", 111, "cook", 60000));
        staffMembers.add(new StaffMember("Lis", 222, "Host", 40000));
        staffMembers.add(new StaffMember("Sam", 333, "Cashier", 50000));
        }
    public void currentStaff(){
        for(StaffMember staff : staffMembers){
            System.out.println(staff);
        }
    }

    public void handleHireStaffMember(String name, int id, String role, double salary){

        StaffMember newStaff = new StaffMember(name, id, role, salary);
        staffMembers.add(newStaff);
            System.out.println("Staff member hired successfully.");

    }
    @Override
    public void handleFireStaffMember(int id){
        try {
        StaffMember StaffToFire = findStaffById(id);
        if (StaffToFire != null) {
            staffMembers.remove(StaffToFire);
            System.out.println("Staff with ID "+ id + " has been fired.");
        } else {
            throw new DataNotFoundException("ID Not found Exception");
        }
        }catch (DataNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    public void handleUpdateStaffInfo(int id, String role, double salary){
            StaffMember updateStaff = findStaffById(id);
            if (updateStaff != null) {
                updateStaff.setRole(role);
                updateStaff.setSalary(salary);
                System.out.println("Staff with ID " + id + " has been Updated.");
            } else {
                System.out.println("Staff member not found.");
            }
//

    }
    public StaffMember findStaffById(int id){
        for(StaffMember staff: staffMembers){
            if(staff.getId() == id){
                return staff;
            }
        }
        return null;
    }
    public boolean checkIfIdExists(int id){
        return findStaffById(id) != null;
    }
}
