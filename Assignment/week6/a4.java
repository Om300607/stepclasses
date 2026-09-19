public class a4 {

    static class RaceEntry {
        private final String bibNumber;
        private final double entryFee;
        private double amountPaid;
        private double lateFeeTotal;

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

        protected void applyLateFee(double amount) {
            lateFeeTotal += amount;
        }

        String announce() {
            return "Race Entry | Bib: " + bibNumber + " | Balance: " + getBalanceDue();
        }
    }

    static class RunnerEntry extends RaceEntry {
        private final String category;

        public RunnerEntry(String bibNumber, double entryFee, String category) {
            super(bibNumber, entryFee);
            this.category = category;
        }

        @Override
        protected void applyLateFee(double amount) {
            super.applyLateFee(amount * 2);
        }

        @Override
        String announce() {
            return "Runner Entry | Bib: " + getBibNumber() + " | Category: " + category
                    + " | Balance: " + getBalanceDue();
        }
    }

    static class RelayTeamEntry extends RaceEntry {
        private final int teamSize;

        public RelayTeamEntry(String bibNumber, double entryFee, int teamSize) {
            super(bibNumber, entryFee);
            this.teamSize = teamSize;
        }

        public int getTeamSize() {
            return teamSize;
        }

        @Override
        String announce() {
            return "Relay Team | Bib: " + getBibNumber() + " | Team Size: " + teamSize
                    + " | Balance: " + getBalanceDue();
        }
    }

    static String announceAll(RaceEntry[] entries) {
        StringBuilder report = new StringBuilder();   // one builder for the whole loop
        for (RaceEntry entry : entries) {
            report.append(entry.announce());          // polymorphic call, no if-else on type

            // Guard first, then downcast, only inside the branch where the check passed
            if (entry instanceof RelayTeamEntry) {
                RelayTeamEntry relay = (RelayTeamEntry) entry;
                report.append(" [Team size via downcast: ").append(relay.getTeamSize()).append("]");
            }
            report.append(" | ");
        }
        return report.toString();
    }

    public static void main(String[] args) {
        RunnerEntry runnerEntry = new RunnerEntry("BIB2001", 80, "Open 10K");
        runnerEntry.pay(30);
        runnerEntry.applyLateFee(20);                       // balance becomes 90.0
        RelayTeamEntry relayEntry = new RelayTeamEntry("BIB4001", 300, 4);

        RaceEntry[] fleet = {runnerEntry, relayEntry};
        System.out.println(announceAll(fleet));

        // Compiles fine, but fails at runtime: plain is not really a RelayTeamEntry
        RaceEntry plain = new RaceEntry("BIB5001", 50);
        try {
            RelayTeamEntry bad = (RelayTeamEntry) plain;
        } catch (ClassCastException e) {
            System.out.println("ClassCastException at runtime");
        }
    }
}