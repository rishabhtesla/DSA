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
 *   Depth recursion visual for tree [3,9,20,null,null,15,7]:
 *     depth(9) = 1
 *     depth(15) = depth(7) = 1, so depth(20) = 2
 *     depth(3) = 1 + max(1,2) = 3
 *     return 3
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
