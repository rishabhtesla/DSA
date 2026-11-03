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
 *   Duplicate-run visual for 1->1->2:
 *     detect the run 1->1 and remove the entire run
 *     value 2 is not duplicated, so keep it
 *     result: 2
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P64_RemoveDuplicatesFromSortedListII {
    public static ListNode deleteDuplicates(ListNode head) {
        ListNode d = new ListNode(0, head), p = d;
        while (head != null) {
            if (head.next != null && head.val == head.next.val) {
                int v = head.val;
                while (head != null && head.val == v) head = head.next;
                p.next = head;
            } else {
                p = head;
                head = head.next;
            }
        }
        return d.next;
    }

    public static void main(String[] args) {
        System.out.println(deleteDuplicates(new ListNode(1, new ListNode(1, new ListNode(2)))).val);
    }
}
