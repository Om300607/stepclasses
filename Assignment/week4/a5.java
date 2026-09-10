class DeliveryAccount {
    String studentId;
    double orderValue;

    static double minimumSurgePercent;

    static {
        minimumSurgePercent = 1.0;
    }

    public DeliveryAccount(String studentId, double orderValue) {
        this.studentId = studentId;
        this.orderValue = orderValue;
    }

    public DeliveryAccount(String studentId) {
        this(studentId, 0.0);
    }

    public final double calculateSurgeFee(int delayMinutes) {
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

class PremiumDeliveryAccount extends DeliveryAccount {
    public PremiumDeliveryAccount(String studentId, double orderValue) {
        super(studentId, orderValue);
    }

    public PremiumDeliveryAccount(String studentId) {
        super(studentId);
    }
}

public class a5 {

    public static double processAccount(DeliveryAccount account, double amount, int delayMinutes) {
        if (account == null) {
            return 0.0;
        }

        account.orderValue = amount;
        double surgeFee = account.calculateSurgeFee(delayMinutes);

        if (account instanceof PremiumDeliveryAccount) {
            surgeFee = surgeFee * 0.5;
        }

        return surgeFee;
    }

    public static void processBatch(DeliveryAccount[] accounts, double[] amounts, int[] delayMinutesArray) {
        if (accounts == null || amounts == null || delayMinutesArray == null) {
            throw new IllegalArgumentException("Input arrays must not be null");
        }

        if (accounts.length != amounts.length || accounts.length != delayMinutesArray.length) {
            throw new IllegalArgumentException("Array length mismatch: batch aborted for data integrity");
        }

        int processedCount = 0;
        int nullSkippedCount = 0;
        int premiumCount = 0;
        int regularCount = 0;
        double grandTotalSurgeFees = 0.0;

        for (int i = 0; i < accounts.length; i++) {
            DeliveryAccount account = accounts[i];
            if (account == null) {
                nullSkippedCount++;
                continue;
            }

            processedCount++;
            if (account instanceof PremiumDeliveryAccount) {
                premiumCount++;
            } else {
                regularCount++;
            }

            double fee = processAccount(account, amounts[i], delayMinutesArray[i]);
            grandTotalSurgeFees += fee;
        }

        System.out.println(processedCount + " processed | " +
                           nullSkippedCount + " null skipped | " +
                           premiumCount + " premium | " +
                           regularCount + " regular | grand total surge fees = Rs " +
                           grandTotalSurgeFees);
    }

    public static void main(String[] args) {
        DeliveryAccount[] accounts = {
            new PremiumDeliveryAccount("STU001", 500),
            null,
            new DeliveryAccount("STU002", 300)
        };

        double[] amounts = {500, 400, 300};
        int[] delayMinutesArray = {10, 5, 0};

        processBatch(accounts, amounts, delayMinutesArray);
    }
}