import java.util.*;

public class a2 {
    public static int[] longestStreak(int[] costs, long budget) {
        int left = 0;
        long sum = 0;
        int bestLen = 0;
        int bestStart = -1;
        for (int right = 0; right < costs.length; right++) {
            sum += costs[right];
            while (sum > budget && left <= right) {
                sum -= costs[left];
                left++;
            }
            int len = right - left + 1;
            if (len > bestLen) {
                bestLen = len;
                bestStart = left;
            }
        }
        return new int[]{bestLen, bestStart};
    }

    public static void main(String[] args) {
        int[] r1 = longestStreak(new int[]{4, 2, 1, 7, 3, 1, 2, 1, 5}, 8);
        System.out.println("(" + r1[0] + ", " + r1[1] + ")");
        int[] r2 = longestStreak(new int[]{9, 10}, 8);
        System.out.println("(" + r2[0] + ", " + r2[1] + ")");
    }
}