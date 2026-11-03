package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P83: BALANCED BINARY TREE
 *
 * Question
 * Determine whether every node's subtree heights differ by at most one.
 *
 * Example
 * [3,9,20,null,null,15,7] -> true.
 *
 * Constraints
 * 0 <= nodes <= 5000.
 *
 * Approach used by edSlash
 * 1. Return height, or -1 as soon as a subtree is unbalanced, avoiding repeated scans.
 * 2. Solve the same problem for the left and right subtrees, then combine their results at the current node.
 * 3. The base case for a null node stops the recursion and supplies the identity value for the combination.
 *
 * Dry run
 *   Balance visual for tree [3,9,20,null,null,15,7]:
 *     left subtree height = 1
 *     right subtree height = 2
 *     root difference = |1-2| = 1 <= 1
 *     every subtree satisfies the rule -> true
 *
 * Complexity
 * O(n) time and O(h) auxiliary space, where h is tree height.
 */
public class P83_BalancedBinaryTree {
    public static boolean isBalanced(TreeNode root) {
        return h(root) >= 0;
    }

    static int h(TreeNode n) {
        if (n == null) return 0;
        int a = h(n.left), b = h(n.right);
        return a < 0 || b < 0 || Math.abs(a - b) > 1 ? -1 : 1 + Math.max(a, b);
    }

    public static void main(String[] args) {
        System.out.println(isBalanced(new TreeNode(1)));
    }
}
