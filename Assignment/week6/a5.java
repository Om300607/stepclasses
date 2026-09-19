// Problem 5: Race-Wide Bib Issuance, Discount Codes & Nightly Settlement Engine

public class a5 {

    static class RaceEntry {
        private static int bibCounter = 0;      // shared across every entry and subclass

        private final String entryCode;         // final: cannot be reassigned or set from outside
        private final String bibNumber;
        private final double entryFee;
        private double amountPaid;

        public RaceEntry(String bibNumber, double entryFee) {
            // Validate FIRST so a rejected construction never increments the counter
            if (bibNumber == null || bibNumber.trim().length() < 4) {
                throw new IllegalArgumentException("Invalid bib number: " + bibNumber);
            }
            this.bibNumber = bibNumber;
            this.entryFee = entryFee;
            bibCounter++;                                        // once per object
            this.entryCode = String.format("ENT-%04d", bibCounter);
        }

        public String getEntryCode() {
            return entryCode;
        }

        public String getBibNumber() {
            return bibNumber;
        }

        void pay(double amount) {
            amountPaid += amount;
        }

        // Mode-aware version reuses the flat version
        void pay(double amount, String mode) {
            System.out.println("Paying via " + mode);
            pay(amount);
        }

        double getBalanceDue() {
            return entryFee - amountPaid;
        }

        // Format: "M" + three digits + one uppercase letter, no regex
        static boolean isValidDiscountCode(String code) {
            if (code == null || code.length() != 5) {   // length first, before any charAt()
                return false;
            }
            if (code.charAt(0) != 'M') {
                return false;
            }
            for (int i = 1; i <= 3; i++) {
                if (!Character.isDigit(code.charAt(i))) {
                    return false;
                }
            }
            return Character.isUpperCase(code.charAt(4));
        }

        static int getBibCounter() {
            return bibCounter;
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

    // Never throws on a null entry
    static String settleNight(RaceEntry[] entries) {
        int processed = 0;
        int nullSkipped = 0;
        int relay = 0;
        int individual = 0;

        for (RaceEntry entry : entries) {
            if (entry == null) {
                nullSkipped++;
                continue;
            }
            processed++;
            if (entry instanceof RelayTeamEntry) {
                relay++;
            } else {
                individual++;
            }
        }
        return processed + " processed | " + nullSkipped + " null skipped | "
                + relay + " relay | " + individual + " individual";
    }

    public static void main(String[] args) {
        // Discount code checks
        System.out.println(RaceEntry.isValidDiscountCode("M123A"));   // true
        System.out.println(RaceEntry.isValidDiscountCode("M12A"));    // false
        System.out.println(RaceEntry.isValidDiscountCode("X123A"));   // false

        RunnerEntry r = new RunnerEntry("BIB2001", 80, "Open 10K");
        EliteRunnerEntry eliteEntry = new EliteRunnerEntry("BIB3001", 150, "Elite Full Marathon", 500);
        RelayTeamEntry relayEntry = new RelayTeamEntry("BIB4001", 300, 4);
        RaceEntry plain = new RaceEntry("BIB5001", 50);

        r.pay(10, "UPI");                                              // Paying via UPI

        System.out.println(RaceEntry.getBibCounter());                 // 4

        // A rejected construction must not increment the counter
        try {
            new RaceEntry("B1", 50);
        } catch (IllegalArgumentException e) {
            System.out.println("construction rejected");
        }
        System.out.println(RaceEntry.getBibCounter());                 // still 4

        System.out.println(settleNight(new RaceEntry[]{eliteEntry, null, relayEntry}));
        // 2 processed | 1 null skipped | 1 relay | 1 individual
    }
}