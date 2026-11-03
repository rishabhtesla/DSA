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
 *   Cycle-entry visual for 0->1->2->1:
 *     slow and fast first meet inside the cycle
 *     reset one pointer to the head
 *     move both one node at a time; they meet at node 1
 *     return node 1
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
