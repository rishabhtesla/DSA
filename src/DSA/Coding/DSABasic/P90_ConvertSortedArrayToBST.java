package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P90: CONVERT SORTED ARRAY TO BST
 *
 * Question
 * Create a height-balanced BST from a sorted array.
 *
 * Example
 * [-10,-3,0,5,9] -> root 0 in one valid balanced tree.
 *
 * Constraints
 * 1 <= n <= 10^4; array strictly increasing.
 *
 * Approach used by edSlash
 * 1. Choose midpoint as root and recursively build left and right halves.
 * 2. Solve the same problem for the left and right subtrees, then combine their results at the current node.
 * 3. The base case for a null node stops the recursion and supplies the identity value for the combination.
 *
 * Dry run
 * Example from main: [-10,-3,0,5,9] -> root 0 in one valid balanced tree.
 * 1. Start at the root and apply the base case if the node is null.
 * 2. Recursively solve the left and right child subtrees.
 * 3. Combine the child results at the current node.
 * 4. Return the result assembled at the root.
 *
 * Complexity
 * O(n) time and O(h) auxiliary space, where h is tree height.
 */
public class P90_ConvertSortedArrayToBST {
    public static TreeNode sortedArrayToBST(int[] a) {
        return b(a, 0, a.length - 1);
    }

    static TreeNode b(int[] a, int l, int r) {
        if (l > r) return null;
        int m = (l + r) / 2;
        return new TreeNode(a[m], b(a, l, m - 1), b(a, m + 1, r));
    }

    public static void main(String[] args) {
        System.out.println(sortedArrayToBST(new int[]{-10, -3, 0, 5, 9}).val);
    }
}
