package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P64: REMOVE DUPLICATES FROM SORTED LIST II
 *
 * Question
 * Remove every value that occurs more than once from a sorted list.
 *
 * Example
 * 1->1->2 -> 2.
 *
 * Constraints
 * 0 <= nodes <= 300; sorted nondecreasing.
 *
 * Approach used by edSlash
 * 1. Use a dummy predecessor; skip an entire equal-value run when a duplicate is found.
 * 2. Use pointers to represent the already-processed part and the nodes that still need to be visited.
 * 3. Save the next node before changing links, then advance the pointers while preserving the list structure.
 *
 * Dry run
 * Example from main: 1->1->2 -> 2.
 * 1. Initialize the pointers described in the approach.
 * 2. Save the next node before changing a link or moving a pointer.
 * 3. Update the links and advance until the required node or the end is reached.
 * 4. Return the resulting head or answer.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P64_RemoveDuplicatesFromSortedListII {
    public static ListNode deleteDuplicates(ListNode h) {
        ListNode d = new ListNode(0, h), p = d;
        while (h != null) {
            if (h.next != null && h.val == h.next.val) {
                int v = h.val;
                while (h != null && h.val == v) h = h.next;
                p.next = h;
            } else {
                p = h;
                h = h.next;
            }
        }
        return d.next;
    }

    public static void main(String[] args) {
        System.out.println(deleteDuplicates(new ListNode(1, new ListNode(1, new ListNode(2)))).val);
    }
}
