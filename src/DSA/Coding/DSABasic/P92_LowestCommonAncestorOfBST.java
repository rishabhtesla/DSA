package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P92: LOWEST COMMON ANCESTOR OF BST
 *
 * Question
 * Find the lowest node whose subtree contains p and q in a BST.
 *
 * Example
 * BST [6,2,8,0,4,7,9], p=2,q=8 -> 6.
 *
 * Constraints
 * All values unique; p and q exist in the BST.
 *
 * Approach used by edSlash
 * 1. If both values are smaller go left; both larger go right; otherwise current node splits them.
 * 2. Solve the same problem for the left and right subtrees, then combine their results at the current node.
 * 3. The base case for a null node stops the recursion and supplies the identity value for the combination.
 *
 * Dry run
 * Example from main: BST [6,2,8,0,4,7,9], p=2,q=8 -> 6.
 * 1. Start at the root and apply the base case if the node is null.
 * 2. Recursively solve the left and right child subtrees.
 * 3. Combine the child results at the current node.
 * 4. Return the result assembled at the root.
 *
 * Complexity
 * O(n) time and O(h) auxiliary space, where h is tree height.
 */
public class P92_LowestCommonAncestorOfBST {
    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode firstNode, TreeNode secondNode) {
        while (root != null) {
            if (firstNode.val < root.val && secondNode.val < root.val) root = root.left;
            else if (firstNode.val > root.val && secondNode.val > root.val) root = root.right;
            else return root;
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(lowestCommonAncestor(new TreeNode(2, new TreeNode(1), new TreeNode(3)), new TreeNode(1), new TreeNode(3)).val);
    }
}
