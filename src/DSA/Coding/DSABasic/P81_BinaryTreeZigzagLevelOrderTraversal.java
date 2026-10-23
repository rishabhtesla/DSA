package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Return level-order values alternating left-to-right and right-to-left.
 * EXAMPLE: [3,9,20,null,null,15,7] -> [[3],[20,9],[15,7]].
 * CONSTRAINTS: 0 <= nodes <= 2000.
 * LOGIC / ALGORITHM:
 * 1. Breadth-first traversal uses a deque output; prepend values on reversed levels.
 * 2. Combine the child results while visiting each node once, carrying the needed value toward the root.
 * 3. Return the root/result assembled from the completed subtrees.
 * INVARIANT: Every completed subtree already satisfies its property, and combining child results preserves correctness.
 * <p>
 * COMPLEXITY: O(n) time and O(h) auxiliary space, where h is tree height.
 * DRY RUN: Main input [3,9,20,null,null,15,7] -> [[3],[20,9],[15,7]].; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P81_BinaryTreeZigzagLevelOrderTraversal {
    public static List<List<Integer>> zigzagLevelOrder(TreeNode r) {
        List<List<Integer>> o = new ArrayList<>();
        if (r == null) return o;
        Queue<TreeNode> q = new ArrayDeque<>();
        q.add(r);
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
