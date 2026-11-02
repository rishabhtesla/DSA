package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P88: INSERT INTO A BINARY SEARCH TREE
 *
 * Question
 * Insert a value into a BST while preserving ordering.
 *
 * Example
 * BST [4,2,7], insert 5 -> [4,2,7,null,null,5].
 *
 * Constraints
 * 0 <= nodes <= 5000; values are unique.
 *
 * Approach used by edSlash
 * 1. Recurse toward the proper null child according to comparison.
 * 2. Solve the same problem for the left and right subtrees, then combine their results at the current node.
 * 3. The base case for a null node stops the recursion and supplies the identity value for the combination.
 *
 * Dry run
 * Example from main: BST [4,2,7], insert 5 -> [4,2,7,null,null,5].
 * 1. Start at the root and apply the base case if the node is null.
 * 2. Recursively solve the left and right child subtrees.
 * 3. Combine the child results at the current node.
 * 4. Return the result assembled at the root.
 *
 * Complexity
 * O(n) time and O(h) auxiliary space, where h is tree height.
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
