// Problem 2: Three Shapes of One Race Family

public class a2 {

    static class RaceEntry {
        private final String bibNumber;
        private final double entryFee;
        private double amountPaid;

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
            return entryFee - amountPaid;
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

        public String getCategory() {
            return category;
        }

        @Override
        String announce() {
            return "Runner Entry | Bib: " + getBibNumber() + " | Category: " + category
                    + " | Balance: " + getBalanceDue();
        }
    }

    // Multilevel: RaceEntry -> RunnerEntry -> EliteRunnerEntry
    static class EliteRunnerEntry extends RunnerEntry {
        private final double sponsorBonus;

        public EliteRunnerEntry(String bibNumber, double entryFee, String category, double sponsorBonus) {
            super(bibNumber, entryFee, category);
            this.sponsorBonus = sponsorBonus;
        }

        @Override
        String announce() {
            return "Elite Runner | Bib: " + getBibNumber() + " | Category: " + getCategory()
                    + " | Sponsor Bonus: " + sponsorBonus + " | Balance: " + getBalanceDue();
        }
    }

    // Hierarchical: independent branch, extends RaceEntry directly
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

    // instanceof only, no "type" field. Most specific class is checked first.
    static String classifyGeneration(RaceEntry entry) {
        if (entry instanceof EliteRunnerEntry) {
            return "Multilevel descendant (3 generations deep)";
        } else if (entry instanceof RelayTeamEntry) {
            return "Hierarchical sibling (independent branch)";
        } else if (entry instanceof RunnerEntry) {
            return "Single inheritance (2 generations deep)";
        }
        return "Base entry (1st generation)";
    }

    // Polymorphism: no type checks, each object runs its own getBalanceDue()
    static double getTotalBalanceDue(RaceEntry[] entries) {
        double total = 0;
        for (RaceEntry e : entries) {
            total += e.getBalanceDue();
        }
        return total;
    }

    public static void main(String[] args) {
        RunnerEntry runnerEntry = new RunnerEntry("BIB2001", 80, "Open 10K");
        EliteRunnerEntry eliteEntry = new EliteRunnerEntry("BIB3001", 150, "Elite Full Marathon", 500);
        RelayTeamEntry relayEntry = new RelayTeamEntry("BIB4001", 300, 4);

        System.out.println(runnerEntry.announce());
        System.out.println(eliteEntry.announce());
        System.out.println(relayEntry.announce());

        System.out.println(classifyGeneration(eliteEntry));
        System.out.println(classifyGeneration(relayEntry));

        System.out.println(getTotalBalanceDue(new RaceEntry[]{runnerEntry, eliteEntry, relayEntry})); // 530.0
    }
}