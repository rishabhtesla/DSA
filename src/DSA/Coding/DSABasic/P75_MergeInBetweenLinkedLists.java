package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P75: MERGE IN BETWEEN LINKED LISTS
 *
 * Question
 * Replace nodes from index a through b in list1 with list2.
 *
 * Example
 * [0,1,2], a=1,b=1, [9] -> [0,9,2].
 *
 * Constraints
 * 3 <= list1 length <= 10^4; 0 <= a <= b < length-1.
 *
 * Approach used by edSlash
 * 1. Find predecessor at a-1 and successor at b+1, splice list2 between them.
 * 2. Use pointers to represent the already-processed part and the nodes that still need to be visited.
 * 3. Save the next node before changing links, then advance the pointers while preserving the list structure.
 *
 * Dry run
 * Example from main: [0,1,2], a=1,b=1, [9] -> [0,9,2].
 * 1. Initialize the pointers described in the approach.
 * 2. Save the next node before changing a link or moving a pointer.
 * 3. Update the links and advance until the required node or the end is reached.
 * 4. Return the resulting head or answer.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P75_MergeInBetweenLinkedLists {
    public static ListNode mergeInBetween(ListNode a, int x, int y, ListNode b) {
        ListNode p = a;
        for (int i = 1; i < x; i++) p = p.next;
        ListNode q = p;
        for (int i = x; i <= y; i++) q = q.next;
        p.next = b;
        while (b.next != null) b = b.next;
        b.next = q.next;
        return a;
    }

    public static void main(String[] args) {
        System.out.println(mergeInBetween(new ListNode(0, new ListNode(1, new ListNode(2))), 1, 1, new ListNode(9)).next.val);
    }
}
