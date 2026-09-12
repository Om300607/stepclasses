class F5FeeAccount {
    private String regNo;
    private double totalFee;
    private double amountPaid;

    public F5FeeAccount(String regNo, double totalFee, double amountPaid) {
        this.regNo = regNo;
        this.totalFee = totalFee;
        this.amountPaid = amountPaid;
    }

    public void pay(double amount) {
        if (amount > 0) {
            this.amountPaid += amount;
        }
    }

    public double getDue() {
        return totalFee - amountPaid;
    }
}

class F5HostelFeeAccount extends F5FeeAccount {
    public F5HostelFeeAccount(String regNo, double totalFee, double amountPaid) {
        super(regNo, totalFee, amountPaid);
    }
}

class F5HostelRoom {
    String roomNo;
    int beds;
    int occupied;

    public F5HostelRoom(String roomNo, int beds, int occupied) {
        this.roomNo = roomNo;
        this.beds = beds;
        this.occupied = occupied;
    }

    public void allot() {
        if (occupied < beds) {
            occupied++;
        }
    }

    public static F5HostelRoom findAvailableRoom(F5HostelRoom[] rooms) {
        if (rooms == null) return null;
        for (F5HostelRoom room : rooms) {
            if (room != null && room.occupied < room.beds) {
                return room;
            }
        }
        return null;
    }

    public static F5HostelRoom safeAllot(F5HostelRoom[] rooms) {
        F5HostelRoom available = findAvailableRoom(rooms);
        if (available != null) {
            available.allot();
            return available;
        }
        return null;
    }
}

class F5SrmStudent {
    static int totalStudents = 0;

    String name;
    String regNo;
    F5HostelFeeAccount feeAccount;
    F5HostelRoom room;

    public F5SrmStudent(String name, String regNo, F5HostelFeeAccount feeAccount, F5HostelRoom room) {
        this.name = name;
        this.regNo = regNo;
        this.feeAccount = feeAccount;
        this.room = room;
        totalStudents++;
    }

    public String fullStatus() {
        String roomNo = (room != null) ? room.roomNo : "unallotted";
        return name + " | Due: Rs " + feeAccount.getDue() + " | Room: " + roomNo;
    }
}

public class a5 {
    public static void main(String[] args) {
        F5HostelRoom[] rooms = {
            new F5HostelRoom("C-214", 2, 1),
            new F5HostelRoom("C-507", 2, 1)
        };

        F5HostelFeeAccount f1 = new F5HostelFeeAccount("101", 200000, 60000);
        F5HostelFeeAccount f2 = new F5HostelFeeAccount("102", 200000, 20000);
        F5HostelFeeAccount f3 = new F5HostelFeeAccount("103", 200000, 0);

        f1.pay(-5000);

        F5SrmStudent s1 = new F5SrmStudent("Ravi", "101", f1, F5HostelRoom.safeAllot(rooms));
        F5SrmStudent s2 = new F5SrmStudent("Anitha", "102", f2, F5HostelRoom.safeAllot(rooms));
        F5SrmStudent s3 = new F5SrmStudent("Karthik", "103", f3, null);

        System.out.println(s1.fullStatus());
        System.out.println(s2.fullStatus());
        System.out.println(s3.fullStatus());
        System.out.println("Total students: " + F5SrmStudent.totalStudents);
    }
}