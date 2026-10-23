package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Delete a key from a BST and preserve its ordering.
 * EXAMPLE: BST [5,3,6,2,4,null,7], delete 3 -> valid BST rooted at 5.
 * CONSTRAINTS: 0 <= nodes <= 5000; keys are unique.
 * LOGIC / ALGORITHM:
 * 1. Find key; replace a two-child node with its right-subtree successor, then delete successor.
 * 2. Combine the child results while visiting each node once, carrying the needed value toward the root.
 * 3. Return the root/result assembled from the completed subtrees.
 * INVARIANT: Every completed subtree already satisfies its property, and combining child results preserves correctness.
 * <p>
 * COMPLEXITY: O(n) time and O(h) auxiliary space, where h is tree height.
 * DRY RUN: Main input BST [5,3,6,2,4,null,7], delete 3 -> valid BST rooted at 5.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P89_DeleteNodeInABST {
    public static TreeNode deleteNode(TreeNode r, int k) {
        if (r == null) return null;
        if (k < r.val) r.left = deleteNode(r.left, k);
        else if (k > r.val) r.right = deleteNode(r.right, k);
        else {
            if (r.left == null) return r.right;
            if (r.right == null) return r.left;
            TreeNode s = r.right;
            while (s.left != null) s = s.left;
            r.val = s.val;
            r.right = deleteNode(r.right, s.val);
        }
        return r;
    }

    public static void main(String[] args) {
        System.out.println(deleteNode(new TreeNode(1), 1));
    }
}
