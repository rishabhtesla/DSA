package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P80: BINARY TREE LEVEL ORDER TRAVERSAL
 *
 * Question
 * Return values grouped by tree depth from left to right.
 *
 * Example
 * tree [3,9,20,null,null,15,7] -> [[3],[9,20],[15,7]].
 *
 * Constraints
 * 0 <= nodes <= 2000; values fit 32-bit integers.
 *
 * Approach used by edSlash
 * 1. Use a queue and process exactly its current size as one level.
 * 2. Solve the same problem for the left and right subtrees, then combine their results at the current node.
 * 3. The base case for a null node stops the recursion and supplies the identity value for the combination.
 *
 * Dry run
 *   Level-order queue visual:
 *     queue [3] -> output [3], enqueue 9 and 20
 *     queue [9,20] -> output [9,20], enqueue 15 and 7
 *     queue [15,7] -> output [15,7]
 *     result: [[3],[9,20],[15,7]]
 *
 * Complexity
 * O(n) time and O(h) auxiliary space, where h is tree height.
 */
public class P80_BinaryTreeLevelOrderTraversal {
    public static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> o = new ArrayList<>();
        if (root == null) return o;
        Queue<TreeNode> q = new ArrayDeque<>();
        q.add(root);
        while (!q.isEmpty()) {
            List<Integer> z = new ArrayList<>();
            for (int n = q.size(); n > 0; n--) {
                TreeNode x = q.remove();
                z.add(x.val);
                if (x.left != null) q.add(x.left);
                if (x.right != null) q.add(x.right);
            }
            o.add(z);
        }
        return o;
    }

    public static void main(String[] args) {
        System.out.println(levelOrder(new TreeNode(1)));
    }
}
