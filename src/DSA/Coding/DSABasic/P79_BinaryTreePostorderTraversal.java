package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Return binary-tree values in left-right-root order.
 * EXAMPLE: tree [1,null,2,3] -> [3,2,1].
 * CONSTRAINTS: 0 <= nodes <= 100; values fit 32-bit integers.
 * LOGIC / ALGORITHM:
 * 1. Recursively process both children before recording each root.
 * 2. Combine the child results while visiting each node once, carrying the needed value toward the root.
 * 3. Return the root/result assembled from the completed subtrees.
 * INVARIANT: Every completed subtree already satisfies its property, and combining child results preserves correctness.
 * <p>
 * COMPLEXITY: O(n) time and O(h) auxiliary space, where h is tree height.
 * DRY RUN: Main input tree [1,null,2,3] -> [3,2,1].; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P79_BinaryTreePostorderTraversal {
    public static List<Integer> postorderTraversal(TreeNode r) {
        List<Integer> o = new ArrayList<>();
        post(r, o);
        return o;
    }

    static void post(TreeNode n, List<Integer> o) {
        if (n == null) return;
        post(n.left, o);
        post(n.right, o);
        o.add(n.val);
    }

    public static void main(String[] args) {
        System.out.println(postorderTraversal(new TreeNode(1)));
    }
}
