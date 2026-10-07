package DSA.Coding.DSABasic;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Shared Definition for Binary Tree Node.
 */


/**
 * PROBLEM STATEMENT:
 * A binary tree is univalued if every node in the tree has the same value [00:00:43].
 * Given the root of a binary tree, return true if the given tree is univalued, or false otherwise [00:01:08].
 *
 * EXAMPLES & EXPLANATION:
 * Example 1: root = [1, 1, 1, 1, 1, null, 1] [00:01:31]
 * - Root val = 1.
 * - Every node in left and right subtrees has value 1.
 * - Result: true
 *
 * Example 2: root = [2, 2, 2, 5, 2] (From video explanation [00:03:36, 00:09:50])
 * - Root val = 2.
 * - Subtree check encounters Node(5).
 * - Node(5).val (5) != 2 -> helper returns false [00:08:49].
 * - Result: false [00:10:21].
 *
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Recursive DFS Helper Verification):
 * • Edge Cases:
 *   - `if (root == null) return true` [00:04:55].
 * • Helper Method Parameters:
 *   - `helper(TreeNode root, int val)` accepts current node and expected root value [00:04:37].
 * • Condition Boundaries:
 *   - Base condition 1 (null node): `if (root == null) return true` [00:04:55].
 *   - Base condition 2 (mismatch value): `if (root.val != val) return false` [00:06:00, 00:06:06].
 *   - Subtree recursion: `leftAns = helper(root.left, val)`, `rightAns = helper(root.right, val)` [00:05:26, 00:05:45].
 *   - Combination: `return leftAns && rightAns` [00:06:33].
 * • Operational Steps:
 *   1. Initiate call `helper(root, root.val)` from main function [00:04:17].
 *   2. If current `root == null`, return `true` [00:04:55].
 *   3. If `root.val != val`, return `false` [00:06:06].
 *   4. Traverse left child: `leftAns = helper(root.left, val)` [00:05:26].
 *   5. Traverse right child: `rightAns = helper(root.right, val)` [00:05:45].
 *   6. Return logical AND of left and right results (`leftAns && rightAns`) [00:06:33].
 * • Time Complexity: O(N) - Visits each node at most once during depth-first traversal.
 * • Space Complexity: O(H) - Call stack memory proportional to tree height H (O(N) worst-case skewed, O(log N) balanced).
 * • LOGIC BEHIND THIS APPROACH:
 *   Passing `root.val` down as a constant scalar parameter guarantees every node is checked against the same anchor reference [00:02:35, 00:04:44]. 
 *   The short-circuiting logical AND (`&&`) ensures that if any single node fails the match test, `false` propagates back up to the caller [00:06:33, 00:09:50].
 *
 * ---
 * VISUAL DRY RUN (APPROACH 1: Recursive Helper - root = [2, 2, 2, 2, 2]):
 * Frame 1: helper(Node(2_root), val=2)
 * - 2 == 2 (no mismatch).
 * - Call left: helper(Node(2_left), val=2)
 *   - 2 == 2. Left/right children are null -> return true && true = true [00:11:59].
 * - Call right: helper(Node(2_right), val=2)
 *   - 2 == 2. Left/right children are null -> return true && true = true [00:13:39].
 * - Frame 1 returns leftAns(true) && rightAns(true) -> true [00:13:54].
 *
 * ---
 * VISUAL DRY RUN (APPROACH 2: Parent-Child Comparative Direct Recursion):
 * isUnivalTree(root):
 * - If root == null -> true
 * - If root.left != null && root.left.val != root.val -> false
 * - If root.right != null && root.right.val != root.val -> false
 * - Return isUnivalTree(root.left) && isUnivalTree(root.right)
 */
public class UnivaluedBinaryTree {

    // APPROACH 1: Recursive DFS Helper Verification (Anchor Strategy)
    public static boolean isUnivalTreeOptimal(TreeNode root) {
        if (root == null) return true;
        // Delegate to helper function passing root value as target reference [00:04:17]
        return helper(root, root.val);
    }

    private static boolean helper(TreeNode root, int val) {
        // Base Case 1: Null node is valid [00:04:55]
        if (root == null) {
            return true;
        }

        // Base Case 2: Value mismatch detected [00:06:00]
        if (root.val != val) {
            return false; // Not a univalued tree [00:06:06]
        }

        // Recursively verify left and right subtrees [00:05:26, 00:05:45]
        boolean leftAns = helper(root.left, val);
        boolean rightAns = helper(root.right, val);

        // Both subtrees must be univalued [00:06:33]
        return leftAns && rightAns;
    }

    // APPROACH 2: Parent-Child Comparative Direct Recursion (Without Helper)
    // Directly compares each node's value with its left and right children.
    public static boolean isUnivalTreeDirect(TreeNode root) {
        if (root == null) return true;

        if (root.left != null && root.left.val != root.val) {
            return false;
        }

        if (root.right != null && root.right.val != root.val) {
            return false;
        }

        return isUnivalTreeDirect(root.left) && isUnivalTreeDirect(root.right);
    }

    // Helper method to build a binary tree from level-order Integer array representation
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

        // --- TEST CASE 1 (Valid Univalued Tree [1, 1, 1, 1, 1, null, 1]) ---
        TreeNode tree1_1 = buildTree(new Integer[]{1, 1, 1, 1, 1, null, 1});
        TreeNode tree1_2 = buildTree(new Integer[]{1, 1, 1, 1, 1, null, 1});

        boolean res1_1 = isUnivalTreeOptimal(tree1_1);
        boolean res1_2 = isUnivalTreeDirect(tree1_2);

        System.out.println("Test Case 1: root = [1, 1, 1, 1, 1, null, 1]");
        System.out.println("Approach 1 (DFS Helper) Result: " + res1_1);
        System.out.println("Approach 2 (Direct Ref) Result: " + res1_2);
        boolean check1 = res1_1 && res1_2;
        System.out.println("Verification: " + (check1 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (Invalid Tree with Node 5 [2, 2, 2, 5, 2] from Video Explanation) ---
        TreeNode tree2_1 = buildTree(new Integer[]{2, 2, 2, 5, 2});
        TreeNode tree2_2 = buildTree(new Integer[]{2, 2, 2, 5, 2});

        boolean res2_1 = isUnivalTreeOptimal(tree2_1);
        boolean res2_2 = isUnivalTreeDirect(tree2_2);

        System.out.println("Test Case 2: root = [2, 2, 2, 5, 2]");
        System.out.println("Approach 1 (DFS Helper) Result: " + res2_1);
        System.out.println("Approach 2 (Direct Ref) Result: " + res2_2);
        boolean check2 = !res2_1 && !res2_2;
        System.out.println("Verification: " + (check2 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}