package DSA.Coding.DSABasic;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Shared Definition for Binary Tree Node.
 */


/**
 * PROBLEM STATEMENT:
 * Given the root of a binary tree, check whether it is a mirror of itself 
 * (i.e., symmetric around its center) [00:00:29, 00:00:36].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: root = [1, 2, 2, 3, 4, 4, 3] [00:00:47, 00:02:10]
 * - Root = 1
 * - Compare Subtree 1 (Left Child 2) and Subtree 2 (Right Child 2):
 *   - Both values match (2 == 2) [00:01:50].
 *   - Outer check: Subtree1.left (3) vs Subtree2.right (3) -> Match! [00:02:05]
 *   - Inner check: Subtree1.right (4) vs Subtree2.left (4) -> Match! [00:02:02]
 * - Result: true [00:02:59].
 * 
 * Example 2: root = [1, 2, 2, null, 3, null, 3] [00:02:20, 00:03:06]
 * - Outer check: Subtree1.left (null) vs Subtree2.right (3) -> Mismatch! [00:05:55]
 * - Result: false [00:03:06, 00:06:17].
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Two-Tree Mirror Equivalence Recursion):
 * • Edge Cases:
 *   - Base condition: `if (root == null) return true` [00:06:36].
 * • Helper Function Signature:
 *   - `helper(TreeNode r1, TreeNode r2)` checks if r1 and r2 are mirror reflections [00:07:06].
 * • Condition Boundaries:
 *   - Base condition 1 (Null check): `if (r1 == null || r2 == null) return r1 == r2` [00:08:00, 00:08:29].
 *   - Base condition 2 (Value mismatch): `if (r1.val != r2.val) return false` [00:09:48, 00:10:05].
 *   - Outer child mirror recursion: `ans1 = helper(r1.left, r2.right)` [00:10:50, 00:10:59].
 *   - Inner child mirror recursion: `ans2 = helper(r1.right, r2.left)` [00:11:36, 00:11:47].
 *   - Combination: `return ans1 && ans2` [00:11:53].
 * • Operational Steps:
 *   1. Handle null root check (`root == null` -> `true`) [00:06:36].
 *   2. Delegate to helper function passing left and right subtrees: `helper(root.left, root.right)` [00:07:28].
 *   3. If both `r1` and `r2` are null, return `true`; if only one is null, return `false` [00:08:29, 00:09:13].
 *   4. If `r1.val != r2.val`, return `false` [00:10:05].
 *   5. Recursively check outer boundary (`r1.left` vs `r2.right`) [00:10:59].
 *   6. Recursively check inner boundary (`r1.right` vs `r2.left`) [00:11:47].
 *   7. Return `ans1 && ans2` [00:11:53].
 * • Time Complexity: O(N) - Visits every tree node once.
 * • Space Complexity: O(H) - Call stack depth proportional to tree height H (O(log N) balanced, O(N) skewed).
 * • LOGIC BEHIND THIS APPROACH:
 *   Checking `r1 == r2` inside the null branch cleanly returns `true` when both are null and `false` when only one is null, eliminating redundant branching [00:08:29, 00:09:08]. 
 *   Reflecting traversal paths (`r1.left` against `r2.right` and `r1.right` against `r2.left`) mirrors optical inversion mathematically [00:01:17, 00:10:59].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 1: Mirror Recursion - root = [1, 2, 2, 3, 4, 4, 3]):
 * Call: helper(Node(2_left), Node(2_right)) [00:13:04]
 * - 2 == 2 (match).
 * - Outer Call: helper(2_left.left [3], 2_right.right [3]) [00:13:40]
 *   - 3 == 3. Children of both 3s are null -> returns true && true = true [00:14:26].
 * - Inner Call: helper(2_left.right [4], 2_right.left [4]) [00:15:34]
 *   - 4 == 4. Children of both 4s are null -> returns true && true = true [00:16:27].
 * - Return true && true -> true [00:16:36].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 2: Iterative Dual Queue Strategy):
 * Queue queue = new LinkedList();
 * queue.add(root.left); queue.add(root.right);
 * While queue is not empty:
 *   Pop node1, node2
 *   If node1 == null && node2 == null -> continue
 *   If node1 == null || node2 == null || node1.val != node2.val -> return false
 *   queue.add(node1.left);  queue.add(node2.right); // Outer pair
 *   queue.add(node1.right); queue.add(node2.left);  // Inner pair
 * Return true.
 */
public class SymmetricTree {

    // APPROACH 1: Two-Tree Mirror Equivalence Recursion (Anchor Strategy)
    public static boolean isSymmetricOptimal(TreeNode root) {
        // Base edge case: an empty root is trivially symmetric [00:06:36]
        if (root == null) {
            return true;
        }

        // Delegate symmetry check to helper function comparing left and right subtrees [00:07:28]
        return helper(root.left, root.right);
    }

    private static boolean helper(TreeNode r1, TreeNode r2) {
        // Base Case 1: If either node is null, both must be null to be symmetric [00:08:00, 00:08:29]
        if (r1 == null || r2 == null) {
            return r1 == r2; // True if both null, false if only one null [00:08:38, 00:09:08]
        }

        // Base Case 2: Node values must be identical [00:09:48]
        if (r1.val != r2.val) {
            return false; // Mismatch value violates mirror property [00:10:05]
        }

        // Cross-compare outer pair (r1's left vs r2's right) [00:10:50, 00:10:59]
        boolean ans1 = helper(r1.left, r2.right);

        // Cross-compare inner pair (r1's right vs r2's left) [00:11:36, 00:11:47]
        boolean ans2 = helper(r1.right, r2.left);

        // Both sub-reflections must be mirror images [00:11:53]
        return ans1 && ans2;
    }

    // APPROACH 2: Iterative Dual Queue Strategy (BFS Mirror Traversal)
    // Uses a Queue to compare symmetric pairs iteratively without call stack recursion.
    public static boolean isSymmetricIterative(TreeNode root) {
        if (root == null) return true;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root.left);
        queue.add(root.right);

        while (!queue.isEmpty()) {
            TreeNode n1 = queue.poll();
            TreeNode n2 = queue.poll();

            if (n1 == null && n2 == null) continue;
            if (n1 == null || n2 == null || n1.val != n2.val) return false;

            // Enqueue outer pair
            queue.add(n1.left);
            queue.add(n2.right);

            // Enqueue inner pair
            queue.add(n1.right);
            queue.add(n2.left);
        }

        return true;
    }

    // Helper method to build binary tree from level-order Integer array representation
    private static TreeNode buildTree(Integer[] values) {
        if (values == null || values.length == 0 || values[0] == null) return null;

        TreeNode root = new TreeNode(values[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        int i = 1;
        while (!queue.isEmpty() && i < values.length) {
            TreeNode curr = queue.poll();

            if (i < values.length && values[i] != null) {
                curr.left = new TreeNode(values[i]);
                queue.add(curr.left);
            }
            i++;

            if (i < values.length && values[i] != null) {
                curr.right = new TreeNode(values[i]);
                queue.add(curr.right);
            }
            i++;
        }

        return root;
    }

    // Comprehensive Verification Test Suite
    public static void main(String[] args) {
        System.out.println("================ RUNNING DSA TEST SUITE ================\n");

        // --- TEST CASE 1 (Symmetric Tree [1, 2, 2, 3, 4, 4, 3]) ---
        TreeNode tree1_1 = buildTree(new Integer[]{1, 2, 2, 3, 4, 4, 3});
        TreeNode tree1_2 = buildTree(new Integer[]{1, 2, 2, 3, 4, 4, 3});

        boolean res1_1 = isSymmetricOptimal(tree1_1);
        boolean res1_2 = isSymmetricIterative(tree1_2);

        System.out.println("Test Case 1: root = [1, 2, 2, 3, 4, 4, 3]");
        System.out.println("Approach 1 (Mirror DFS)       Result: " + res1_1);
        System.out.println("Approach 2 (Iterative Queue)  Result: " + res1_2);
        boolean check1 = res1_1 && res1_2;
        System.out.println("Verification: " + (check1 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (Asymmetric Tree [1, 2, 2, null, 3, null, 3]) ---
        TreeNode tree2_1 = buildTree(new Integer[]{1, 2, 2, null, 3, null, 3});
        TreeNode tree2_2 = buildTree(new Integer[]{1, 2, 2, null, 3, null, 3});

        boolean res2_1 = isSymmetricOptimal(tree2_1);
        boolean res2_2 = isSymmetricIterative(tree2_2);

        System.out.println("Test Case 2: root = [1, 2, 2, null, 3, null, 3]");
        System.out.println("Approach 1 (Mirror DFS)       Result: " + res2_1);
        System.out.println("Approach 2 (Iterative Queue)  Result: " + res2_2);
        boolean check2 = !res2_1 && !res2_2;
        System.out.println("Verification: " + (check2 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}