package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P79: BINARY TREE POSTORDER TRAVERSAL
 *
 * Question
 * Return binary-tree values in left-right-root order.
 *
 * Example
 * tree [1,null,2,3] -> [3,2,1].
 *
 * Constraints
 * 0 <= nodes <= 100; values fit 32-bit integers.
 *
 * Approach used by edSlash
 * 1. Recursively process both children before recording each root.
 * 2. Solve the same problem for the left and right subtrees, then combine their results at the current node.
 * 3. The base case for a null node stops the recursion and supplies the identity value for the combination.
 *
 * Dry run
 * Example from main: tree [1,null,2,3] -> [3,2,1].
 * 1. Start at the root and apply the base case if the node is null.
 * 2. Recursively solve the left and right child subtrees.
 * 3. Combine the child results at the current node.
 * 4. Return the result assembled at the root.
 *
 * Complexity
 * O(n) time and O(h) auxiliary space, where h is tree height.
 */
public class P79_BinaryTreePostorderTraversal {
    public static List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> o = new ArrayList<>();
        post(root, o);
        return o;
    }

    static void post(TreeNode n, List<Integer> o) {
        if (n == null) return;
        post(n.left, o);
        post(n.right, o);
        o.add(n.val);
    }

    public static void main(String[] args) {
        System.out.println(postorderTraversal(new TreeNode(1)));
    }
}
