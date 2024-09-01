import java.util.HashMap;
import java.util.Map;

public class StaffManagement implements StaffOperations, SetupDefaults{
    private final Map<Integer, StaffMember> staffMembers;

    public StaffManagement(){
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
        staffMembers.put(staff.getId(), staff); // Add to the map
    }
    public void currentStaff(){
        for(StaffMember staff : staffMembers.values()){
            System.out.println(staff);
        }
    }

    public void handleHireStaffMember(String name, int id, String role, double salary){

        StaffMember newStaff = new StaffMember(name, id, role, salary);
        staffMembers.put(id, newStaff);
            System.out.println("Staff member hired successfully.");

    }
    @Override
    public void handleFireStaffMember(int id){
        try {
        //StaffMember StaffToFire = findStaffById(id);
        if (staffMembers.remove(id) != null) {
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
    }
    public StaffMember findStaffById(int id){
        for(StaffMember staff: staffMembers.values()){
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
