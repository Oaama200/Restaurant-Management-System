import java.util.Objects;

public class StaffMember {
    private String Name;
    private int id;
    private String roll;
    private String salary;

    public StaffMember(String name, int id, String roll, String salary) {
        this.Name = name;
        this.id = id;
        this.roll = roll;
        this.salary = salary;
    }

    public String getName() {
        return Name;
    }

    public int getId() {
        return id;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
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
        return getId() == that.getId() && getName().equals(that.getName()) && getRoll().equals(that.getRoll()) && getSalary().equals(that.getSalary());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getId(), getRoll(), getSalary());

    }
    @Override
    public String toString() {

        String staffName = getName();
        int staffId = getId();
        String staffROll = getRoll();
        String staffSalary = getSalary();


        return String.format("Name: %-10s ID: %-10d Roll: %-10s Salary: %-10s",
                staffName, staffId, staffROll, staffSalary);
    }
}
