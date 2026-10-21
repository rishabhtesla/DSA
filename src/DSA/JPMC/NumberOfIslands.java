package DSA.JPMC;

/**
 * PROBLEM: Number of Islands
 * Given an m x n 2D binary grid grid which represents a map of '1's (land) and '0's (water), 
 * return the number of islands. An island is surrounded by water and is formed by connecting 
 * adjacent lands horizontally or vertically.
 * 
 * EXAMPLE:
 * Input: grid = [
 *   ["1","1","0","0","0"],
 *   ["1","1","0","0","0"],
 *   ["0","0","1","0","0"],
 *   ["0","0","0","1","1"]
 * ]
 * Output: 3
 * 
 * LOGIC:
 * Iterate through every cell in the grid. When you find a '1' (land), you've found a new island.
 * Increment the island count. Then, trigger a DFS (or BFS) from that cell to find all connected 
 * '1's that belong to this island. To avoid recounting, mark the visited '1's as '0' (sink them).
 * 
 * DRY RUN:
 * - Scan hits (0,0) which is '1'. Island count = 1.
 * - DFS sinks (0,0), (0,1), (1,0), (1,1).
 * - Scan continues until it hits (2,2) which is '1'. Island count = 2.
 * - DFS sinks (2,2).
 * - Scan hits (3,3) which is '1'. Island count = 3.
 * - DFS sinks (3,3) and (3,4).
 * Result = 3.
 * 
 * COMPLEXITY: Time: O(M * N) where M is rows, N is cols. Space: O(M * N) worst case call stack.
 */
/**
 * Learning supplement: flood-fill visits each cell at most once, O(R*C) time
 * and O(R*C) worst-case stack/queue space. Examples:
 * {@code [["1","1","0"],["0","1","0"]] -> 1} and
 * {@code [["1","0"],["0","1"]] -> 2}. Invariant: marked land belongs to one
 * discovered island and is never revisited. The main DFS consumes each
 * connected neighbor before counting another island. Ask whether diagonals
 * connect and whether input mutation is acceptable.
 */
public class NumberOfIslands {
    
    public static int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) return 0;
        
        int numIslands = 0;
        int rows = grid.length;
        int cols = grid[0].length;
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == '1') {
                    numIslands++;
                    dfs(grid, i, j); // Sink the island
                }
            }
        }
        return numIslands;
    }
    
    private static void dfs(char[][] grid, int r, int c) {
        int rows = grid.length;
        int cols = grid[0].length;
        
        // Bounds check and water check
        if (r < 0 || c < 0 || r >= rows || c >= cols || grid[r][c] == '0') {
            return;
        }
        
        // Mark as visited by mutating the grid directly (sinking it)
        grid[r][c] = '0';
        
        // Traverse all 4 adjacent directions
        dfs(grid, r + 1, c); // down
        dfs(grid, r - 1, c); // up
        dfs(grid, r, c + 1); // right
        dfs(grid, r, c - 1); // left
    }

    public static void main(String[] args) {
        char[][] grid = {
            {'1','1','0','0','0'},
            {'1','1','0','0','0'},
            {'0','0','1','0','0'},
            {'0','0','0','1','1'}
        };
        System.out.println("Number of Islands: " + numIslands(grid)); // Output: 3
    }
}