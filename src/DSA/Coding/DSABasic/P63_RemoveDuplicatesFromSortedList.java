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
 * Example from main: 1->1->2 -> 1->2.
 * 1. Initialize the pointers described in the approach.
 * 2. Save the next node before changing a link or moving a pointer.
 * 3. Update the links and advance until the required node or the end is reached.
 * 4. Return the resulting head or answer.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P63_RemoveDuplicatesFromSortedList {
    public static ListNode deleteDuplicates(ListNode h) {
        for (ListNode p = h; p != null && p.next != null; )
            if (p.val == p.next.val) p.next = p.next.next;
            else p = p.next;
        return h;
    }

    public static void main(String[] args) {
        System.out.println(deleteDuplicates(new ListNode(1, new ListNode(1))).val);
    }
}
