package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P89: DELETE NODE IN ABST
 *
 * Question
 * Delete a key from a BST and preserve its ordering.
 *
 * Example
 * BST [5,3,6,2,4,null,7], delete 3 -> valid BST rooted at 5.
 *
 * Constraints
 * 0 <= nodes <= 5000; keys are unique.
 *
 * Approach used by edSlash
 * 1. Find key; replace a two-child node with its right-subtree successor, then delete successor.
 * 2. Solve the same problem for the left and right subtrees, then combine their results at the current node.
 * 3. The base case for a null node stops the recursion and supplies the identity value for the combination.
 *
 * Dry run
 * Example from main: BST [5,3,6,2,4,null,7], delete 3 -> valid BST rooted at 5.
 * 1. Start at the root and apply the base case if the node is null.
 * 2. Recursively solve the left and right child subtrees.
 * 3. Combine the child results at the current node.
 * 4. Return the result assembled at the root.
 *
 * Complexity
 * O(n) time and O(h) auxiliary space, where h is tree height.
 */
public class P89_DeleteNodeInABST {
    public static TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) return null;
        if (key < root.val) root.left = deleteNode(root.left, key);
        else if (key > root.val) root.right = deleteNode(root.right, key);
        else {
            if (root.left == null) return root.right;
            if (root.right == null) return root.left;
            TreeNode s = root.right;
            while (s.left != null) s = s.left;
            root.val = s.val;
            root.right = deleteNode(root.right, s.val);
        }
        return root;
    }

    public static void main(String[] args) {
        System.out.println(deleteNode(new TreeNode(1), 1));
    }
}
