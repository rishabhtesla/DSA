package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P91: KTH SMALLEST ELEMENT IN BST
 *
 * Question
 * Return the kth smallest key in a BST.
 *
 * Example
 * BST [3,1,4,null,2], k=1 -> 1.
 *
 * Constraints
 * 1 <= k <= nodes <= 10^4; keys unique.
 *
 * Approach used by edSlash
 * 1. Iterative inorder traversal visits BST keys in sorted order; stop at kth visit.
 * 2. Solve the same problem for the left and right subtrees, then combine their results at the current node.
 * 3. The base case for a null node stops the recursion and supplies the identity value for the combination.
 *
 * Dry run
 * Example from main: BST [3,1,4,null,2], k=1 -> 1.
 * 1. Start at the root and apply the base case if the node is null.
 * 2. Recursively solve the left and right child subtrees.
 * 3. Combine the child results at the current node.
 * 4. Return the result assembled at the root.
 *
 * Complexity
 * O(n) time and O(h) auxiliary space, where h is tree height.
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
