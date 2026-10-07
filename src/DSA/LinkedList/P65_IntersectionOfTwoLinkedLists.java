package DSA.LinkedList;

/**
 * ============================================================================
 * [65 / 65] - INTERSECTION OF TWO LINKED LISTS (LeetCode 160)
 * ============================================================================
 * 
 * PROBLEM:
 *   Given the heads of two singly linked-lists headA and headB, return the node
 *   at which the two lists intersect. If they do not intersect, return null.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - The Two-Runner Path Equalizer:
 *     Let path A have length `a + c` and path B have length `b + c`, where `c` is common tail.
 *     If pointer `pA` traverses list A then jumps to headB, and pointer `pB` traverses
 *     list B then jumps to headA:
 *     Both traverse exactly `(a + c + b)` steps!
 *     They will either collide at the intersection node or both terminate at `null`.
 *
 * COMPLEXITY:
 *   - Time:  O(m + n) - At most two passes per pointer.
 *   - Space: O(1) - Two reference pointers.
 *
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 */
public class P65_IntersectionOfTwoLinkedLists {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    public static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) return null;

        ListNode pA = headA;
        ListNode pB = headB;

        while (pA != pB) {
            pA = (pA == null) ? headB : pA.next;
            pB = (pB == null) ? headA : pB.next;
        }

        return pA;
    }

    public static void main(String[] args) {
        ListNode common = new ListNode(8);
        common.next = new ListNode(4);
        common.next.next = new ListNode(5);

        ListNode headA = new ListNode(4);
        headA.next = new ListNode(1);
        headA.next.next = common;

        ListNode headB = new ListNode(5);
        headB.next = new ListNode(6);
        headB.next.next = new ListNode(1);
        headB.next.next.next = common;

        ListNode intersection = getIntersectionNode(headA, headB);
        System.out.println("P65 Output: " + (intersection != null ? intersection.val : "null"));
        // Expected: 8
    }
}