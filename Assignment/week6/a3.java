import java.util.Arrays;

public class a3 {

    static class RaceEntry {
        private final String bibNumber;
        private final double entryFee;
        private double amountPaid;
        private double lateFeeTotal;

        // Private history: never exposed directly
        private final double[] lateFeeHistory = new double[10];
        private int lateFeeCount = 0;

        public RaceEntry(String bibNumber, double entryFee) {
            if (bibNumber == null || bibNumber.trim().length() < 4) {
                throw new IllegalArgumentException("Invalid bib number: " + bibNumber);
            }
            this.bibNumber = bibNumber;
            this.entryFee = entryFee;
        }

        public String getBibNumber() {
            return bibNumber;
        }

        void pay(double amount) {
            amountPaid += amount;
        }

        double getBalanceDue() {
            return entryFee - amountPaid + lateFeeTotal;
        }

        // Every penalty (base or overridden) is recorded here, in one place
        protected void applyLateFee(double amount) {
            if (lateFeeCount >= lateFeeHistory.length) {
                throw new IllegalStateException("Late fee limit reached");
            }
            lateFeeHistory[lateFeeCount++] = amount;
            lateFeeTotal += amount;
        }

        // Defensive copy: tampering with the result never touches the real history
        double[] getLateFeeHistory() {
            return Arrays.copyOf(lateFeeHistory, lateFeeCount);
        }
    }

    static class RunnerEntry extends RaceEntry {
        private final String category;

        public RunnerEntry(String bibNumber, double entryFee, String category) {
            super(bibNumber, entryFee);
            this.category = category;
        }

        public String getCategory() {
            return category;
        }

        @Override
        protected void applyLateFee(double amount) {
            super.applyLateFee(amount * 2);   // reuse parent's deduction + recording
        }
    }

    public static void main(String[] args) {
        RunnerEntry r = new RunnerEntry("BIB2001", 80, "Open 10K");
        r.pay(30);
        r.applyLateFee(20);
        System.out.println(r.getBalanceDue());                          // 90.0

        double[] history = r.getLateFeeHistory();
        System.out.println(Arrays.toString(history));                   // [40.0]
        history[0] = 999;                                               // tamper with the copy
        System.out.println(Arrays.toString(r.getLateFeeHistory()));     // [40.0]
    }
}