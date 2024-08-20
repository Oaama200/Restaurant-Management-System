import java.util.Objects;

public class StaffMember {
    private String Name;
    private int id;
    private String role;
    private String salary;

    public StaffMember(String name, int id, String role, String salary) {
        this.Name = name;
        this.id = id;
        this.role = role;
        this.salary = salary;
    }

    public String getName() {
        return Name;
    }

    public int getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StaffMember that)) return false;
        return getId() == that.getId() && getName().equals(that.getName()) && getRole().equals(that.getRole()) && getSalary().equals(that.getSalary());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getId(), getRole(), getSalary());

    }
    @Override
    public String toString() {

        String staffName = getName();
        int staffId = getId();
        String staffRole = getRole();
        String staffSalary = getSalary();


        return String.format("Name: %-10s ID: %-10d Role: %-10s Salary: %-10s",
                staffName, staffId, staffRole, staffSalary);
    }
}
