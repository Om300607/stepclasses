import java.util.*;

public class a5 {
    public static List<Integer> auditRoute(int[][] grid) {
        List<Integer> route = new ArrayList<>();
        int top = 0;
        int bottom = grid.length - 1;
        int left = 0;
        int right = grid[0].length - 1;
        while (top <= bottom && left <= right) {
            for (int j = left; j <= right; j++) {
                route.add(grid[top][j]);
            }
            top++;
            for (int i = top; i <= bottom; i++) {
                route.add(grid[i][right]);
            }
            right--;
            if (top <= bottom) {
                for (int j = right; j >= left; j--) {
                    route.add(grid[bottom][j]);
                }
                bottom--;
            }
            if (left <= right) {
                for (int i = bottom; i >= top; i--) {
                    route.add(grid[i][left]);
                }
                left++;
            }
        }
        return route;
    }

    public static void main(String[] args) {
        int[][] grid = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12}
        };
        System.out.println(auditRoute(grid));
    }
}