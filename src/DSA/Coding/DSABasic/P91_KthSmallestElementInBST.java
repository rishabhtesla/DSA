package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Return the kth smallest key in a BST.
 * EXAMPLE: BST [3,1,4,null,2], k=1 -> 1.
 * CONSTRAINTS: 1 <= k <= nodes <= 10^4; keys unique.
 * LOGIC / ALGORITHM:
 * 1. Iterative inorder traversal visits BST keys in sorted order; stop at kth visit.
 * 2. Combine the child results while visiting each node once, carrying the needed value toward the root.
 * 3. Return the root/result assembled from the completed subtrees.
 * INVARIANT: Every completed subtree already satisfies its property, and combining child results preserves correctness.
 * <p>
 * COMPLEXITY: O(n) time and O(h) auxiliary space, where h is tree height.
 * DRY RUN: Main input BST [3,1,4,null,2], k=1 -> 1.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P91_KthSmallestElementInBST {
    public static int kthSmallest(TreeNode r, int k) {
        Deque<TreeNode> s = new ArrayDeque<>();
        while (true) {
            while (r != null) {
                s.push(r);
                r = r.left;
            }
            r = s.pop();
            if (--k == 0) return r.val;
            r = r.right;
        }
    }

    public static void main(String[] args) {
        System.out.println(kthSmallest(new TreeNode(2, new TreeNode(1), new TreeNode(3)), 1));
    }
}
