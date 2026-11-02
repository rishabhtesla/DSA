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
 * Example from main: [3,9,20,null,null,15,7] -> true.
 * 1. Start at the root and apply the base case if the node is null.
 * 2. Recursively solve the left and right child subtrees.
 * 3. Combine the child results at the current node.
 * 4. Return the result assembled at the root.
 *
 * Complexity
 * O(n) time and O(h) auxiliary space, where h is tree height.
 */
public class P83_BalancedBinaryTree {
    public static boolean isBalanced(TreeNode r) {
        return h(r) >= 0;
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
