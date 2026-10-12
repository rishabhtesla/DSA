package DSA.LinkedList;

/**
 * ============================================================================
 * [61 / 65] - ROTATE LIST (LeetCode 61)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Given the `head` of a linked list, rotate the list to the right by `k` places.
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Naive / Repeated Single-Step Rotation Approach:
 *    - Detach the tail and move it to the front `k` times.
 *    - Inefficient: takes `O(n * k)` time, which TLEs (Time Limit Exceeded) if `k` is very large.
 *
 * 2. Optimal Ring Closure & Modular Shift Approach (O(n) Time | O(1) Space):
 *    - **The Core Insight:** Rotating a linked list is equivalent to closing it into a ring,
 *      finding the new break point, and opening it.
 *    - **Step 1:** Traverse the list once to count its total length `n` and capture the reference to the `tail` node.
 *    - **Step 2:** Form a circular ring by connecting the tail back to the head: `tail.next = head`.
 *    - **Step 3:** Optimize rotations using modulo arithmetic: `k = k % n`. If `k == 0`, no rotation is needed.
 *    - **Step 4:** The new tail will be located exactly at step `n - k` starting from the old head.
 *      Walk `newTail` to that position, extract `newHead = newTail.next`, and break the ring (`newTail.next = null`).
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(n) - One pass to find the tail/length, and a second partial pass to locate the new tail.
 * - Space: O(1) - In-place pointer updates require constant auxiliary space.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE:
 * - List: `1 -> 2 -> 3 -> 4 -> 5`, `k = 2`
 * - Result: `4 -> 5 -> 1 -> 2 -> 3`
 *
 * VISUAL DRY RUN (for `k = 2`):
 *   - Length `n = 5`, tail is at `5`.
 *   - Tail connects to head: forms a circle `1 -> 2 -> 3 -> 4 -> 5 -> (back to 1)`.
 *   - `k %= 5` remains `2`. Steps to new tail = `5 - 2 = 3`.
 *   - Walk `newTail` 3 steps from the tail (ends up at node `3`).
 *   - `newHead` becomes `newTail.next` (node `4`). Ring is broken at node `3` (`newTail.next = null`).
 *   - Returns `4 -> 5 -> 1 -> 2 -> 3`.
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why is computing `k = k % n` crucial?
 *    (If `k` is larger than the length of the list, rotating by `k` is identical to rotating by `k % n`.
 *    It prevents redundant full-circle traversals when `k` is extremely large.)
 * 2. What edge cases are handled upfront?
 *    (A `null` head, a single-node list (`head.next == null`), or `k = 0` instantly short-circuits
 *    and returns the original list unmodified.)
 */
public class P61_RotateList {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    public static ListNode rotateRight(ListNode head, int k) {
        if (head == null || head.next == null || k == 0) return head;

        // Step 1: Find the length of the list and locate the tail node
        int n = 1;
        ListNode tail = head;
        while (tail.next != null) {
            tail = tail.next;
            n++;
        }

        // Step 2: Form a circular ring by connecting tail to head
        tail.next = head;

        // Step 3: Compute effective rotations and find the new tail position
        k %= n;
        int stepsToNewTail = n - k;
        ListNode newTail = tail;
        while (stepsToNewTail > 0) {
            newTail = newTail.next;
            stepsToNewTail--;
        }

        // Step 4: Break the circle to form the rotated list
        ListNode newHead = newTail.next;
        newTail.next = null;

        return newHead;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);

        ListNode res = rotateRight(head, 2);
        System.out.print("P61 Output: ");
        while (res != null) {
            System.out.print(res.val + (res.next != null ? " -> " : ""));
            res = res.next;
        }
        System.out.println(); // Expected: 4 -> 5 -> 1 -> 2 -> 3
    }
}