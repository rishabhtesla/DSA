package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Create a height-balanced BST from a sorted singly linked list.
 * EXAMPLE: 1->2->3 -> root 2.
 * CONSTRAINTS: 0 <= n <= 2*10^4; list sorted strictly increasing.
 * LOGIC / ALGORITHM:
 * 1. Copy list values, choose midpoint recursively, and build both halves.
 * 2. Combine the child results while visiting each node once, carrying the needed value toward the root.
 * 3. Return the root/result assembled from the completed subtrees.
 * INVARIANT: Every completed subtree already satisfies its property, and combining child results preserves correctness.
 * <p>
 * COMPLEXITY: O(n) time and O(h) auxiliary space, where h is tree height.
 * DRY RUN: Main input 1->2->3 -> root 2.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P93_ConvertSortedListToBST {
    public static TreeNode sortedListToBST(ListNode h) {
        ArrayList<Integer> a = new ArrayList<>();
        for (; h != null; h = h.next) a.add(h.val);
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
