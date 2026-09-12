class EventTicket {
    protected String attendeeId;
    protected double basePrice;
    protected double balanceDue;

    public EventTicket(double basePrice) {
        this.attendeeId = "STU1";
        this.basePrice = basePrice;
        this.balanceDue = basePrice;
    }

    public EventTicket(String attendeeId, double basePrice) {
        this.attendeeId = attendeeId;
        this.basePrice = basePrice;
        this.balanceDue = basePrice;
    }

    public double getBalanceDue() {
        return this.balanceDue;
    }

    public String printTicket() {
        return "Standard Balance: " + this.basePrice + " | Balance: " + this.balanceDue;
    }
}

class WorkshopTicket extends EventTicket {
    protected String track;

    public WorkshopTicket(double basePrice, String track) {
        super(basePrice);
        this.track = track;
    }

    public WorkshopTicket(String attendeeId, double basePrice, String track) {
        super(attendeeId, basePrice);
        this.track = track;
    }

    @Override
    public String printTicket() {
        return "Standard Balance: " + this.basePrice + " | Workshop | Track: " + this.track + " | Balance: " + this.getBalanceDue() + " [Track via downcast: " + this.track + "]";
    }
}

public class a4 {
    public static String batchPrint(EventTicket[] tickets) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tickets.length; i++) {
            EventTicket t = tickets[i];
            if (t instanceof WorkshopTicket) {
                WorkshopTicket wt = (WorkshopTicket) t;
                sb.append("Standard Balance: ").append(wt.basePrice).append(" | Workshop | Track: ").append(wt.track).append(" | Balance: ").append(wt.getBalanceDue()).append(" [Track via downcast: ").append(wt.track).append("]");
            } else {
                sb.append(t.printTicket());
            }
            if (i < tickets.length - 1) {
                sb.append(" | ");
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        EventTicket[] tickets = {
            new EventTicket(500),
            new WorkshopTicket(1200, "AI/ML")
        };
        System.out.println(batchPrint(tickets));
    }
}