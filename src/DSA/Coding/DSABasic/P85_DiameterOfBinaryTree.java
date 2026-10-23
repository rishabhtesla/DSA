package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Return the largest number of edges on any path between two tree nodes.
 * EXAMPLE: [1,2,3,4,5] -> 3.
 * CONSTRAINTS: 1 <= nodes <= 10^4.
 * LOGIC / ALGORITHM:
 * 1. Postorder height gives longest downward arm; update best with left height plus right height.
 * 2. Combine the child results while visiting each node once, carrying the needed value toward the root.
 * 3. Return the root/result assembled from the completed subtrees.
 * INVARIANT: Every completed subtree already satisfies its property, and combining child results preserves correctness.
 * <p>
 * COMPLEXITY: O(n) time and O(h) auxiliary space, where h is tree height.
 * DRY RUN: Main input [1,2,3,4,5] -> 3.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P85_DiameterOfBinaryTree {
    static int best;

    public static int diameterOfBinaryTree(TreeNode r) {
        best = 0;
        d(r);
        return best;
    }

    static int d(TreeNode n) {
        if (n == null) return 0;
        int a = d(n.left), b = d(n.right);
        best = Math.max(best, a + b);
        return 1 + Math.max(a, b);
    }

    public static void main(String[] args) {
        System.out.println(diameterOfBinaryTree(new TreeNode(1)));
    }
}
