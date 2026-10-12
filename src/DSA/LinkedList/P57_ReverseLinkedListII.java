package DSA.LinkedList;

/**
 * ============================================================================
 * [57 / 65] - REVERSE LINKED LIST II (LeetCode 92)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Given the `head` of a singly linked list and two integers `left` and `right` (1-indexed)
 * where `left <= right`, reverse the nodes of the list from position `left` to `right`,
 * and return the modified head.
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Naive / Extra Storage Approach:
 *    - Extract elements between `left` and `right` into an array or list, reverse them in memory,
 *      and write them back. (Inefficient: uses O(k) extra space where `k = right - left + 1`).
 *
 * 2. Optimal Head Insertion within Sublist (One-Pass O(n) Time | O(1) Space):
 *    - **The Core Insight:** Instead of multi-pass pointer reversals or slicing, we can perform an
 *      in-place pointer rotation by repeatedly shifting nodes to the front of the sublist.
 *    - **Step 1:** Use a `dummy` node to safely handle edge cases where `left = 1`. Advance a `prev`
 *      pointer to the node right before position `left` (i.e., at index `left - 1`).
 *    - **Step 2:** Let `curr = prev.next`. This node will remain fixed at the tail of the reversed sublist.
 *    - **Step 3:** Perform head-insertion `right - left` times by taking `curr.next` and moving it
 *      directly to the front of the reversed section (right after `prev`).
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(n) - Single linear pass to locate the sublist start and reverse elements in-place.
 * - Space: O(1) - Constant auxiliary pointer memory.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE:
 * - List: `1 -> 2 -> 3 -> 4 -> 5`, `left = 2`, `right = 4`
 * - Result: `1 -> 4 -> 3 -> 2 -> 5`
 *
 * VISUAL DRY RUN (for `left = 2, right = 4`):
 *   - `prev` stops at node `1`. `curr` points to node `2`.
 *   - Iteration 1: Node `3` is plucked and inserted right after `prev` (`1`). Sublist becomes `1 -> 3 -> 2 -> 4 -> 5`.
 *   - Iteration 2: Node `4` is plucked and inserted right after `prev` (`1`). Sublist becomes `1 -> 4 -> 3 -> 2 -> 5`.
 *   - Reversal complete. Returns `dummy.next`.
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why does head-insertion inside the sublist achieve reversal in a single pass?
 *    (By keeping `curr` stationary while successively pulling the next immediate node to the front of the sublist,
 *    we invert the sequence order on the fly without needing a second pointer pass.)
 * 2. What edge cases are handled cleanly?
 *    (When `left == right`, the loops are bypassed instantly and the original list is returned unmodified.
 *    When `left = 1`, the dummy node allows `prev` to anchor correctly at the true beginning of the list.)
 */
public class P57_ReverseLinkedListII {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    public static ListNode reverseBetween(ListNode head, int left, int right) {
        if (head == null || left == right) return head;

        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;

        // Reach the node right before 'left'
        for (int i = 1; i < left; i++) {
            prev = prev.next;
        }

        ListNode curr = prev.next;

        // Repeatedly move curr.next to the front of the reversed sublist
        for (int i = 0; i < right - left; i++) {
            ListNode next = curr.next;
            curr.next = next.next;
            next.next = prev.next;
            prev.next = next;
        }

        return dummy.next;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);

        ListNode res = reverseBetween(head, 2, 4);
        System.out.print("P57 Output: ");
        while (res != null) {
            System.out.print(res.val + (res.next != null ? " -> " : ""));
            res = res.next;
        }
        System.out.println(); // Expected: 1 -> 4 -> 3 -> 2 -> 5
    }
}