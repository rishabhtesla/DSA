package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P78: BINARY TREE INORDER TRAVERSAL
 *
 * Question
 * Return binary-tree values in left-root-right order.
 *
 * Example
 * tree [1,null,2,3] -> [1,3,2].
 *
 * Constraints
 * 0 <= nodes <= 100; values fit 32-bit integers.
 *
 * Approach used by edSlash
 * 1. Recursively finish the left subtree, record root, then finish right subtree.
 * 2. Solve the same problem for the left and right subtrees, then combine their results at the current node.
 * 3. The base case for a null node stops the recursion and supplies the identity value for the combination.
 *
 * Dry run
 *   Inorder visual for tree [1,null,2,3]:
 *     go left from 1 (null), then visit 1
 *     go right to 2, visit its left child 3 first
 *     visit 2 last
 *     result: [1,3,2]
 *
 * Complexity
 * O(n) time and O(h) auxiliary space, where h is tree height.
 */
public class P78_BinaryTreeInorderTraversal {
    public static List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> o = new ArrayList<>();
        in(root, o);
        return o;
    }

    static void in(TreeNode n, List<Integer> o) {
        if (n == null) return;
        in(n.left, o);
        o.add(n.val);
        in(n.right, o);
    }

    public static void main(String[] args) {
        System.out.println(inorderTraversal(new TreeNode(1)));
    }
}
