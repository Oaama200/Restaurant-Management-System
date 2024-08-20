import java.util.ArrayList;

public class StaffManagement implements StaffOperations, SetupDefaults{
    final private ArrayList<StaffMember> staffMember;

    public StaffManagement(){
        staffMember = new ArrayList<>();
        addDefaults();
    }

    @Override
    public void addDefaults() {
            staffMember.add(new StaffMember("John", 111, "cook", "60k"));
            staffMember.add(new StaffMember("Lis", 222, "Host", "40k"));
            staffMember.add(new StaffMember("Sam", 333, "Cashier", "40k"));
        }
    public void currentStaff(){
        for(StaffMember staff : staffMember){
            System.out.println(staff);
        }
    }

    @Override
    public void hireStaffMember(String name, int id, String roll, String salary){

        StaffMember newStaff = new StaffMember(name, id, roll, salary);
        if (staffMember.contains(newStaff)) {
            System.out.println("This staff member already exists and cannot be hired again.");
        } else {
            staffMember.add(newStaff);
            System.out.println("Staff member hired successfully.");
        }
    }
    @Override
    public void fireStaffMember(int id){

        StaffMember StaffToFire = findStaffById(id);
        if (StaffToFire != null) {
            staffMember.remove(StaffToFire);
            System.out.println("Staff with ID "+ id + " has been fired.");
        } else {
            System.out.println("Staff member not found.");
        }
    }

    public void updateStaffInfo( int id, String role, String salary){
        StaffMember updateStaff = findStaffById(id);
        if (updateStaff != null) {
            //updateStaff.setName(name);
            updateStaff.setRole(role);
            updateStaff.setSalary(salary);
            System.out.println("Staff with ID "+ id + " has been Updated.");
        } else {
            System.out.println("Staff member not found.");
        }

    }
    public StaffMember findStaffById(int id){
        for(StaffMember staff: staffMember){
            if(staff.getId() == id){
                return staff;
            }
        }
        return null;
    }
    public boolean checkIfIdExists(int id){
        for(StaffMember staff: staffMember){
            if(staff.getId() == id){
                return true;
            }
        }
        return false;
    }
}
