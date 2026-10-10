public class a2 {
    static int[] warehouseSummary(int[][] grid) {
        int total = 0;
        int max = Integer.MIN_VALUE;
        int maxRow = -1, maxCol = -1;
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                total += grid[r][c];
                if (grid[r][c] > max) {
                    max = grid[r][c];
                    maxRow = r;
                    maxCol = c;
                }
            }
        }
        return new int[]{total, maxRow, maxCol};
    }

    public static void main(String[] args) {
        int[][] grid = {
            {4, 5, 2},
            {7, 3, 8},
            {2, 12, 3}
        };
        int[] res = warehouseSummary(grid);
        System.out.println("Total = " + res[0] + ", MaxCoordinate = (" + res[1] + ", " + res[2] + ")");
    }
}