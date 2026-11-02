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
 * Example from main: BST [4,1,6,0,2,5,7] -> node 4 becomes 22.
 * 1. Start at the root and apply the base case if the node is null.
 * 2. Recursively solve the left and right child subtrees.
 * 3. Combine the child results at the current node.
 * 4. Return the result assembled at the root.
 *
 * Complexity
 * O(n) time and O(h) auxiliary space, where h is tree height.
 */
public class P95_BSTToGreaterSumTree {
    static int sum;

    public static TreeNode bstToGst(TreeNode r) {
        sum = 0;
        go(r);
        return r;
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
