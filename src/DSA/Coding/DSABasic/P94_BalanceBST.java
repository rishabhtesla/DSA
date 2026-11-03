package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P94: BALANCE BST
 *
 * Question
 * Rebuild an unbalanced BST into a height-balanced BST with the same keys.
 *
 * Example
 * BST 1->2->3 -> balanced root 2.
 *
 * Constraints
 * 1 <= nodes <= 10^4; input is a valid BST.
 *
 * Approach used by edSlash
 * 1. Inorder produces sorted keys; midpoint recursion rebuilds a balanced tree.
 * 2. Solve the same problem for the left and right subtrees, then combine their results at the current node.
 * 3. The base case for a null node stops the recursion and supplies the identity value for the combination.
 *
 * Dry run
 *   Balance-BST visual for 1->2->3:
 *     inorder values are [1,2,3]
 *     choose middle value 2 as the new root
 *     attach 1 to the left and 3 to the right
 *     return balanced root 2
 *
 * Complexity
 * O(n) time and O(h) auxiliary space, where h is tree height.
 */
public class P94_BalanceBST {
    public static TreeNode balanceBST(TreeNode root) {
        List<Integer> a = new ArrayList<>();
        in(root, a);
        return b(a, 0, a.size() - 1);
    }

    static void in(TreeNode n, List<Integer> a) {
        if (n == null) return;
        in(n.left, a);
        a.add(n.val);
        in(n.right, a);
    }

    static TreeNode b(List<Integer> a, int l, int root) {
        if (l > root) return null;
        int m = (l + root) / 2;
        return new TreeNode(a.get(m), b(a, l, m - 1), b(a, m + 1, root));
    }

    public static void main(String[] args) {
        System.out.println(balanceBST(new TreeNode(1)).val);
    }
}
