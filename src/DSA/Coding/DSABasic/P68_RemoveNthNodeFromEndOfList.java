package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P68: REMOVE NTH NODE FROM END OF LIST
 *
 * Question
 * Remove the nth node from the end and return the head.
 *
 * Example
 * 1->2, n=1 -> 1.
 *
 * Constraints
 * 1 <= nodes <= 30; 1 <= n <= nodes.
 *
 * Approach used by edSlash
 * 1. Keep fast n nodes ahead of slow; when fast reaches tail, slow precedes the target.
 * 2. Use pointers to represent the already-processed part and the nodes that still need to be visited.
 * 3. Save the next node before changing links, then advance the pointers while preserving the list structure.
 *
 * Dry run
 *   Gap-pointer visual for 1->2, n = 1:
 *     move the fast pointer n+1 positions ahead of slow
 *     advance both until fast reaches null
 *     slow is just before the node to remove; remove node 2
 *     result: 1
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P68_RemoveNthNodeFromEndOfList {
    public static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode d = new ListNode(0, head), p = d;
        for (int i = 0; i < n; i++) p = p.next;
        ListNode q = d;
        while (p.next != null) {
            p = p.next;
            q = q.next;
        }
        q.next = q.next.next;
        return d.next;
    }

    public static void main(String[] args) {
        System.out.println(removeNthFromEnd(new ListNode(1, new ListNode(2)), 1).val);
    }
}
