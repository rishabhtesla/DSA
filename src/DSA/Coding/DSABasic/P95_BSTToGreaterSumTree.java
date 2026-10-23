package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Replace each BST key with itself plus every key greater than it.
 * EXAMPLE: BST [4,1,6,0,2,5,7] -> node 4 becomes 22.
 * CONSTRAINTS: 1 <= nodes <= 100; keys are unique.
 * LOGIC / ALGORITHM:
 * 1. Reverse inorder visits descending keys while maintaining cumulative sum.
 * 2. Combine the child results while visiting each node once, carrying the needed value toward the root.
 * 3. Return the root/result assembled from the completed subtrees.
 * INVARIANT: Every completed subtree already satisfies its property, and combining child results preserves correctness.
 * <p>
 * COMPLEXITY: O(n) time and O(h) auxiliary space, where h is tree height.
 * DRY RUN: Main input BST [4,1,6,0,2,5,7] -> node 4 becomes 22.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
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
