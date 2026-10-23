package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Insert a value into a BST while preserving ordering.
 * EXAMPLE: BST [4,2,7], insert 5 -> [4,2,7,null,null,5].
 * CONSTRAINTS: 0 <= nodes <= 5000; values are unique.
 * LOGIC / ALGORITHM:
 * 1. Recurse toward the proper null child according to comparison.
 * 2. Combine the child results while visiting each node once, carrying the needed value toward the root.
 * 3. Return the root/result assembled from the completed subtrees.
 * INVARIANT: Every completed subtree already satisfies its property, and combining child results preserves correctness.
 * <p>
 * COMPLEXITY: O(n) time and O(h) auxiliary space, where h is tree height.
 * DRY RUN: Main input BST [4,2,7], insert 5 -> [4,2,7,null,null,5].; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P88_InsertIntoABinarySearchTree {
    public static TreeNode insertIntoBST(TreeNode r, int v) {
        if (r == null) return new TreeNode(v);
        if (v < r.val) r.left = insertIntoBST(r.left, v);
        else r.right = insertIntoBST(r.right, v);
        return r;
    }

    public static void main(String[] args) {
        System.out.println(insertIntoBST(new TreeNode(2), 1).left.val);
    }
}
