package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Return binary-tree values in left-root-right order.
 * EXAMPLE: tree [1,null,2,3] -> [1,3,2].
 * CONSTRAINTS: 0 <= nodes <= 100; values fit 32-bit integers.
 * LOGIC / ALGORITHM:
 * 1. Recursively finish the left subtree, record root, then finish right subtree.
 * 2. Combine the child results while visiting each node once, carrying the needed value toward the root.
 * 3. Return the root/result assembled from the completed subtrees.
 * INVARIANT: Every completed subtree already satisfies its property, and combining child results preserves correctness.
 * <p>
 * COMPLEXITY: O(n) time and O(h) auxiliary space, where h is tree height.
 * DRY RUN: Main input tree [1,null,2,3] -> [1,3,2].; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P78_BinaryTreeInorderTraversal {
    public static List<Integer> inorderTraversal(TreeNode r) {
        List<Integer> o = new ArrayList<>();
        in(r, o);
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
