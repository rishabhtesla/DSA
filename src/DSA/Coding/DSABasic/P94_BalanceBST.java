package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Rebuild an unbalanced BST into a height-balanced BST with the same keys.
 * EXAMPLE: BST 1->2->3 -> balanced root 2.
 * CONSTRAINTS: 1 <= nodes <= 10^4; input is a valid BST.
 * LOGIC / ALGORITHM:
 * 1. Inorder produces sorted keys; midpoint recursion rebuilds a balanced tree.
 * 2. Combine the child results while visiting each node once, carrying the needed value toward the root.
 * 3. Return the root/result assembled from the completed subtrees.
 * INVARIANT: Every completed subtree already satisfies its property, and combining child results preserves correctness.
 * <p>
 * COMPLEXITY: O(n) time and O(h) auxiliary space, where h is tree height.
 * DRY RUN: Main input BST 1->2->3 -> balanced root 2.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P94_BalanceBST {
    public static TreeNode balanceBST(TreeNode r) {
        List<Integer> a = new ArrayList<>();
        in(r, a);
        return b(a, 0, a.size() - 1);
    }

    static void in(TreeNode n, List<Integer> a) {
        if (n == null) return;
        in(n.left, a);
        a.add(n.val);
        in(n.right, a);
    }

    static TreeNode b(List<Integer> a, int l, int r) {
        if (l > r) return null;
        int m = (l + r) / 2;
        return new TreeNode(a.get(m), b(a, l, m - 1), b(a, m + 1, r));
    }

    public static void main(String[] args) {
        System.out.println(balanceBST(new TreeNode(1)).val);
    }
}
