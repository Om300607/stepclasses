class F2FeeAccount {
    private String regNo;
    private double totalFee;
    private double amountPaid;

    public F2FeeAccount(String regNo, double totalFee, double amountPaid) {
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

class F2HostelFeeAccount extends F2FeeAccount {
    public F2HostelFeeAccount(String regNo, double totalFee, double amountPaid) {
        super(regNo, totalFee, amountPaid);
    }

    public void payInTwoInstallments(double amount) {
        pay(amount / 2);
        pay(amount / 2);
    }
}

class F2ScholarshipFeeAccount extends F2FeeAccount {
    private double scholarshipPercent;

    public F2ScholarshipFeeAccount(String regNo, double totalFee, double amountPaid, double scholarshipPercent) {
        super(regNo, totalFee, amountPaid);
        this.scholarshipPercent = scholarshipPercent;
    }

    public double effectiveDue() {
        return getDue() * (1 - (scholarshipPercent / 100));
    }
}

public class a2 {
    public static void main(String[] args) {
        F2FeeAccount plain = new F2FeeAccount("101", 150000, 150000);
        F2FeeAccount hostel = new F2HostelFeeAccount("102", 200000, 60000);
        F2FeeAccount scholarship = new F2ScholarshipFeeAccount("103", 180000, 0, 20);

        F2FeeAccount[] accounts = {plain, hostel, scholarship};

        for (F2FeeAccount acc : accounts) {
            if (acc instanceof F2ScholarshipFeeAccount) {
                System.out.println("Scholarship account effective due: Rs " + ((F2ScholarshipFeeAccount) acc).effectiveDue());
            } else if (acc instanceof F2HostelFeeAccount) {
                System.out.println("Hostel account due: Rs " + acc.getDue());
            } else {
                System.out.println("Plain account due: Rs " + acc.getDue());
            }
        }
    }
}