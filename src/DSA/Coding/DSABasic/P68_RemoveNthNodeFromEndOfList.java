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
 * Example from main: 1->2, n=1 -> 1.
 * 1. Initialize the pointers described in the approach.
 * 2. Save the next node before changing a link or moving a pointer.
 * 3. Update the links and advance until the required node or the end is reached.
 * 4. Return the resulting head or answer.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P68_RemoveNthNodeFromEndOfList {
    public static ListNode removeNthFromEnd(ListNode h, int n) {
        ListNode d = new ListNode(0, h), p = d;
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
