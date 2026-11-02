package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P67: LINKED LIST CYCLE II
 *
 * Question
 * Return the node where a linked-list cycle begins.
 *
 * Example
 * 0->1->2->1 -> node 1.
 *
 * Constraints
 * 0 <= nodes <= 10^4; cycle may be absent.
 *
 * Approach used by edSlash
 * 1. After Floyd pointers meet, reset one to head; equal remaining distances make their next meeting the entry.
 * 2. Use pointers to represent the already-processed part and the nodes that still need to be visited.
 * 3. Save the next node before changing links, then advance the pointers while preserving the list structure.
 *
 * Dry run
 * Example from main: 0->1->2->1 -> node 1.
 * 1. Initialize the pointers described in the approach.
 * 2. Save the next node before changing a link or moving a pointer.
 * 3. Update the links and advance until the required node or the end is reached.
 * 4. Return the resulting head or answer.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P67_LinkedListCycleII {
    public static ListNode detectCycle(ListNode head) {
        ListNode s = head, f = head;
        while (f != null && f.next != null) {
            s = s.next;
            f = f.next.next;
            if (s == f) {
                s = head;
                while (s != f) {
                    s = s.next;
                    f = f.next;
                }
                return s;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(detectCycle(null));
    }
}
