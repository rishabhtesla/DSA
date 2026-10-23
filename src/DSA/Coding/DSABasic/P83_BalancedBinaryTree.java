package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Determine whether every node's subtree heights differ by at most one.
 * EXAMPLE: [3,9,20,null,null,15,7] -> true.
 * CONSTRAINTS: 0 <= nodes <= 5000.
 * LOGIC / ALGORITHM:
 * 1. Return height, or -1 as soon as a subtree is unbalanced, avoiding repeated scans.
 * 2. Combine the child results while visiting each node once, carrying the needed value toward the root.
 * 3. Return the root/result assembled from the completed subtrees.
 * INVARIANT: Every completed subtree already satisfies its property, and combining child results preserves correctness.
 * <p>
 * COMPLEXITY: O(n) time and O(h) auxiliary space, where h is tree height.
 * DRY RUN: Main input [3,9,20,null,null,15,7] -> true.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
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
