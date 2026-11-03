package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P95: BST TO GREATER SUM TREE
 *
 * Question
 * Replace each BST key with itself plus every key greater than it.
 *
 * Example
 * BST [4,1,6,0,2,5,7] -> node 4 becomes 22.
 *
 * Constraints
 * 1 <= nodes <= 100; keys are unique.
 *
 * Approach used by edSlash
 * 1. Reverse inorder visits descending keys while maintaining cumulative sum.
 * 2. Solve the same problem for the left and right subtrees, then combine their results at the current node.
 * 3. The base case for a null node stops the recursion and supplies the identity value for the combination.
 *
 * Dry run
 *   Reverse-inorder GST visual for [4,1,6,0,2,5,7]:
 *     visit values in descending order: 7,6,5,4,2,1,0
 *     running sum at 7 = 7; at 6 = 13; at 5 = 18
 *     running sum at 4 = 22, so node 4 becomes 22
 *     continue updating smaller values with the running sum
 *
 * Complexity
 * O(n) time and O(h) auxiliary space, where h is tree height.
 */
public class P95_BSTToGreaterSumTree {
    static int sum;

    public static TreeNode bstToGst(TreeNode root) {
        sum = 0;
        go(root);
        return root;
    }

    static void go(TreeNode n) {
        if (n == null) return;
        go(n.right);
        sum += n.val;
        n.val = sum;
        go(n.left);
    }

    public static void main(String[] args) {
        System.out.println(bstToGst(new TreeNode(1)).val);
    }
}
