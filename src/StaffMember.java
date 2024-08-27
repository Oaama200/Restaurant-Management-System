import java.util.Objects;

public class StaffMember {
    private String name;
    private int id;
    private String role;
    private double salary;

    public StaffMember(String name, int id, String role, double salary) {
        this.name = name;
        this.id = id;
        this.role = role;
        this.salary = salary;
    }

    public String getName() {
        return name;
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

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StaffMember that)) return false;
        return getId() == that.getId() && getName().equals(that.getName()) && getRole().equals(that.getRole()) && getSalary()==(that.getSalary());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getId(), getRole(), getSalary());

    }
    @Override
    public String toString() {
        return String.format("Name: %-10s ID: %-10d Role: %-10s Salary: %-10s",
                name, id, role, salary);
    }
}
