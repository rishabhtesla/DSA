package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Find the lowest node whose subtree contains p and q in a BST.
 * EXAMPLE: BST [6,2,8,0,4,7,9], p=2,q=8 -> 6.
 * CONSTRAINTS: All values unique; p and q exist in the BST.
 * LOGIC / ALGORITHM:
 * 1. If both values are smaller go left; both larger go right; otherwise current node splits them.
 * 2. Combine the child results while visiting each node once, carrying the needed value toward the root.
 * 3. Return the root/result assembled from the completed subtrees.
 * INVARIANT: Every completed subtree already satisfies its property, and combining child results preserves correctness.
 * <p>
 * COMPLEXITY: O(n) time and O(h) auxiliary space, where h is tree height.
 * DRY RUN: Main input BST [6,2,8,0,4,7,9], p=2,q=8 -> 6.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P92_LowestCommonAncestorOfBST {
    public static TreeNode lowestCommonAncestor(TreeNode r, TreeNode p, TreeNode q) {
        while (r != null) {
            if (p.val < r.val && q.val < r.val) r = r.left;
            else if (p.val > r.val && q.val > r.val) r = r.right;
            else return r;
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(lowestCommonAncestor(new TreeNode(2, new TreeNode(1), new TreeNode(3)), new TreeNode(1), new TreeNode(3)).val);
    }
}
