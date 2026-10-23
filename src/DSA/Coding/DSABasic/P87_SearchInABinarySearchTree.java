package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Return the subtree rooted at value val in a BST.
 * EXAMPLE: BST [4,2,7,1,3], val=2 -> subtree rooted at 2.
 * CONSTRAINTS: 1 <= nodes <= 5000; values unique and BST-ordered.
 * LOGIC / ALGORITHM:
 * 1. Compare val with current node and move left or right until found or null.
 * 2. Combine the child results while visiting each node once, carrying the needed value toward the root.
 * 3. Return the root/result assembled from the completed subtrees.
 * INVARIANT: Every completed subtree already satisfies its property, and combining child results preserves correctness.
 * <p>
 * COMPLEXITY: O(n) time and O(h) auxiliary space, where h is tree height.
 * DRY RUN: Main input BST [4,2,7,1,3], val=2 -> subtree rooted at 2.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P87_SearchInABinarySearchTree {
    public static TreeNode searchBST(TreeNode r, int v) {
        while (r != null && r.val != v) r = v < r.val ? r.left : r.right;
        return r;
    }

    public static void main(String[] args) {
        System.out.println(searchBST(new TreeNode(2), 2).val);
    }
}
