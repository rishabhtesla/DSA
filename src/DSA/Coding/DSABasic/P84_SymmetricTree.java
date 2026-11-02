package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P84: SYMMETRIC TREE
 *
 * Question
 * Determine whether a tree is a mirror of itself around its root.
 *
 * Example
 * [1,2,2,3,4,4,3] -> true.
 *
 * Constraints
 * 0 <= nodes <= 1000.
 *
 * Approach used by edSlash
 * 1. Compare left subtree with the opposite right subtree recursively.
 * 2. Solve the same problem for the left and right subtrees, then combine their results at the current node.
 * 3. The base case for a null node stops the recursion and supplies the identity value for the combination.
 *
 * Dry run
 * Example from main: [1,2,2,3,4,4,3] -> true.
 * 1. Start at the root and apply the base case if the node is null.
 * 2. Recursively solve the left and right child subtrees.
 * 3. Combine the child results at the current node.
 * 4. Return the result assembled at the root.
 *
 * Complexity
 * O(n) time and O(h) auxiliary space, where h is tree height.
 */
public class P84_SymmetricTree {
    public static boolean isSymmetric(TreeNode root) {
        return root == null || same(root.left, root.right);
    }

    static boolean same(TreeNode a, TreeNode b) {
        return a == null || b == null ? a == b : a.val == b.val && same(a.left, b.right) && same(a.right, b.left);
    }

    public static void main(String[] args) {
        System.out.println(isSymmetric(new TreeNode(1)));
    }
}
