import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

enum LeaveStatus {
    PENDING {
        LeaveStatus approve() {
            return APPROVED;
        }
        LeaveStatus reject() {
            return REJECTED;
        }
        LeaveStatus revertToPending() {
            return PENDING;
        }
    },
    APPROVED {
        LeaveStatus approve() {
            throw new IllegalStateException("Request is already approved");
        }
        LeaveStatus reject() {
            throw new IllegalStateException("Approved request cannot be rejected");
        }
        LeaveStatus revertToPending() {
            throw new IllegalStateException("Approved request cannot revert to Pending");
        }
    },
    REJECTED {
        LeaveStatus approve() {
            throw new IllegalStateException("Rejected request cannot be approved");
        }
        LeaveStatus reject() {
            throw new IllegalStateException("Request is already rejected");
        }
        LeaveStatus revertToPending() {
            throw new IllegalStateException("Rejected request cannot revert to Pending");
        }
    };

    abstract LeaveStatus approve();
    abstract LeaveStatus reject();
    abstract LeaveStatus revertToPending();

    String label() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}

class LeaveRequest {
    Employee employee;
    LocalDate from;
    LocalDate to;
    LeaveStatus status = LeaveStatus.PENDING;
    LeaveRequest(Employee employee, LocalDate from, LocalDate to) {
        this.employee = employee;
        this.from = from;
        this.to = to;
    }
}

abstract class Employee {
    String name;
    Employee(String name) {
        this.name = name;
    }
    abstract int maxLeaveDays();

    LeaveRequest submitLeave(LocalDate from, LocalDate to) {
        long days = ChronoUnit.DAYS.between(from, to) + 1;
        if (days <= 0) {
            System.out.println("Leave request rejected: invalid dates.");
            return null;
        }
        if (days > maxLeaveDays()) {
            System.out.println("Leave request rejected: " + name + " can request at most " + maxLeaveDays() + " days.");
            return null;
        }
        LeaveRequest r = new LeaveRequest(this, from, to);
        System.out.println("Leave request submitted by " + name + " for " + from + " to " + to + ". Status: " + r.status.label() + ".");
        return r;
    }
}

class FullTimeEmployee extends Employee {
    FullTimeEmployee(String name) {
        super(name);
    }
    int maxLeaveDays() {
        return 20;
    }
}

class PartTimeEmployee extends Employee {
    PartTimeEmployee(String name) {
        super(name);
    }
    int maxLeaveDays() {
        return 7;
    }
}

class Contractor extends Employee {
    Contractor(String name) {
        super(name);
    }
    int maxLeaveDays() {
        return 5;
    }
}

class Manager extends Employee {
    Manager(String name) {
        super(name);
    }
    int maxLeaveDays() {
        return 25;
    }

    void approve(LeaveRequest r) {
        try {
            r.status = r.status.approve();
            System.out.println("Leave request for " + r.employee.name + " approved. Status: " + r.status.label() + ".");
        } catch (IllegalStateException e) {
            System.out.println("Cannot change status: " + e.getMessage() + ".");
        }
    }

    void reject(LeaveRequest r) {
        try {
            r.status = r.status.reject();
            System.out.println("Leave request for " + r.employee.name + " rejected. Status: " + r.status.label() + ".");
        } catch (IllegalStateException e) {
            System.out.println("Cannot change status: " + e.getMessage() + ".");
        }
    }

    void revertToPending(LeaveRequest r) {
        try {
            r.status = r.status.revertToPending();
            System.out.println("Leave request for " + r.employee.name + " set to Pending.");
        } catch (IllegalStateException e) {
            System.out.println("Cannot change status: " + e.getMessage() + ".");
        }
    }
}

public class a4 {
    public static void main(String[] args) {
        Manager manager = new Manager("Manager");
        Employee john = new FullTimeEmployee("John Doe");
        Employee jane = new PartTimeEmployee("Jane Smith");

        LeaveRequest r1 = john.submitLeave(LocalDate.of(2024, 10, 10), LocalDate.of(2024, 10, 12));
        manager.approve(r1);
        LeaveRequest r2 = jane.submitLeave(LocalDate.of(2024, 11, 1), LocalDate.of(2024, 11, 5));
        manager.revertToPending(r1);
    }
}