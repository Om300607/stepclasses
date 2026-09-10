class Emp {
    private int empId;
    private String empName;
    private double salary;

    public Emp(int empId, String empName, double salary) {
        this.empId = empId;
        this.empName = empName;
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }

    public double effectiveSalary() {
        return salary;
    }
}

class MgrEmp extends Emp {
    private double teamBonus;

    public MgrEmp(int empId, String empName, double salary, double teamBonus) {
        super(empId, empName, salary);
        this.teamBonus = teamBonus;
    }

    @Override
    public double effectiveSalary() {
        return getSalary() + teamBonus;
    }
}

class InternEmp extends Emp {
    private double stipendCap;

    public InternEmp(int empId, String empName, double salary, double stipendCap) {
        super(empId, empName, salary);
        this.stipendCap = stipendCap;
    }

    @Override
    public double effectiveSalary() {
        return Math.min(getSalary(), stipendCap);
    }
}

class PSlot {
    String slotNo;
    int capacity;
    int occupiedCount;

    public PSlot(String slotNo, int capacity, int occupiedCount) {
        this.slotNo = slotNo;
        this.capacity = capacity;
        this.occupiedCount = occupiedCount;
    }

    public void allot() {
        if (occupiedCount < capacity) {
            occupiedCount++;
        }
    }
}

class CompanyEmployeeRecord {
    static int totalRecords = 0;

    String name;
    String empId;
    Emp employee;
    PSlot slot;

    public CompanyEmployeeRecord(String name, String empId, Emp employee, PSlot slot) {
        this.name = name;
        this.empId = empId;
        this.employee = employee;
        this.slot = slot;
        totalRecords++;
    }

    public String fullProfile() {
        String slotDisplay = (slot != null) ? slot.slotNo : "no parking assigned";
        return name + " | Pay: Rs " + employee.effectiveSalary() + " | Slot: " + slotDisplay;
    }
}

public class a5 {

    public static PSlot findAvailableSlot(PSlot[] slots) {
        if (slots == null) {
            return null;
        }
        for (PSlot s : slots) {
            if (s != null && s.occupiedCount < s.capacity) {
                return s;
            }
        }
        return null;
    }

    public static PSlot safeAllot(PSlot[] slots) {
        PSlot available = findAvailableSlot(slots);
        if (available != null) {
            available.allot();
            return available;
        }
        return null;
    }

    public static void main(String[] args) {
        PSlot[] slots = {
            new PSlot("A1", 1, 0),
            new PSlot("A2", 1, 0)
        };

        Emp e1 = new MgrEmp(101, "Divya", 70000.0, 8000.0);
        Emp e2 = new Emp(102, "Karan", 40000.0);
        Emp e3 = new InternEmp(103, "Meera", 12000.0, 10000.0);

        CompanyEmployeeRecord r1 = new CompanyEmployeeRecord("Divya", "EMP101", e1, safeAllot(slots));
        CompanyEmployeeRecord r2 = new CompanyEmployeeRecord("Karan", "EMP102", e2, safeAllot(slots));
        CompanyEmployeeRecord r3 = new CompanyEmployeeRecord("Meera", "EMP103", e3, null);

        System.out.println(r1.fullProfile());
        System.out.println(r2.fullProfile());
        System.out.println(r3.fullProfile());
        System.out.println("Total records: " + CompanyEmployeeRecord.totalRecords);
    }
}