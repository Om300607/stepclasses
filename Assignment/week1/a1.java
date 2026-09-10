public class a1 {

    public static void checkDuplicateSeats(int[] seatNumbers) {
        boolean hasDuplicate = false;

        for (int i = 0; i < seatNumbers.length; i++) {
            boolean alreadyReported = false;
            for (int k = 0; k < i; k++) {
                if (seatNumbers[k] == seatNumbers[i]) {
                    alreadyReported = true;
                    break;
                }
            }
            if (alreadyReported) {
                continue;
            }

            for (int j = i + 1; j < seatNumbers.length; j++) {
                if (seatNumbers[i] == seatNumbers[j]) {
                    System.out.println("Duplicate Seat Number Found: " + seatNumbers[i]);
                    hasDuplicate = true;
                    break;
                }
            }
        }

        if (!hasDuplicate) {
            System.out.println("No Duplicate Seats Found");
        }
    }

    public static void main(String[] args) {
        int[] testSeats1 = {101, 102, 103, 102, 105};
        checkDuplicateSeats(testSeats1);

        int[] testSeats2 = {101, 102, 103, 104, 105};
        checkDuplicateSeats(testSeats2);
    }
}