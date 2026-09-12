class EventTicket {
    protected String attendeeId;
    protected double basePrice;
    protected double balanceDue;

    public EventTicket(String attendeeId, double basePrice) {
        if (attendeeId == null || attendeeId.trim().isEmpty() || attendeeId.length() < 4) {
            throw new IllegalArgumentException("Invalid attendeeId");
        }
        this.attendeeId = attendeeId;
        this.basePrice = basePrice;
        this.balanceDue = basePrice;
    }

    public double getBalanceDue() {
        return this.balanceDue;
    }

    public String printTicket() {
        return "Standard Event Ticket | Balance Due: " + this.balanceDue;
    }
}

class WorkshopTicket extends EventTicket {
    protected String track;

    public WorkshopTicket(String attendeeId, double basePrice, String track) {
        super(attendeeId, basePrice);
        this.track = track;
    }

    @Override
    public String printTicket() {
        return "Workshop Ticket | Track: " + this.track + " | Balance Due: " + this.balanceDue;
    }
}

class PremiumWorkshopTicket extends WorkshopTicket {
    protected double kitFee;

    public PremiumWorkshopTicket(String attendeeId, double basePrice, String track, double kitFee) {
        super(attendeeId, basePrice, track);
        this.kitFee = kitFee;
        this.balanceDue += kitFee;
    }

    @Override
    public String printTicket() {
        return "Premium Workshop Ticket Track: " + this.track + " | Kit Fee: " + this.kitFee + " Balance Due: " + this.balanceDue;
    }
}

class HackathonTicket extends EventTicket {
    protected String teamName;

    public HackathonTicket(String attendeeId, double basePrice, String teamName) {
        super(attendeeId, basePrice);
        this.teamName = teamName;
    }

    @Override
    public String printTicket() {
        return "Hackathon Ticket | Team: " + this.teamName + " | Balance Due: " + this.balanceDue;
    }
}

public class a2 {
    public static String classifyGeneration(EventTicket ticket) {
        if (ticket instanceof PremiumWorkshopTicket) {
            return "Multilevel descendant (3 generations deep)";
        } else if (ticket instanceof WorkshopTicket || ticket instanceof HackathonTicket) {
            return "Hierarchical sibling (independent branch)";
        }
        return "Base generation";
    }

    public static double getTotalBalanceDue(EventTicket[] tickets) {
        double total = 0;
        for (EventTicket t : tickets) {
            if (t != null) {
                total += t.getBalanceDue();
            }
        }
        return total;
    }

    public static void main(String[] args) {
        EventTicket standard = new EventTicket("STU1", 500);
        WorkshopTicket workshop = new WorkshopTicket("STU2", 1200, "AI/ML");
        PremiumWorkshopTicket premium = new PremiumWorkshopTicket("STU3", 2000, "Cloud Native", 300);
        HackathonTicket hackathon = new HackathonTicket("STU4", 800, "Byte Force");

        System.out.println(standard.printTicket());
        System.out.println(workshop.printTicket());
        System.out.println(premium.printTicket());
        System.out.println(hackathon.printTicket());

        System.out.println(classifyGeneration(premium));
        System.out.println(classifyGeneration(hackathon));

        EventTicket[] tickets = {standard, workshop, premium, hackathon};
        System.out.println(getTotalBalanceDue(tickets));
    }
}