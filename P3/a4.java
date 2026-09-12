class BrokenSrmStudent {
    static String name;
    static String regNo;
    static int attendance;

    public BrokenSrmStudent(String n, String r, int a) {
        name = n;
        regNo = r;
        attendance = a;
    }
}

class FixedSrmStudent {
    static String university = "SRM";
    static int admissionCount = 0;

    String name;
    String regNo;
    int attendance;

    public FixedSrmStudent(String name, int attendance) {
        admissionCount++;
        this.name = name;
        this.regNo = "RA23110030101" + admissionCount;
        this.attendance = attendance;
    }

    public void printIdCard() {
        System.out.println(name + " | " + regNo);
    }

    public static void printTotalAdmissions() {
        System.out.println("Students admitted so far: " + admissionCount);
    }
}

public class a4 {
    public static void main(String[] args) {
        System.out.println("Broken version:");
        new BrokenSrmStudent("Ravi", "RA1", 80);
        new BrokenSrmStudent("Meera", "RA2", 90);
        System.out.println(BrokenSrmStudent.name);
        System.out.println(BrokenSrmStudent.name);
        System.out.println("(Ravi's data was overwritten — both students now show \"Meera\")\n");

        System.out.println("Fixed version: same two students created");
        FixedSrmStudent f1 = new FixedSrmStudent("Ravi", 80);
        FixedSrmStudent f2 = new FixedSrmStudent("Meera", 90);
        f1.printIdCard();
        f2.printIdCard();
        FixedSrmStudent.printTotalAdmissions();
    }
}