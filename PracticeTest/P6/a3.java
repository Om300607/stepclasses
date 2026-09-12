import java.util.Arrays;

class EventTicket {
    protected String attendeeId;
    protected double basePrice;
    protected double balanceDue;
    protected double[] lateFeeHistory = new double[0];

    public EventTicket(double basePrice) {
        this.attendeeId = "STU1";
        this.basePrice = basePrice;
        this.balanceDue = basePrice;
    }

    public void pay(double amount) {
        if (amount > 0) {
            this.balanceDue -= amount;
        }
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
}

class WorkshopTicket extends EventTicket {
    protected String track;

    public WorkshopTicket(double basePrice) {
        super(basePrice);
        this.track = "General";
    }

    @Override
    protected void applyLateFee(double amount) {
        super.applyLateFee(amount * 2);
    }
}

public class a3 {
    public static void main(String[] args) {
        WorkshopTicket w = new WorkshopTicket(1200);
        w.pay(1200);
        w.applyLateFee(100);
        System.out.println(w.getBalanceDue());

        double[] history = w.getLateFeeHistory();
        System.out.println(Arrays.toString(history));

        history[0] = 999;
        System.out.println(Arrays.toString(w.getLateFeeHistory()));
    }
}