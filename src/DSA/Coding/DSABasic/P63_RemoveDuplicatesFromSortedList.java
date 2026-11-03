package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P63: REMOVE DUPLICATES FROM SORTED LIST
 *
 * Question
 * Collapse repeated values in a sorted linked list to one node.
 *
 * Example
 * 1->1->2 -> 1->2.
 *
 * Constraints
 * 0 <= nodes <= 300; sorted nondecreasing.
 *
 * Approach used by edSlash
 * 1. Compare each node with its next; bypass the next node when values match.
 * 2. Use pointers to represent the already-processed part and the nodes that still need to be visited.
 * 3. Save the next node before changing links, then advance the pointers while preserving the list structure.
 *
 * Dry run
 *   Duplicate-skip visual for 1->1->2:
 *     compare first 1 with next 1 -> skip the duplicate
 *     compare 1 with 2 -> advance
 *     result: 1->2
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P63_RemoveDuplicatesFromSortedList {
    public static ListNode deleteDuplicates(ListNode head) {
        for (ListNode p = head; p != null && p.next != null; )
            if (p.val == p.next.val) p.next = p.next.next;
            else p = p.next;
        return head;
    }

    public static void main(String[] args) {
        System.out.println(deleteDuplicates(new ListNode(1, new ListNode(1))).val);
    }
}
