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
        System.out.println("Paid via " + mode);
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

class GroupTicket extends EventTicket {
    protected int groupSize;

    public GroupTicket(double basePrice, int groupSize) {
        super(basePrice);
        this.groupSize = groupSize;
    }
}

public class a5 {
    public static String processNightlySettlement(EventTicket[] tickets) {
        int processed = 0;
        int skipped = 0;
        int groups = 0;
        int individuals = 0;

        for (EventTicket t : tickets) {
            if (t == null) {
                skipped++;
            } else {
                processed++;
                if (t instanceof GroupTicket) {
                    groups++;
                } else {
                    individuals++;
                }
            }
        }
        return processed + " processed " + skipped + " null skipped | " + groups + " group | " + individuals + " individual";
    }

    public static void main(String[] args) {
        EventTicket t1 = new EventTicket(500);
        System.out.println(t1.ticketId);
        System.out.println(EventTicket.getTicketsIssued());

        System.out.println(EventTicket.isValidPromoCode("F123A"));
        System.out.println(EventTicket.isValidPromoCode("F12A"));
        System.out.println(EventTicket.isValidPromoCode("X123A"));

        t1.pay(200);
        t1.pay(200, "UPI");
        System.out.println(t1.getBalanceDue());

        EventTicket[] tickets = {
            new GroupTicket(2000, 5),
            null,
            new EventTicket(500)
        };
        System.out.println(processNightlySettlement(tickets));
    }
}