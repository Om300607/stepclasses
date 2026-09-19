// Problem 1: Race Entry Foundation & Batch Bib Validator

public class a1 {

    static class RaceEntry {
        private final String bibNumber;
        private final double entryFee;
        private double amountPaid;

        // The ONLY place the bib validation rule lives
        public RaceEntry(String bibNumber, double entryFee) {
            if (bibNumber == null || bibNumber.trim().length() < 4) {
                throw new IllegalArgumentException("Invalid bib number: " + bibNumber);
            }
            this.bibNumber = bibNumber;
            this.entryFee = entryFee;
            this.amountPaid = 0;
        }

        public String getBibNumber() {
            return bibNumber;
        }

        public double getEntryFee() {
            return entryFee;
        }

        void pay(double amount) {
            amountPaid += amount;
        }

        double getBalanceDue() {
            return entryFee - amountPaid;
        }
    }

    static class RunnerEntry extends RaceEntry {
        private final String category;

        public RunnerEntry(String bibNumber, double entryFee, String category) {
            super(bibNumber, entryFee);   // shared fields forwarded, not duplicated
            this.category = category;
        }

        public String getCategory() {
            return category;
        }
    }

    static String registerBatch(String[] bibNumbers, double entryFee) {
        int registered = 0;
        int rejected = 0;
        for (String bib : bibNumbers) {
            try {
                new RaceEntry(bib, entryFee);   // constructor does the validation
                registered++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }
        return "Registered: " + registered + " | Rejected: " + rejected;
    }

    public static void main(String[] args) {
        try {
            new RaceEntry("B1", 50);
        } catch (IllegalArgumentException e) {
            System.out.println("construction rejected");
        }

        RunnerEntry r = new RunnerEntry("BIB2001", 80, "Open 10K");
        r.pay(30);
        System.out.println(r.getBalanceDue());   // 50.0

        System.out.println(registerBatch(new String[]{"BIB1", "B1", "BIB2"}, 80));
        // Registered: 2 | Rejected: 1
    }
}