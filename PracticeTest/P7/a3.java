// Problem 3: Quarterly Bonus Calculator

interface Auditable {
    String auditRecord();
}

abstract class StaffMember {
    private double baseSalary;
    protected double bonusRate;

    // Simple constructor chains to the richer one - no duplicated assignment
    public StaffMember(double baseSalary) {
        this(baseSalary, 0.10);
    }

    public StaffMember(double baseSalary, double bonusRate) {
        this.baseSalary = baseSalary;
        this.bonusRate = bonusRate;
    }

    public abstract double calculateBonus();

    public double getSalary() {
        return baseSalary;
    }

    public void setSalary(double baseSalary) {
        if (baseSalary < 0) {
            System.out.println("Rejected: salary cannot be negative");
            return; // salary unchanged
        }
        this.baseSalary = baseSalary;
    }
}

class TeamLead extends StaffMember implements Auditable {
    private final int teamSize;

    public TeamLead(double baseSalary, int teamSize) {
        super(baseSalary);                  // uses default 0.10 rate
        this.teamSize = teamSize;
    }

    public TeamLead(double baseSalary, double bonusRate, int teamSize) {
        super(baseSalary, bonusRate);
        this.teamSize = teamSize;
    }

    @Override
    public double calculateBonus() {
        return getSalary() * bonusRate;
    }

    @Override
    public String auditRecord() {
        return "TeamLead audit: " + teamSize + " team members, salary $" + getSalary();
    }
}

public class a3 {

    static String getAuditIfApplicable(StaffMember s) {
        if (s instanceof Auditable) {
            Auditable a = (Auditable) s; // safe cast against the interface
            return a.auditRecord();
        }
        return "No audit required";
    }

    public static void main(String[] args) {
        TeamLead t = new TeamLead(60000, 5);
        System.out.println(t.calculateBonus());      // 6000.0

        TeamLead t2 = new TeamLead(60000, 0.20, 5);
        System.out.println(t2.calculateBonus());     // 12000.0

        t.setSalary(-5000);                          // rejected
        System.out.println(t.getSalary());           // 60000.0

        StaffMember ref = t; // upcasting: TeamLead stored as its parent type
        System.out.println(getAuditIfApplicable(ref));
    }
}