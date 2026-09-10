class FareSplitter {
    String tripId;
    double totalFare;
    int passengerCount;

    public FareSplitter(String tripId, double totalFare, int passengerCount) {
        if (totalFare < 0 || passengerCount <= 0) {
            throw new IllegalArgumentException();
        }
        this.tripId = tripId;
        this.totalFare = totalFare;
        this.passengerCount = passengerCount;
    }

    public FareSplitter(String tripId, double totalFare) {
        this(tripId, totalFare, 2);
    }

    public FareSplitter(String tripId) {
        this(tripId, 0.0, 2);
    }

    public double[] fareBreakdown() {
        double[] breakdown = new double[passengerCount];
        long totalCents = Math.round(totalFare * 100.0);
        long baseShare = totalCents / passengerCount;
        long remainder = totalCents % passengerCount;

        for (int i = 0; i < passengerCount; i++) {
            long share = baseShare;
            if (i == passengerCount - 1) {
                share += remainder;
            }
            breakdown[i] = share / 100.0;
        }
        return breakdown;
    }

    public boolean isConfirmationOverdue(int confirmed, int expected) {
        return confirmed < expected;
    }
}

public class a2 {
    public static void main(String[] args) {
        FareSplitter fs1 = new FareSplitter("TRIP001", 100000, 3);
        double[] bd1 = fs1.fareBreakdown();
        System.out.print("[");
        for (int i = 0; i < bd1.length; i++) {
            System.out.print(bd1[i] + (i < bd1.length - 1 ? ", " : ""));
        }
        System.out.println("]");

        FareSplitter fs2 = new FareSplitter("TRIP003");
        double[] bd2 = fs2.fareBreakdown();
        System.out.print("[");
        for (int i = 0; i < bd2.length; i++) {
            System.out.print(bd2[i] + (i < bd2.length - 1 ? ", " : ""));
        }
        System.out.println("]");
    }
}