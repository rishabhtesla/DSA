package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P93: CONVERT SORTED LIST TO BST
 *
 * Question
 * Create a height-balanced BST from a sorted singly linked list.
 *
 * Example
 * 1->2->3 -> root 2.
 *
 * Constraints
 * 0 <= n <= 2*10^4; list sorted strictly increasing.
 *
 * Approach used by edSlash
 * 1. Copy list values, choose midpoint recursively, and build both halves.
 * 2. Solve the same problem for the left and right subtrees, then combine their results at the current node.
 * 3. The base case for a null node stops the recursion and supplies the identity value for the combination.
 *
 * Dry run
 *   Sorted-list-to-BST visual for 1->2->3:
 *     copy values in sorted order -> [1,2,3]
 *     choose middle value 2 as root
 *     values 1 and 3 become the left and right children
 *     return root 2
 *
 * Complexity
 * O(n) time and O(h) auxiliary space, where h is tree height.
 */
public class P93_ConvertSortedListToBST {
    public static TreeNode sortedListToBST(ListNode head) {
        ArrayList<Integer> a = new ArrayList<>();
        for (; head != null; head = head.next) a.add(head.val);
        return b(a, 0, a.size() - 1);
    }

    static TreeNode b(ArrayList<Integer> a, int l, int r) {
        if (l > r) return null;
        int m = (l + r) / 2;
        return new TreeNode(a.get(m), b(a, l, m - 1), b(a, m + 1, r));
    }

    public static void main(String[] args) {
        System.out.println(sortedListToBST(new ListNode(1, new ListNode(2, new ListNode(3)))).val);
    }
}
