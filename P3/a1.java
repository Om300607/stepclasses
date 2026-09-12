class F1SrmStudent {
    String name;
    String regNo;
    int attendance;

    public F1SrmStudent(String name, String regNo, int attendance) {
        this.name = name;
        this.regNo = regNo;
        this.attendance = attendance;
    }

    public void addAttendanceUpdate(int newAttendance) {
        this.attendance = newAttendance;
    }

    public boolean isEligible() {
        return this.attendance >= 75;
    }

    public static double classAverage(F1SrmStudent[] students) {
        double total = 0;
        for (F1SrmStudent s : students) {
            total += s.attendance;
        }
        return total / students.length;
    }
}

public class a1 {
    public static void main(String[] args) {
        F1SrmStudent[] students = {
            new F1SrmStudent("Ravi", "101", 82),
            new F1SrmStudent("Anitha", "102", 68),
            new F1SrmStudent("Karthik", "103", 91),
            new F1SrmStudent("Meera", "104", 74),
            new F1SrmStudent("Suresh", "105", 60)
        };

        for (F1SrmStudent s : students) {
            System.out.println(s.name + " " + s.attendance + "% " + (s.isEligible() ? "Eligible" : "Detained"));
        }
        System.out.println("Class average: " + F1SrmStudent.classAverage(students) + "%");
    }
}