package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Return the number of nodes on the longest root-to-leaf path.
 * EXAMPLE: tree [3,9,20,null,null,15,7] -> 3.
 * CONSTRAINTS: 0 <= nodes <= 10^4.
 * LOGIC / ALGORITHM:
 * 1. Depth is zero for null; otherwise one plus the larger child depth.
 * 2. Combine the child results while visiting each node once, carrying the needed value toward the root.
 * 3. Return the root/result assembled from the completed subtrees.
 * INVARIANT: Every completed subtree already satisfies its property, and combining child results preserves correctness.
 * <p>
 * COMPLEXITY: O(n) time and O(h) auxiliary space, where h is tree height.
 * DRY RUN: Main input tree [3,9,20,null,null,15,7] -> 3.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P82_MaximumDepthOfBinaryTree {
    public static int maxDepth(TreeNode r) {
        return r == null ? 0 : 1 + Math.max(maxDepth(r.left), maxDepth(r.right));
    }

    public static void main(String[] args) {
        System.out.println(maxDepth(new TreeNode(1)));
    }
}
