package DSA.LinkedList;

/**
 * ============================================================================
 * [54 / 65] - ADD TWO NUMBERS (LeetCode 2)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * You are given two non-empty linked lists representing two non-negative integers.
 * The digits are stored in reverse order, and each node contains a single digit.
 * Add the two numbers and return the sum as a linked list in reverse order.
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Naive / Integer Conversion Approach:
 *    - Convert linked lists into numbers, add them, and convert back.
 *    - Fails for large inputs due to integer overflow (exceeding `long` limits).
 *
 * 2. Optimal Digit-by-Digit Column Addition Approach (O(max(m, n)) Time | O(max(m, n)) Space):
 *    - **The Core Insight:** Storing digits in *reverse order* actually simplifies addition because
 *      the heads represent the least significant digits (ones place), matching elementary school column addition.
 *    - **Step 1:** Use a `dummy` head node to cleanly handle the resulting linked list construction without special-case logic.
 *    - **Step 2:** Loop while `l1 != null || l2 != null || carry != 0`.
 *    - **Step 3:** At each step, compute `sum = carry + (l1 != null ? l1.val : 0) + (l2 != null ? l2.val : 0)`.
 *    - **Step 4:** Extract the new node value as `sum % 10` and update the carry as `sum / 10`.
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(max(m, n)) where `m` and `n` are the lengths of lists `l1` and `l2`, traversing the longer list.
 * - Space: O(max(m, n)) to store the newly generated output linked list nodes.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE:
 * - `l1 = 2 -> 4 -> 3` (represents 342)
 * - `l2 = 5 -> 6 -> 4` (represents 465)
 * - Result = `7 -> 0 -> 8` (represents 807)
 *
 * VISUAL DRY RUN:
 *   - Head addition: `2 + 5 = 7`, carry = `0` -> creates node `7`.
 *   - Next addition: `4 + 6 = 10`, carry = `1` -> creates node `0`, carry becomes `1`.
 *   - Final addition: `3 + 4 + 1 = 8`, carry = `0` -> creates node `8`.
 *   - Returns result list starting at `dummy.next` (`7 -> 0 -> 8`).
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why is storing digits in reverse order ideal for linked lists?
 *    (It allows us to process additions from the least significant digit to the most significant digit
 *    in a single linear pass from left to right, matching the natural traversal direction of singly linked lists.)
 * 2. What edge cases are handled automatically by the loop condition `l1 != null || l2 != null || carry != 0`?
 *    (Lists of uneven lengths and a trailing carry after both lists have been fully traversed are seamlessly handled.)
 */
public class P54_AddTwoNumbers {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        int carry = 0;

        while (l1 != null || l2 != null || carry != 0) {
            int sum = carry;
            if (l1 != null) {
                sum += l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                sum += l2.val;
                l2 = l2.next;
            }

            carry = sum / 10;
            curr.next = new ListNode(sum % 10);
            curr = curr.next;
        }

        return dummy.next;
    }

    public static void main(String[] args) {
        // 342: (2 -> 4 -> 3)
        ListNode l1 = new ListNode(2);
        l1.next = new ListNode(4);
        l1.next.next = new ListNode(3);

        // 465: (5 -> 6 -> 4)
        ListNode l2 = new ListNode(5);
        l2.next = new ListNode(6);
        l2.next.next = new ListNode(4);

        // Sum: 807 -> (7 -> 0 -> 8)
        ListNode res = addTwoNumbers(l1, l2);
        System.out.print("P54 Output: ");
        while (res != null) {
            System.out.print(res.val + (res.next != null ? " -> " : ""));
            res = res.next;
        }
        System.out.println(); // Expected: 7 -> 0 -> 8
    }
}