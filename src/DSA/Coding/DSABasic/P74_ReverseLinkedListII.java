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
 *   Sublist-reversal visual for 1->2, left=1, right=2:
 *     isolate the section 1->2
 *     reverse its link -> 2->1
 *     reconnect unchanged prefix and suffix
 *     result: 2->1
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P74_ReverseLinkedListII {
    public static ListNode reverseBetween(ListNode head, int leftPosition, int rightPosition) {
        ListNode d = new ListNode(0, head), p = d;
        for (int i = 1; i < leftPosition; i++) p = p.next;
        ListNode q = p.next;
        for (int i = 0; i < rightPosition - leftPosition; i++) {
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
