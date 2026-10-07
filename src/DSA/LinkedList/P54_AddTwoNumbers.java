package DSA.LinkedList;

/**
 * ============================================================================
 * [54 / 65] - ADD TWO NUMBERS (LeetCode 2)
 * ============================================================================
 * 
 * PROBLEM:
 *   You are given two non-empty linked lists representing two non-negative integers.
 *   The digits are stored in reverse order, and each node contains a single digit.
 *   Add the two numbers and return the sum as a linked list in reverse order.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Reverse order actually simplifies addition: the heads represent the least
 *     significant digits (ones place).
 *   - Use a `dummy` node to construct the result cleanly.
 *   - Loop while `l1 != null || l2 != null || carry != 0`.
 *   - At each step: `sum = carry + (l1 != null ? l1.val : 0) + (l2 != null ? l2.val : 0)`.
 *     New node val is `sum % 10`, updated carry is `sum / 10`.
 *
 * COMPLEXITY:
 *   - Time:  O(max(m, n)) - Traversing the longer list.
 *   - Space: O(max(m, n)) - Output list nodes.
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