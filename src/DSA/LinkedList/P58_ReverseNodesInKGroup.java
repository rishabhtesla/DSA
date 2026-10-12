package DSA.LinkedList;

/**
 * ============================================================================
 * [58 / 65] - REVERSE NODES IN K-GROUP (LeetCode 25)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Given the `head` of a linked list, reverse the nodes of the list `k` at a time,
 * and return the modified list. If the number of nodes is not a multiple of `k`,
 * leave the remaining nodes as-is.
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Recursive Approach:
 *    - Recursively reverse the first `k` nodes, then link the result to the recursive call of the remaining list.
 *    - Valid, but uses O(n / k) call stack space.
 *
 * 2. Optimal Iterative Lookahead & Splicing Approach (O(n) Time | O(1) Space):
 *    - **The Core Insight:** We can reverse groups iteratively without recursion by pre-checking if a full
 *      group of `k` nodes exists before performing any mutations.
 *    - **Step 1 (Lookahead):** Use a helper function `getKth()` to advance `k` steps. If fewer than `k` nodes
 *      remain (`kth == null`), stop and leave the remaining tail unchanged.
 *    - **Step 2 (In-Place Reversal):** Reverse the `k` nodes iteratively using standard pointer rotation
 *      while anchoring to the start of the next group (`groupNext`).
 *    - **Step 3 (Reconnection):** Splice the newly reversed sublist back between `groupPrev` and `groupNext`,
 *      then shift `groupPrev` forward to the end of the current reversed group for the next iteration.
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(n) - Every node is checked once by the lookahead helper and reversed once.
 * - Space: O(1) - Iterative pointer adjustments require constant extra memory space.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE:
 * - List: `1 -> 2 -> 3 -> 4 -> 5`, `k = 2`
 * - Result: `2 -> 1 -> 4 -> 3 -> 5`
 *
 * VISUAL DRY RUN (for `k = 2`):
 *   - Lookahead finds first group `[1, 2]`. Reverses to `[2, 1]`. `groupPrev` anchors at `1`.
 *   - Lookahead finds second group `[3, 4]`. Reverses to `[4, 3]`. `groupPrev` anchors at `3`.
 *   - Lookahead on remaining node `[5]` returns `null` (fewer than 2 nodes remain) -> stops and leaves node `5` as-is.
 *   - Final list: `2 -> 1 -> 4 -> 3 -> 5`.
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why is the lookahead check (`getKth`) critical before reversing?
 *    (It ensures that we don't accidentally reverse a partial tail group when the total node count
 *    is not a multiple of `k`, satisfying the problem requirement to leave remaining nodes as-is.)
 * 2. What edge cases are handled automatically?
 *    (A `null` head or `k = 1` short-circuits instantly and returns the original list unmodified.)
 */
public class P58_ReverseNodesInKGroup {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    public static ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || k == 1) return head;

        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode groupPrev = dummy;

        while (true) {
            // Check if there are at least k nodes remaining to reverse
            ListNode kth = getKth(groupPrev, k);
            if (kth == null) break;

            ListNode groupNext = kth.next;
            ListNode prev = groupNext;
            ListNode curr = groupPrev.next;

            // Reverse the current k-group elements
            while (curr != groupNext) {
                ListNode tmp = curr.next;
                curr.next = prev;
                prev = curr;
                curr = tmp;
            }

            ListNode tmp = groupPrev.next;
            groupPrev.next = kth;
            groupPrev = tmp;
        }

        return dummy.next;
    }

    private static ListNode getKth(ListNode curr, int k) {
        while (curr != null && k > 0) {
            curr = curr.next;
            k--;
        }
        return curr;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);

        ListNode res = reverseKGroup(head, 2);
        System.out.print("P58 Output: ");
        while (res != null) {
            System.out.print(res.val + (res.next != null ? " -> " : ""));
            res = res.next;
        }
        System.out.println(); // Expected: 2 -> 1 -> 4 -> 3 -> 5
    }
}