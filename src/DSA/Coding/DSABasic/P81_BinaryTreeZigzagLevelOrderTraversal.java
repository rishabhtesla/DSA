package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P81: BINARY TREE ZIGZAG LEVEL ORDER TRAVERSAL
 *
 * Question
 * Return level-order values alternating left-to-right and right-to-left.
 *
 * Example
 * [3,9,20,null,null,15,7] -> [[3],[20,9],[15,7]].
 *
 * Constraints
 * 0 <= nodes <= 2000.
 *
 * Approach used by edSlash
 * 1. Breadth-first traversal uses a deque output; prepend values on reversed levels.
 * 2. Solve the same problem for the left and right subtrees, then combine their results at the current node.
 * 3. The base case for a null node stops the recursion and supplies the identity value for the combination.
 *
 * Dry run
 *   Zigzag queue visual:
 *     level 0 left-to-right -> [3]
 *     level 1 right-to-left -> [20,9]
 *     level 2 left-to-right -> [15,7]
 *     result: [[3],[20,9],[15,7]]
 *
 * Complexity
 * O(n) time and O(h) auxiliary space, where h is tree height.
 */
public class P81_BinaryTreeZigzagLevelOrderTraversal {
    public static List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> o = new ArrayList<>();
        if (root == null) return o;
        Queue<TreeNode> q = new ArrayDeque<>();
        q.add(root);
        boolean rev = false;
        while (!q.isEmpty()) {
            LinkedList<Integer> z = new LinkedList<>();
            for (int n = q.size(); n > 0; n--) {
                TreeNode x = q.remove();
                if (rev) z.addFirst(x.val);
                else z.add(x.val);
                if (x.left != null) q.add(x.left);
                if (x.right != null) q.add(x.right);
            }
            o.add(z);
            rev = !rev;
        }
        return o;
    }

    public static void main(String[] args) {
        System.out.println(zigzagLevelOrder(new TreeNode(1)));
    }
}
