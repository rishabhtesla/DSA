package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Return values grouped by tree depth from left to right.
 * EXAMPLE: tree [3,9,20,null,null,15,7] -> [[3],[9,20],[15,7]].
 * CONSTRAINTS: 0 <= nodes <= 2000; values fit 32-bit integers.
 * LOGIC / ALGORITHM:
 * 1. Use a queue and process exactly its current size as one level.
 * 2. Combine the child results while visiting each node once, carrying the needed value toward the root.
 * 3. Return the root/result assembled from the completed subtrees.
 * INVARIANT: Every completed subtree already satisfies its property, and combining child results preserves correctness.
 * <p>
 * COMPLEXITY: O(n) time and O(h) auxiliary space, where h is tree height.
 * DRY RUN: Main input tree [3,9,20,null,null,15,7] -> [[3],[9,20],[15,7]].; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P80_BinaryTreeLevelOrderTraversal {
    public static List<List<Integer>> levelOrder(TreeNode r) {
        List<List<Integer>> o = new ArrayList<>();
        if (r == null) return o;
        Queue<TreeNode> q = new ArrayDeque<>();
        q.add(r);
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
