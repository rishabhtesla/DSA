package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P82: MAXIMUM DEPTH OF BINARY TREE
 *
 * Question
 * Return the number of nodes on the longest root-to-leaf path.
 *
 * Example
 * tree [3,9,20,null,null,15,7] -> 3.
 *
 * Constraints
 * 0 <= nodes <= 10^4.
 *
 * Approach used by edSlash
 * 1. Depth is zero for null; otherwise one plus the larger child depth.
 * 2. Solve the same problem for the left and right subtrees, then combine their results at the current node.
 * 3. The base case for a null node stops the recursion and supplies the identity value for the combination.
 *
 * Dry run
 * Example from main: tree [3,9,20,null,null,15,7] -> 3.
 * 1. Start at the root and apply the base case if the node is null.
 * 2. Recursively solve the left and right child subtrees.
 * 3. Combine the child results at the current node.
 * 4. Return the result assembled at the root.
 *
 * Complexity
 * O(n) time and O(h) auxiliary space, where h is tree height.
 */
public class P82_MaximumDepthOfBinaryTree {
    public static int maxDepth(TreeNode root) {
        return root == null ? 0 : 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    public static void main(String[] args) {
        System.out.println(maxDepth(new TreeNode(1)));
    }
}
