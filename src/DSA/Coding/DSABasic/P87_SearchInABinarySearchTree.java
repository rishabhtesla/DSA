package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P87: SEARCH IN A BINARY SEARCH TREE
 *
 * Question
 * Return the subtree rooted at value val in a BST.
 *
 * Example
 * BST [4,2,7,1,3], val=2 -> subtree rooted at 2.
 *
 * Constraints
 * 1 <= nodes <= 5000; values unique and BST-ordered.
 *
 * Approach used by edSlash
 * 1. Compare val with current node and move left or right until found or null.
 * 2. Solve the same problem for the left and right subtrees, then combine their results at the current node.
 * 3. The base case for a null node stops the recursion and supplies the identity value for the combination.
 *
 * Dry run
 * Example from main: BST [4,2,7,1,3], val=2 -> subtree rooted at 2.
 * 1. Start at the root and apply the base case if the node is null.
 * 2. Recursively solve the left and right child subtrees.
 * 3. Combine the child results at the current node.
 * 4. Return the result assembled at the root.
 *
 * Complexity
 * O(n) time and O(h) auxiliary space, where h is tree height.
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
