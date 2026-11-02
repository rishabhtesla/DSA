package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P62: MERGE TWO SORTED LISTS
 *
 * Question
 * Merge two sorted linked lists into one sorted list.
 *
 * Example
 * 1->2 and 1->3 -> 1->1->2->3.
 *
 * Constraints
 * 0 <= total nodes <= 50; lists are sorted.
 *
 * Approach used by edSlash
 * 1. Use a dummy tail and repeatedly link the smaller head.
 * 2. Use pointers to represent the already-processed part and the nodes that still need to be visited.
 * 3. Save the next node before changing links, then advance the pointers while preserving the list structure.
 *
 * Dry run
 * Example from main: 1->2 and 1->3 -> 1->1->2->3.
 * 1. Initialize the pointers described in the approach.
 * 2. Save the next node before changing a link or moving a pointer.
 * 3. Update the links and advance until the required node or the end is reached.
 * 4. Return the resulting head or answer.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P62_MergeTwoSortedLists {
    public static ListNode mergeTwoLists(ListNode firstList, ListNode secondList) {
        ListNode d = new ListNode(), p = d;
        while (firstList != null && secondList != null) {
            if (firstList.val <= secondList.val) {
                p.next = firstList;
                firstList = firstList.next;
            } else {
                p.next = secondList;
                secondList = secondList.next;
            }
            p = p.next;
        }
        p.next = firstList == null ? secondList : firstList;
        return d.next;
    }

    public static void main(String[] args) {
        System.out.println(mergeTwoLists(new ListNode(1), new ListNode(2)).val);
    }
}
