final class SurgeFeeCalculator {
    private final double minimumSurgePercent;

    public SurgeFeeCalculator(double minimumSurgePercent) {
        this.minimumSurgePercent = minimumSurgePercent;
    }

    public final double calculateSurgeFee(double orderValue, int delayMinutes) {
        if (orderValue < 0 || delayMinutes < 0) {
            throw new IllegalArgumentException("orderValue and delayMinutes must be non-negative");
        }

        if (delayMinutes == 0) {
            return 0.0;
        }

        int tier1Minutes = Math.min(delayMinutes, 5);
        int tier2Minutes = Math.max(0, Math.min(delayMinutes - 5, 10));
        int tier3Minutes = Math.max(0, delayMinutes - 15);

        double tier1Fee = tier1Minutes * (0.005 * orderValue);
        double tier2Fee = tier2Minutes * (0.010 * orderValue);
        double tier3Fee = tier3Minutes * (0.020 * orderValue);

        double tieredFee = tier1Fee + tier2Fee + tier3Fee;
        double floorFee = (minimumSurgePercent / 100.0) * orderValue;

        return Math.max(tieredFee, floorFee);
    }
}

public class a4 {
    public static void main(String[] args) {
        SurgeFeeCalculator calc = new SurgeFeeCalculator(1.0);

        System.out.println("Rs " + calc.calculateSurgeFee(500, 0));
        System.out.println("Rs " + calc.calculateSurgeFee(500, 1));
        System.out.println("Rs " + calc.calculateSurgeFee(500, 16));
    }
}