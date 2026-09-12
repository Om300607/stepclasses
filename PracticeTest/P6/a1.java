import java.util.Arrays;

class EventTicket {
    protected String attendeeId;
    protected double basePrice;
    protected double balanceDue;
    protected double[] lateFeeHistory = new double[0];
    protected static int totalTicketsIssued = 0;
    protected final String ticketId;

    public EventTicket(String attendeeId, double basePrice) {
        if (attendeeId == null || attendeeId.trim().isEmpty() || attendeeId.length() < 4) {
            throw new IllegalArgumentException("Invalid attendeeId");
        }
        this.attendeeId = attendeeId;
        this.basePrice = basePrice;
        this.balanceDue = basePrice;
        totalTicketsIssued++;
        this.ticketId = "TCK-" + (1000 + totalTicketsIssued);
    }

    public EventTicket(double basePrice) {
        this.attendeeId = "STU" + (++totalTicketsIssued);
        this.basePrice = basePrice;
        this.balanceDue = basePrice;
        this.ticketId = "TCK-" + (1000 + totalTicketsIssued);
    }

    public void pay(double amount) {
        if (amount > 0) {
            this.balanceDue -= amount;
        }
    }

    public void pay(double amount, String mode) {
        pay(amount);
    }

    public double getBalanceDue() {
        return this.balanceDue;
    }

    protected void applyLateFee(double amount) {
        this.balanceDue += amount;
        double[] newHistory = Arrays.copyOf(this.lateFeeHistory, this.lateFeeHistory.length + 1);
        newHistory[newHistory.length - 1] = amount;
        this.lateFeeHistory = newHistory;
    }

    public double[] getLateFeeHistory() {
        return Arrays.copyOf(this.lateFeeHistory, this.lateFeeHistory.length);
    }

    public String printTicket() {
        return "Standard Event Ticket | Balance Due: " + this.balanceDue;
    }

    public static String registerBatch(String[] attendeeIds, double basePrice) {
        int registered = 0;
        int rejected = 0;
        for (String id : attendeeIds) {
            try {
                new EventTicket(id, basePrice);
                registered++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }
        return "Registered: " + registered + " Rejected: " + rejected;
    }

    public static boolean isValidPromoCode(String code) {
        if (code == null || code.length() != 5) {
            return false;
        }
        if (code.charAt(0) != 'F') {
            return false;
        }
        for (int i = 1; i <= 3; i++) {
            if (!Character.isDigit(code.charAt(i))) {
                return false;
            }
        }
        return Character.isUpperCase(code.charAt(4));
    }

    public static int getTicketsIssued() {
        return totalTicketsIssued;
    }
}

class WorkshopTicket extends EventTicket {
    protected String track;

    public WorkshopTicket(String attendeeId, double basePrice, String track) {
        super(attendeeId, basePrice);
        this.track = track;
    }

    public WorkshopTicket(double basePrice) {
        super(basePrice);
        this.track = "General";
    }

    @Override
    protected void applyLateFee(double amount) {
        super.applyLateFee(amount * 2);
    }

    @Override
    public String printTicket() {
        return "Workshop Ticket | Track: " + this.track + " | Balance Due: " + this.balanceDue;
    }
}

public class a1 {
    public static void main(String[] args) {
        try {
            EventTicket t1 = new EventTicket("ST1", 500);
        } catch (IllegalArgumentException e) {
            System.out.println("construction rejected");
        }

        WorkshopTicket w = new WorkshopTicket("STU2", 1200, "AI/ML");
        w.pay(500);
        System.out.println(w.getBalanceDue());

        String[] ids = {"STU1", "ST1", "STU2", "STU3", "ST2"};
        System.out.println(EventTicket.registerBatch(ids, 500));
    }
}