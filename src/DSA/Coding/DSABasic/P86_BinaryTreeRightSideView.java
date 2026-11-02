package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P86: BINARY TREE RIGHT SIDE VIEW
 *
 * Question
 * Return the value visible at each depth from the right side.
 *
 * Example
 * [1,2,3,null,5,null,4] -> [1,3,4].
 *
 * Constraints
 * 0 <= nodes <= 100.
 *
 * Approach used by edSlash
 * 1. Breadth-first traversal records the final node removed from each level.
 * 2. Solve the same problem for the left and right subtrees, then combine their results at the current node.
 * 3. The base case for a null node stops the recursion and supplies the identity value for the combination.
 *
 * Dry run
 * Example from main: [1,2,3,null,5,null,4] -> [1,3,4].
 * 1. Start at the root and apply the base case if the node is null.
 * 2. Recursively solve the left and right child subtrees.
 * 3. Combine the child results at the current node.
 * 4. Return the result assembled at the root.
 *
 * Complexity
 * O(n) time and O(h) auxiliary space, where h is tree height.
 */
public class P86_BinaryTreeRightSideView {
    public static List<Integer> rightSideView(TreeNode r) {
        List<Integer> o = new ArrayList<>();
        if (r == null) return o;
        Queue<TreeNode> q = new ArrayDeque<>();
        q.add(r);
        while (!q.isEmpty()) {
            TreeNode x = null;
            for (int n = q.size(); n > 0; n--) {
                x = q.remove();
                if (x.left != null) q.add(x.left);
                if (x.right != null) q.add(x.right);
            }
            o.add(x.val);
        }
        return o;
    }

    public static void main(String[] args) {
        System.out.println(rightSideView(new TreeNode(1)));
    }
}
