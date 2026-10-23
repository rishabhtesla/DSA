package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Create a height-balanced BST from a sorted array.
 * EXAMPLE: [-10,-3,0,5,9] -> root 0 in one valid balanced tree.
 * CONSTRAINTS: 1 <= n <= 10^4; array strictly increasing.
 * LOGIC / ALGORITHM:
 * 1. Choose midpoint as root and recursively build left and right halves.
 * 2. Combine the child results while visiting each node once, carrying the needed value toward the root.
 * 3. Return the root/result assembled from the completed subtrees.
 * INVARIANT: Every completed subtree already satisfies its property, and combining child results preserves correctness.
 * <p>
 * COMPLEXITY: O(n) time and O(h) auxiliary space, where h is tree height.
 * DRY RUN: Main input [-10,-3,0,5,9] -> root 0 in one valid balanced tree.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
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
