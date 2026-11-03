package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P66: LINKED LIST CYCLE
 *
 * Question
 * Determine whether a linked list contains a cycle.
 *
 * Example
 * 1->2->1 cycle -> true.
 *
 * Constraints
 * 0 <= nodes <= 10^4; next may point to any node or null.
 *
 * Approach used by edSlash
 * 1. Floyd slow/fast pointers meet exactly when a cycle exists.
 * 2. Use pointers to represent the already-processed part and the nodes that still need to be visited.
 * 3. Save the next node before changing links, then advance the pointers while preserving the list structure.
 *
 * Dry run
 *   Cycle-detection visual for 1->2->1:
 *     slow moves one node and fast moves two nodes
 *     slow and fast eventually point to the same node
 *     meeting means a cycle exists -> true
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P66_LinkedListCycle {
    public static boolean hasCycle(ListNode head) {
        ListNode s = head, f = head;
        while (f != null && f.next != null) {
            s = s.next;
            f = f.next.next;
            if (s == f) return true;
        }
        return false;
    }

    public static void main(String[] args) {
        ListNode x = new ListNode(1);
        x.next = x;
        System.out.println(hasCycle(x));
    }
}
