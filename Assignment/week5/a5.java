import java.util.Arrays;

class LoanReceipt {
    private final String memberId;
    private final String[] bookIds;

    public LoanReceipt(String memberId, String[] bookIds) {
        if (memberId == null || bookIds == null) {
            throw new IllegalArgumentException("construction rejected");
        }

        for (String id : bookIds) {
            if (!isValidBookId(id)) {
                throw new IllegalArgumentException("construction rejected");
            }
        }

        this.memberId = memberId;
        this.bookIds = Arrays.copyOf(bookIds, bookIds.length);
    }

    private static boolean isValidBookId(String id) {
        if (id == null || id.length() != 6 || !id.startsWith("BK-")) {
            return false;
        }
        for (int i = 3; i < 6; i++) {
            if (!Character.isDigit(id.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public String getMemberId() {
        return memberId;
    }

    public String[] getBookIds() {
        return Arrays.copyOf(bookIds, bookIds.length);
    }

    public LoanReceipt withCorrectedBookId(int index, String newId) {
        if (index < 0 || index >= bookIds.length || !isValidBookId(newId)) {
            throw new IllegalArgumentException("Invalid replacement parameters");
        }
        String[] updatedIds = Arrays.copyOf(bookIds, bookIds.length);
        updatedIds[index] = newId;
        return new LoanReceipt(this.memberId, updatedIds);
    }
}

class ReferenceOnlyLoanReceipt extends LoanReceipt {
    private final String roomNumber;

    public ReferenceOnlyLoanReceipt(String memberId, String[] bookIds, String roomNumber) {
        super(memberId, bookIds);
        this.roomNumber = roomNumber;
    }

    public String getRoomNumber() {
        return roomNumber;
    }
}

public class a5 {
    private static int maxBatchCapacity;

    static {
        maxBatchCapacity = 5000;
    }

    public static String processNightlyCirculation(LoanReceipt[] receipts) {
        if (receipts == null) {
            return "0 processed | 0 null skipped | 0 reference-only | 0 regular";
        }

        int processed = 0;
        int nullSkipped = 0;
        int referenceOnly = 0;
        int regular = 0;

        for (LoanReceipt r : receipts) {
            if (r == null) {
                nullSkipped++;
            } else {
                processed++;
                if (r instanceof ReferenceOnlyLoanReceipt) {
                    referenceOnly++;
                } else {
                    regular++;
                }
            }
        }

        return processed + " processed | " + nullSkipped + " null skipped | " +
               referenceOnly + " reference-only | " + regular + " regular";
    }

    public static void main(String[] args) {
        try {
            new LoanReceipt("LIB-8841", new String[]{"BK-100", "bad"});
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        LoanReceipt r = new LoanReceipt("LIB-8841", new String[]{"BK-100", "BK-101"});
        String[] ids = r.getBookIds();
        ids[0] = "HACKED";
        System.out.println(r.getBookIds()[0]);

        LoanReceipt[] batch = new LoanReceipt[]{
            new ReferenceOnlyLoanReceipt("LIB-001", new String[]{"BK-200"}, "Reading Room 3"),
            null,
            new LoanReceipt("LIB-002", new String[]{"BK-201"})
        };
        System.out.println(processNightlyCirculation(batch));
    }
}