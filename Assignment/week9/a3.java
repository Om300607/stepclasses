import java.util.*;

public class a3 {
    public static int countPeriods(int[] transactions, long k) {
        Map<Long, Integer> seen = new HashMap<>();
        seen.put(0L, 1);
        long sum = 0;
        int count = 0;
        for (int t : transactions) {
            sum += t;
            count += seen.getOrDefault(sum - k, 0);
            seen.put(sum, seen.getOrDefault(sum, 0) + 1);
        }
        return count;
    }

    public static void main(String[] args) {
        System.out.println(countPeriods(new int[]{3, 4, -7, 1, 3, 3, 1, -4}, 7));
        System.out.println(countPeriods(new int[]{1, 2, 3}, 10));
    }
}