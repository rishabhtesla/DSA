package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Determine whether a tree is a mirror of itself around its root.
 * EXAMPLE: [1,2,2,3,4,4,3] -> true.
 * CONSTRAINTS: 0 <= nodes <= 1000.
 * LOGIC / ALGORITHM:
 * 1. Compare left subtree with the opposite right subtree recursively.
 * 2. Combine the child results while visiting each node once, carrying the needed value toward the root.
 * 3. Return the root/result assembled from the completed subtrees.
 * INVARIANT: Every completed subtree already satisfies its property, and combining child results preserves correctness.
 * <p>
 * COMPLEXITY: O(n) time and O(h) auxiliary space, where h is tree height.
 * DRY RUN: Main input [1,2,2,3,4,4,3] -> true.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P84_SymmetricTree {
    public static boolean isSymmetric(TreeNode r) {
        return r == null || same(r.left, r.right);
    }

    static boolean same(TreeNode a, TreeNode b) {
        return a == null || b == null ? a == b : a.val == b.val && same(a.left, b.right) && same(a.right, b.left);
    }

    public static void main(String[] args) {
        System.out.println(isSymmetric(new TreeNode(1)));
    }
}
