package DSA.LinkedList;

/**
 * ============================================================================
 * [59 / 65] - REMOVE NTH NODE FROM END OF LIST (LeetCode 19)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Given the `head` of a linked list, remove the $nth$ node from the end of the list
 * and return its modified head.
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Two-Pass Length Calculation Approach:
 *    - Traverse the list once to calculate its total length $L$, then perform a second pass
 *      to reach the node at index $L - n$.
 *    - Valid, but requires two full iterations over the list.
 *
 * 2. Optimal Two-Pointer Gap Strategy (Single-Pass O(n) Time | O(1) Space):
 *    - **The Core Insight:** We can find the target node from the end in a **single pass** by maintaining
 *      a fixed gap of $n$ nodes between two pointers (`slow` and `fast`).
 *    - **Step 1:** Introduce a `dummy` node pointing to `head` to safely handle edge cases (e.g., removing the very first head node).
 *    - **Step 2:** Advance the `fast` pointer forward by $n + 1$ steps starting from `dummy`. This creates a precise gap
 *      of $n$ nodes between `slow` (still at `dummy`) and `fast`.
 *    - **Step 3:** Advance both `slow` and `fast` concurrently at the same speed until `fast` reaches `null`.
 *    - **Step 4:** When `fast` hits `null`, `slow` will be positioned **exactly at the node right before the target node**.
 *    - **Step 5:** Delete the target node by updating the pointer: `slow.next = slow.next.next`.
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(n) - Single linear pass through the linked list.
 * - Space: O(1) - Constant auxiliary memory using only reference pointers.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE:
 * - List: `1 -> 2 -> 3 -> 4 -> 5`, `n = 2`
 * - Result: `1 -> 2 -> 3 -> 5`
 *
 * VISUAL DRY RUN (for `n = 2`):
 *   - `dummy` points to `1`. `fast` advances $2 + 1 = 3$ steps ahead.
 *   - `slow` and `fast` move together until `fast` reaches `null`.
 *   - `slow` lands on node `3`.
 *   - `slow.next` (node `4`, which is 2nd from the end) is bypassed via `slow.next = slow.next.next`.
 *   - Returns `dummy.next` (`1 -> 2 -> 3 -> 5`).
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why do we advance `fast` by $n + 1$ steps instead of $n$ steps?
 *    (Advancing by $n + 1$ ensures that `slow` stops at the node *preceding* the target node rather than
 *    the target node itself, making the deletion operation straightforward via pointer reassignment.)
 * 2. How does the `dummy` node prevent null pointer exceptions?
 *    (It handles edge cases where we need to remove the head node of a list, allowing uniform logic
 *    without special-case `if` statements.)
 */
public class P59_RemoveNthNodeFromEndOfList {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    public static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode fast = dummy;
        ListNode slow = dummy;

        // Advance fast pointer by n + 1 steps to create the required gap
        for (int i = 0; i <= n; i++) {
            fast = fast.next;
        }

        // Move both pointers until fast reaches the end of the list
        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }

        // Bypass and delete the target node
        slow.next = slow.next.next;
        return dummy.next;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);

        ListNode res = removeNthFromEnd(head, 2);
        System.out.print("P59 Output: ");
        while (res != null) {
            System.out.print(res.val + (res.next != null ? " -> " : ""));
            res = res.next;
        }
        System.out.println(); // Expected: 1 -> 2 -> 3 -> 5
    }
}