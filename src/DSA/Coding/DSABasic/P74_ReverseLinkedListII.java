package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P74: REVERSE LINKED LIST II
 *
 * Question
 * Reverse links from position left through right, preserving the rest.
 *
 * Example
 * 1->2, left=1,right=2 -> 2->1.
 *
 * Constraints
 * 1 <= left <= right <= n <= 500.
 *
 * Approach used by edSlash
 * 1. Repeatedly remove the node after the reversed segment head and insert it at the front.
 * 2. Use pointers to represent the already-processed part and the nodes that still need to be visited.
 * 3. Save the next node before changing links, then advance the pointers while preserving the list structure.
 *
 * Dry run
 * Example from main: 1->2, left=1,right=2 -> 2->1.
 * 1. Initialize the pointers described in the approach.
 * 2. Save the next node before changing a link or moving a pointer.
 * 3. Update the links and advance until the required node or the end is reached.
 * 4. Return the resulting head or answer.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P74_ReverseLinkedListII {
    public static ListNode reverseBetween(ListNode h, int l, int r) {
        ListNode d = new ListNode(0, h), p = d;
        for (int i = 1; i < l; i++) p = p.next;
        ListNode q = p.next;
        for (int i = 0; i < r - l; i++) {
            ListNode x = q.next;
            q.next = x.next;
            x.next = p.next;
            p.next = x;
        }
        return d.next;
    }

    public static void main(String[] args) {
        System.out.println(reverseBetween(new ListNode(1, new ListNode(2)), 1, 2).val);
    }
}
