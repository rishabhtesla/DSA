package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P65: INTERSECTION OF TWO LINKED LISTS
 *
 * Question
 * Find the node reference where two singly linked lists intersect.
 *
 * Example
 * A and B both enter shared node 7 -> return that node.
 *
 * Constraints
 * 0 <= each length <= 3*10^4; intersection is by reference, not value.
 *
 * Approach used by edSlash
 * 1. Advance two pointers; when one ends, restart it at the other head so path lengths equalize.
 * 2. Use pointers to represent the already-processed part and the nodes that still need to be visited.
 * 3. Save the next node before changing links, then advance the pointers while preserving the list structure.
 *
 * Dry run
 * Example from main: A and B both enter shared node 7 -> return that node.
 * 1. Initialize the pointers described in the approach.
 * 2. Save the next node before changing a link or moving a pointer.
 * 3. Update the links and advance until the required node or the end is reached.
 * 4. Return the resulting head or answer.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P65_IntersectionOfTwoLinkedLists {
    public static ListNode getIntersectionNode(ListNode firstHead, ListNode secondHead) {
        ListNode p = firstHead, q = secondHead;
        while (p != q) {
            p = p == null ? secondHead : p.next;
            q = q == null ? firstHead : q.next;
        }
        return p;
    }

    public static void main(String[] args) {
        ListNode x = new ListNode(7);
        System.out.println(getIntersectionNode(x, x).val);
    }
}
