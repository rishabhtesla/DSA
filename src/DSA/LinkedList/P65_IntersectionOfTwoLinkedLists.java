package DSA.LinkedList;

/**
 * ============================================================================
 * [65 / 65] - INTERSECTION OF TWO LINKED LISTS (LeetCode 160)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Given the heads of two singly linked-lists `headA` and `headB`, return the node
 * at which the two lists intersect. If the two linked lists have no intersection, return `null`.
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Naive / Hash Set Approach:
 *    - Traverse `headA` and store all node references in a `HashSet`. Then traverse `headB`
 *      and check if any node is already in the set.
 *    - Valid, but requires `O(m + n)` auxiliary space.
 *
 * 2. Optimal Two-Runner Path Equalizer Approach (O(m + n) Time | O(1) Space):
 *    - **The Core Insight:** Let list A have a unique prefix length `a` and list B have `b`,
 *      followed by a shared intersecting tail of length `c`.
 *    - Total length of path A is `a + c`, and path B is `b + c`.
 *    - If pointer `pA` traverses list A and then switches to `headB`, while pointer `pB` traverses
 *      list B and then switches to `headA`, both pointers will travel exactly `a + c + b` steps!
 *    - By equalizing the path lengths, both runners are guaranteed to land on the exact intersection node
 *      (or both hit `null` simultaneously if no intersection exists) at the end of their second pass.
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(m + n) - At most two passes per pointer before meeting or concluding non-intersection.
 * - Space: O(1) - Constant auxiliary space using only two reference pointers.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE:
 * - List A: `4 -> 1 -> 8 -> 4 -> 5`
 * - List B: `5 -> 6 -> 1 -> 8 -> 4 -> 5`
 * - Intersection Node Value: `8`
 *
 * VISUAL DRY RUN:
 *   - `pA` traverses list A, hits `null`, and redirects to `headB`.
 *   - `pB` traverses list B, hits `null`, and redirects to `headA`.
 *   - Because they switch paths, the length discrepancy between the two independent heads
 *     is perfectly cancelled out. They meet at node `8` simultaneously.
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why does switching heads guarantee that `pA` and `pB` will eventually meet?
 *    (Mathematically, `(lengthA + lengthB)` equals `(lengthB + lengthA)`.
 *    Thus, both pointers travel the exact same cumulative distance before aligning at the intersection node.)
 * 2. What happens if the two lists do not intersect at all?
 *    (Both pointers will reach the end of their second traversal (`null`) at the exact same iteration,
 *    terminating the loop and returning `null` safely.)
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

        // Traverse both lists; when a pointer reaches the end, switch to the other list's head
        while (pA != pB) {
            pA = (pA == null) ? headB : pA.next;
            pB = (pB == null) ? headA : pB.next;
        }

        // Returns either the intersecting node or null if no intersection exists
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