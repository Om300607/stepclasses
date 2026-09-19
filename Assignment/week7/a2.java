// Problem 2: One-Click Data Export

public class a2 {

    // One single counter shared by both unrelated classes
    private static int totalExports = 0;

    interface Exportable {
        String exportData();
    }

    static class ReportGenerator implements Exportable {
        private final String reportName;

        public ReportGenerator(String reportName) {
            this.reportName = reportName;
        }

        @Override
        public String exportData() {
            totalExports++;
            return "Exported report: " + reportName;
        }
    }

    static class UserProfile implements Exportable {
        private final String username;

        public UserProfile(String username) {
            this.username = username;
        }

        @Override
        public String exportData() {
            totalExports++;
            return "Exported profile: " + username;
        }
    }

    static int getTotalExports() {
        return totalExports;
    }

    static void exportAll(Exportable[] items) {
        for (Exportable item : items) {
            System.out.println(item.exportData());
        }
    }

    public static void main(String[] args) {
        ReportGenerator r = new ReportGenerator("Sales Q1");
        UserProfile u = new UserProfile("jane_doe");

        Exportable ref = r; // upcasting: ReportGenerator stored as the Exportable interface type

        exportAll(new Exportable[]{ref, u});
        System.out.println(getTotalExports());   // 2
    }
}