public class a1 {
    static int[] pairSumSorted(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int sum = nums[left] + nums[right];
            if (sum == target) return new int[]{nums[left], nums[right]};
            if (sum < target) left++;
            else right--;
        }
        return null;
    }

    static void show(int[] nums, int target) {
        int[] r = pairSumSorted(nums, target);
        if (r == null) System.out.println("Not Found");
        else System.out.println("(" + r[0] + ", " + r[1] + ")");
    }

    public static void main(String[] args) {
        show(new int[]{-4, -1, -1, 0, 5, 6, 8, 9}, 4);
        show(new int[]{1, 2, 3}, 100);
    }
}