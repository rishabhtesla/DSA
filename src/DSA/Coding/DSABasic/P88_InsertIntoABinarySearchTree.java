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
 *   BST-insert visual for value 5:
 *     at 4: 5 > 4, move right to 7
 *     at 7: 5 < 7, move left
 *     left child is null, insert 5 there
 *
 * Complexity
 * O(n) time and O(h) auxiliary space, where h is tree height.
 */
public class P88_InsertIntoABinarySearchTree {
    public static TreeNode insertIntoBST(TreeNode root, int value) {
        if (root == null) return new TreeNode(value);
        if (value < root.val) root.left = insertIntoBST(root.left, value);
        else root.right = insertIntoBST(root.right, value);
        return root;
    }

    public static void main(String[] args) {
        System.out.println(insertIntoBST(new TreeNode(2), 1).left.val);
    }
}
