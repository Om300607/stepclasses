import java.util.HashSet;
import java.util.Set;

class BusTicket {
    String passengerName;
    String destination;
    boolean isCheckedIn;

    public BusTicket(String passengerName, String destination) {
        if (passengerName == null || passengerName.trim().isEmpty() ||
            destination == null || destination.trim().isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.passengerName = passengerName.trim();
        this.destination = destination.trim();
        this.isCheckedIn = false;
    }

    public void markCheckedIn() {
        if (isCheckedIn) {
            System.out.println("Already checked in");
        } else {
            isCheckedIn = true;
        }
    }
}

public class a1 {
    public static void processBatch(String[][] rawBookings) {
        int valid = 0;
        int rejected = 0;
        int duplicates = 0;
        Set<String> acceptedPairs = new HashSet<>();

        for (String[] raw : rawBookings) {
            try {
                BusTicket ticket = new BusTicket(raw[0], raw[1]);
                String key = ticket.passengerName.toLowerCase() + "|" + ticket.destination.toLowerCase();
                
                if (acceptedPairs.contains(key)) {
                    duplicates++;
                } else {
                    acceptedPairs.add(key);
                    valid++;
                }
            } catch (Exception e) {
                rejected++;
            }
        }

        System.out.println("Valid: " + valid + " | Rejected: " + rejected + " | Duplicates skipped: " + duplicates);
    }

    public static void main(String[] args) {
        String[][] rawBookings = {
            {"Divya", "Chennai"},
            {"", "Bangalore"},
            {"Ravi123", "Pune"},
            {"Divya", "Chennai"},
            {" ", " "}
        };
        processBatch(rawBookings);
    }
}