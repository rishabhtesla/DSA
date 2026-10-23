package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Return the value visible at each depth from the right side.
 * EXAMPLE: [1,2,3,null,5,null,4] -> [1,3,4].
 * CONSTRAINTS: 0 <= nodes <= 100.
 * LOGIC / ALGORITHM:
 * 1. Breadth-first traversal records the final node removed from each level.
 * 2. Combine the child results while visiting each node once, carrying the needed value toward the root.
 * 3. Return the root/result assembled from the completed subtrees.
 * INVARIANT: Every completed subtree already satisfies its property, and combining child results preserves correctness.
 * <p>
 * COMPLEXITY: O(n) time and O(h) auxiliary space, where h is tree height.
 * DRY RUN: Main input [1,2,3,null,5,null,4] -> [1,3,4].; follow the numbered steps while maintaining the invariant, producing the value printed by main.
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
