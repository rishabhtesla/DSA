package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P85: DIAMETER OF BINARY TREE
 *
 * Question
 * Return the largest number of edges on any path between two tree nodes.
 *
 * Example
 * [1,2,3,4,5] -> 3.
 *
 * Constraints
 * 1 <= nodes <= 10^4.
 *
 * Approach used by edSlash
 * 1. Postorder height gives longest downward arm; update best with left height plus right height.
 * 2. Solve the same problem for the left and right subtrees, then combine their results at the current node.
 * 3. The base case for a null node stops the recursion and supplies the identity value for the combination.
 *
 * Dry run
 * Example from main: [1,2,3,4,5] -> 3.
 * 1. Start at the root and apply the base case if the node is null.
 * 2. Recursively solve the left and right child subtrees.
 * 3. Combine the child results at the current node.
 * 4. Return the result assembled at the root.
 *
 * Complexity
 * O(n) time and O(h) auxiliary space, where h is tree height.
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
