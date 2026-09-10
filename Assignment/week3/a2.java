class Employee {
    private int empId;
    private String empName;
    private double salary;

    public Employee(int empId, String empName, double salary) {
        this.empId = empId;
        this.empName = empName;
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }
}

class ManagerEmployee extends Employee {
    private double teamBonus;

    public ManagerEmployee(int empId, String empName, double salary, double teamBonus) {
        super(empId, empName, salary);
        this.teamBonus = teamBonus;
    }

    public double effectiveSalary() {
        return getSalary() + teamBonus;
    }
}

class InternEmployee extends Employee {
    private double stipendCap;

    public InternEmployee(int empId, String empName, double salary, double stipendCap) {
        super(empId, empName, salary);
        this.stipendCap = stipendCap;
    }

    public double effectiveSalary() {
        return Math.min(getSalary(), stipendCap);
    }
}

public class a2 {
    public static void main(String[] args) {
        Employee plain = new Employee(1, "John", 40000);
        Employee manager = new ManagerEmployee(2, "Alice", 70000, 8000);
        Employee intern = new InternEmployee(3, "Bob", 12000, 10000);

        Employee[] employees = {plain, manager, intern};

        for (Employee emp : employees) {
            if (emp instanceof ManagerEmployee) {
                ManagerEmployee m = (ManagerEmployee) emp;
                System.out.println("Manager effective pay: Rs " + m.effectiveSalary());
            } else if (emp instanceof InternEmployee) {
                InternEmployee in = (InternEmployee) emp;
                System.out.println("Intern effective pay: Rs " + in.effectiveSalary());
            } else if (emp instanceof Employee) {
                System.out.println("Plain employee pay: Rs " + emp.getSalary());
            }
        }
    }
}