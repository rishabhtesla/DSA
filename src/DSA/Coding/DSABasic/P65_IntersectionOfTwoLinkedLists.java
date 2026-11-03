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
 *   Two-pointer visual:
 *     pointer A walks list A, pointer B walks list B
 *     when a pointer reaches null, redirect it to the other list
 *     both pointers then travel the same total distance and meet at node 7
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
