package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P73: SWAP NODES IN PAIRS
 *
 * Question
 * Swap every adjacent pair in a linked list without changing values.
 *
 * Example
 * 1->2->3 -> 2->1->3.
 *
 * Constraints
 * 0 <= nodes <= 100; links may be rewired only.
 *
 * Approach used by edSlash
 * 1. Use a dummy predecessor and rotate each two-node block.
 * 2. Use pointers to represent the already-processed part and the nodes that still need to be visited.
 * 3. Save the next node before changing links, then advance the pointers while preserving the list structure.
 *
 * Dry run
 * Example from main: 1->2->3 -> 2->1->3.
 * 1. Initialize the pointers described in the approach.
 * 2. Save the next node before changing a link or moving a pointer.
 * 3. Update the links and advance until the required node or the end is reached.
 * 4. Return the resulting head or answer.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P73_SwapNodesInPairs {
    public static ListNode swapPairs(ListNode h) {
        ListNode d = new ListNode(0, h);
        for (ListNode p = d; p.next != null && p.next.next != null; ) {
            ListNode a = p.next, b = a.next;
            a.next = b.next;
            b.next = a;
            p.next = b;
            p = a;
        }
        return d.next;
    }

    public static void main(String[] args) {
        System.out.println(swapPairs(new ListNode(1, new ListNode(2))).val);
    }
}
