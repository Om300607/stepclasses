final class BoardingPenaltyCalculator {
    private final double minimumPenaltyPercent;

    public BoardingPenaltyCalculator(double minimumPenaltyPercent) {
        this.minimumPenaltyPercent = minimumPenaltyPercent;
    }

    public final double calculatePenalty(double ticketFare, int minutesLate) {
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

public class a4 {
    public static void main(String[] args) {
        BoardingPenaltyCalculator calc = new BoardingPenaltyCalculator(1.0);
        System.out.println("Rs " + calc.calculatePenalty(1000, 0));
        System.out.println("Rs " + calc.calculatePenalty(1000, 1));
        System.out.println("Rs " + calc.calculatePenalty(1000, 16));
    }
}