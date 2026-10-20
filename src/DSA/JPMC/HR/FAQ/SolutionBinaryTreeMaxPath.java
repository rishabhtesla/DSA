package DSA.JPMC.HR.FAQ;

/**
 * ============================================================================
 * LEETCODE 124: Binary Tree Maximum Path Sum
 * ============================================================================
 *
 * ----------------------------------------------------------------------------
 * 1. PROBLEM STATEMENT
 * ----------------------------------------------------------------------------
 * A path in a binary tree is a sequence of nodes where each pair of adjacent
 * nodes in the sequence has an edge connecting them. A node can only appear in
 * the sequence at most once. Note that the path does NOT need to pass through
 * the root.
 *
 * The path sum of a path is the sum of the node's values in the path.
 *
 * Given the root of a binary tree, return the maximum path sum of any non-empty path.
 *
 * ----------------------------------------------------------------------------
 * 2. EXAMPLES
 * ----------------------------------------------------------------------------
 * Example 1:
 *        1
 *       / \
 *      2   3
 * Input: root = [1, 2, 3]
 * Output: 6
 * Explanation: The optimal path is 2 -> 1 -> 3 with path sum 2 + 1 + 3 = 6.
 *
 * Example 2:
 *       -10
 *       /  \
 *      9   20
 *         /  \
 *        15   7
 * Input: root = [-10, 9, 20, null, null, 15, 7]
 * Output: 42
 * Explanation: The optimal path is 15 -> 20 -> 7 with path sum 15 + 20 + 7 = 42.
 *
 * Example 3 (All Negatives):
 *       -3
 *       / \
 *     -2  -1
 * Input: root = [-3, -2, -1]
 * Output: -1
 * Explanation: The optimal path is the single node [-1]. Connecting nodes makes
 * the sum smaller.
 *
 * ----------------------------------------------------------------------------
 * 3. CONSTRAINTS
 * ----------------------------------------------------------------------------
 * - The number of nodes in the tree is in the range [1, 3 * 10^4].
 * - -1000 <= Node.val <= 1000
 *
 * ----------------------------------------------------------------------------
 * 4. INTUITION & THE "ARCH" VS. "BRANCH" RULE
 * ----------------------------------------------------------------------------
 * What constitutes a valid path?
 * - A path can never fork or split. It is a single continuous thread.
 * - For any node `u` in the tree, a path can pass through `u` in two roles:
 *
 *   Role A: `u` is the HIGHEST NODE (Peak / Turning Point / Arch)
 *           Left Subtree <--- u ---> Right Subtree
 *           Here, the path takes the best from the left child, the best from
 *           the right child, plus `u.val`.
 *           Arch Path Sum = u.val + leftGain + rightGain
 *           CRITICAL: This path CANNOT be extended upward to `u`'s parent,
 *           because doing so would create an invalid "T-junction" / fork.
 *
 *   Role B: `u` is an INTERMEDIATE NODE in a path extending to its parent
 *           Parent ---> u ---> (Left OR Right, but NOT both)
 *           To send a valid continuous path up to the parent, `u` can pick at
 *           most ONE child branch.
 *           Return Value to Parent = u.val + max(leftGain, rightGain)
 *
 * Handling Negatives:
 * - If a child branch contributes a negative sum, we choose NOT to include it.
 *   gain = max(0, recursiveCall(child))
 *
 * ----------------------------------------------------------------------------
 * 5. STEP-BY-STEP DRY RUN (Example 2)
 * ----------------------------------------------------------------------------
 * Tree:
 *          -10
 *         /   \
 *        9     20
 *             /  \
 *            15   7
 *
 * Initial state: globalMax = -INFINITY
 *
 * Step 1: Visit Node 9
 *   - leftGain  = max(0, dfs(null)) = 0
 *   - rightGain = max(0, dfs(null)) = 0
 *   - archSum   = 9 + 0 + 0 = 9
 *   - globalMax = max(-INF, 9) = 9
 *   - returns   : 9 + max(0, 0) = 9
 *
 * Step 2: Visit Node 15
 *   - leftGain  = max(0, dfs(null)) = 0
 *   - rightGain = max(0, dfs(null)) = 0
 *   - archSum   = 15 + 0 + 0 = 15
 *   - globalMax = max(9, 15) = 15
 *   - returns   : 15 + max(0, 0) = 15
 *
 * Step 3: Visit Node 7
 *   - leftGain  = max(0, dfs(null)) = 0
 *   - rightGain = max(0, dfs(null)) = 0
 *   - archSum   = 7 + 0 + 0 = 7
 *   - globalMax = max(15, 7) = 15
 *   - returns   : 7 + max(0, 0) = 7
 *
 * Step 4: Visit Node 20
 *   - leftGain  = max(0, dfs(15)) = 15
 *   - rightGain = max(0, dfs(7))  = 7
 *   - archSum   = 20 + 15 + 7 = 42
 *   - globalMax = max(15, 42) = 42  <-- Peak path identified: 15 -> 20 -> 7
 *   - returns   : 20 + max(15, 7) = 20 + 15 = 35
 *
 * Step 5: Visit Node -10 (Root)
 *   - leftGain  = max(0, dfs(9))  = 9
 *   - rightGain = max(0, dfs(20)) = 35
 *   - archSum   = -10 + 9 + 35 = 34
 *   - globalMax = max(42, 34) = 42
 *   - returns   : -10 + max(9, 35) = -10 + 35 = 25
 *
 * Output: 42
 *
 * ----------------------------------------------------------------------------
 * 6. COMPLEXITY ANALYSIS
 * ----------------------------------------------------------------------------
 * - Time Complexity:  O(N)
 *   Every node in the binary tree is visited exactly once via Post-Order DFS.
 * - Space Complexity: O(H)
 *   H is the height of the tree. In the worst-case (skewed tree), the recursion
 *   stack consumes O(N). In a balanced tree, O(log N).
 * ============================================================================
 */
public class SolutionBinaryTreeMaxPath {

    /**
     * Definition for a binary tree node.
     */
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * Main API method.
     * Uses a single-element array to track global state safely across recursion frames.
     */
    public int maxPathSum(TreeNode root) {
        int[] max = new int[] { Integer.MIN_VALUE };
        postOrderGain(root, max);
        return max[0];
    }

    /**
     * Post-order traversal to compute the maximum contribution each branch can offer.
     *
     * @param node Current tree node being evaluated
     * @param max  Wrapper holding the overall maximum path sum found so far
     * @return     Maximum path sum of a single unbranched path continuing to parent
     */
    private int postOrderGain(TreeNode node, int[] max) {
        if (node == null) {
            return 0;
        }

        // Compute positive gains only (prune negative branches with Math.max(0, ...))
        int leftGain = Math.max(0, postOrderGain(node.left, max));
        int rightGain = Math.max(0, postOrderGain(node.right, max));

        // Evaluate the full path where 'node' is the peak / turning point
        int currentArchPathSum = node.val + leftGain + rightGain;
        max[0] = Math.max(max[0], currentArchPathSum);

        // Return the single strongest branch extending upwards to the parent
        return node.val + Math.max(leftGain, rightGain);
    }

    // Driver method for local testing
    public static void main(String[] args) {
        SolutionBinaryTreeMaxPath solver = new SolutionBinaryTreeMaxPath();

        // Build Example 2: [-10, 9, 20, null, null, 15, 7]
        TreeNode root = new TreeNode(-10);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);

        int result = solver.maxPathSum(root);
        System.out.println("Max Path Sum: " + result); // Expected output: 42
    }
}