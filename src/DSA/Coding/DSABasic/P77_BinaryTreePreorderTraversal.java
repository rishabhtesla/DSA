package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Return binary-tree values in root-left-right order.
 * EXAMPLE: tree [1,null,2,3] -> [1,2,3].
 * CONSTRAINTS: 0 <= nodes <= 100; values fit 32-bit integers.
 * LOGIC / ALGORITHM:
 * 1. Visit root before recursively traversing left and right subtrees.
 * 2. Combine the child results while visiting each node once, carrying the needed value toward the root.
 * 3. Return the root/result assembled from the completed subtrees.
 * INVARIANT: Every completed subtree already satisfies its property, and combining child results preserves correctness.
 * <p>
 * COMPLEXITY: O(n) time and O(h) auxiliary space, where h is tree height.
 * DRY RUN: Main input tree [1,null,2,3] -> [1,2,3].; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P77_BinaryTreePreorderTraversal {
    public static List<Integer> preorderTraversal(TreeNode r) {
        List<Integer> o = new ArrayList<>();
        pre(r, o);
        return o;
    }

    static void pre(TreeNode n, List<Integer> o) {
        if (n == null) return;
        o.add(n.val);
        pre(n.left, o);
        pre(n.right, o);
    }

    public static void main(String[] args) {
        System.out.println(preorderTraversal(new TreeNode(1)));
    }
}
