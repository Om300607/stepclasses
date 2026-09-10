class BusTicketAccount {
    String bookingId;
    double ticketFare;
    static double minimumPenaltyPercent;

    static {
        minimumPenaltyPercent = 1.0;
    }

    public BusTicketAccount(String bookingId, double ticketFare) {
        this.bookingId = bookingId;
        this.ticketFare = ticketFare;
    }

    public BusTicketAccount(String bookingId) {
        this(bookingId, 0.0);
    }

    public final double calculatePenalty(int minutesLate) {
        if (ticketFare < 0 || minutesLate < 0) {
            throw new IllegalArgumentException();
        }
        if (minutesLate == 0) {
            return 0.0;
        }

        int tier1 = Math.min(minutesLate, 5);
        int tier2 = Math.max(0, Math.min(minutesLate - 5, 10));
        int tier3 = Math.max(0, minutesLate - 15);

        double tieredPenalty = (tier1 * 0.005 * ticketFare) +
                               (tier2 * 0.010 * ticketFare) +
                               (tier3 * 0.020 * ticketFare);

        double floor = (minimumPenaltyPercent / 100.0) * ticketFare;

        return Math.max(tieredPenalty, floor);
    }
}

class SleeperAccount extends BusTicketAccount {
    public SleeperAccount(String bookingId, double ticketFare) {
        super(bookingId, ticketFare);
    }
}

public class a5 {
    public static double processAccount(BusTicketAccount account, double amount, int minutesLate) {
        if (account == null) return 0.0;
        
        double penalty = account.calculatePenalty(minutesLate);
        
        if (account instanceof SleeperAccount) {
            penalty *= 0.5;
        }
        return penalty;
    }

    public static void processBatch(BusTicketAccount[] accounts, double[] amounts, int[] minutesLateArray) {
        if (accounts.length != amounts.length || accounts.length != minutesLateArray.length) {
            throw new IllegalArgumentException();
        }

        int processed = 0;
        int nullSkipped = 0;
        int sleeper = 0;
        int regular = 0;
        double grandTotal = 0.0;

        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i] == null) {
                nullSkipped++;
            } else {
                processed++;
                if (accounts[i] instanceof SleeperAccount) {
                    sleeper++;
                } else {
                    regular++;
                }
                grandTotal += processAccount(accounts[i], amounts[i], minutesLateArray[i]);
            }
        }

        System.out.println(processed + " processed | " + nullSkipped + " null skipped | " + 
                           sleeper + " sleeper | " + regular + " regular | grand total penalties = " + grandTotal);
    }

    public static void main(String[] args) {
        BusTicketAccount[] accounts = {
            new SleeperAccount("BK001", 2000),
            null,
            new BusTicketAccount("BK002", 1200)
        };
        double[] amounts = {1200, 900, 700};
        int[] minutesLateArray = {10, 5, 0};

        processBatch(accounts, amounts, minutesLateArray);
    }
}