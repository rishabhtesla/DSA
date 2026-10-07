package DSA.Coding.DSABasic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * Shared Definition for Singly-Linked List Node.
 */


/**
 * PROBLEM STATEMENT:
 * You are given the head of a non-empty linked list representing a non-negative integer [00:00:22].
 * Return the head of the linked list after doubling its value [00:00:52].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: head = [1, 8, 9] [00:00:30]
 * - Number represented = 189 [00:00:38].
 * - Doubled value = 189 * 2 = 378 [00:01:13].
 * - Output linked list = [3, 7, 8] [00:01:24].
 * 
 * Example 2: head = [9, 9, 9]
 * - Number represented = 999.
 * - Doubled value = 999 * 2 = 1998.
 * - Output linked list = [1, 9, 9, 8].
 * 
 * Example 3: head = [4, 3, 9, 2] (Simulated inside video explanation [00:18:10, 00:23:07])
 * - Step 1: Reverse input list -> 2 -> 9 -> 3 -> 4 [00:18:28]
 * - Step 2: Double with carry tracking [00:19:02, 00:22:10]:
 *   - Node 2: 2 + 2 + 0 = 4 -> digit 4, carry 0 [00:19:11]
 *   - Node 9: 9 + 9 + 0 = 18 -> digit 8, carry 1 [00:20:05]
 *   - Node 3: 3 + 3 + 1 = 7 -> digit 7, carry 0 [00:21:08]
 *   - Node 4: 4 + 4 + 0 = 8 -> digit 8, carry 0 [00:21:50]
 * - Intermediate result list: 4 -> 8 -> 7 -> 8 [00:22:18]
 * - Step 3: Reverse result list -> 8 -> 7 -> 8 -> 4 [00:22:36]
 * - Result: [8, 7, 8, 4] (4392 * 2 = 8784) [00:23:00]
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Double Reversal with Carry Accumulation):
 * • Pointer & Helper Initialization:
 *   - `reverseList(ListNode head)` helper reverses list using 3 pointers (`curr`, `prev`, `temp`) [00:09:24, 00:10:19].
 *   - `newHead = reverseList(head)` turns input least-significant-digit first [00:11:25].
 *   - `dummy = new ListNode(-1)` serves as construction anchor [00:11:35].
 *   - `ans = dummy` captures initial dummy node anchor [00:11:53].
 *   - Carry variable `carry = 0` [00:12:29].
 * • Condition Boundaries:
 *   - Reversal loop condition: `while (curr != null)` [00:10:00].
 *   - Doubling loop condition: `while (newHead != null)` [00:12:00].
 *   - Single digit extraction: `d = sum % 10` [00:12:42].
 *   - Carry update: `carry = sum / 10` [00:12:51].
 *   - Post-loop leftover carry check: `if (carry > 0)` append extra node storing `carry` [00:14:19, 00:14:33].
 * • Operational Steps:
 *   1. Reverse original linked list using iterative 3-pointer reversal helper [00:11:25].
 *   2. Instantiate `dummy` node and `carry = 0` tracker [00:11:35, 00:12:29].
 *   3. Iterate reversed list: `sum = newHead.val + newHead.val + carry` [00:12:12, 00:16:44].
 *   4. Compute single digit `d = sum % 10` and carry `carry = sum / 10` [00:12:42, 00:12:51].
 *   5. Create node with `d`, link to `dummy.next`, advance `dummy` and `newHead` pointers [00:13:41, 00:14:07].
 *   6. Check for leftover trailing carry after loop terminates [00:14:19].
 *   7. Reverse generated result list starting at `ans.next` (`reverseList(ans.next)`) [00:15:38, 00:15:50].
 *   8. Return final reversed head node [00:15:56].
 * • Time Complexity: O(n) - Three linear passes over the linked list (reverse input, process doubling, reverse output).
 * • Space Complexity: O(n) auxiliary space - New linked list created for output result.
 * • LOGIC BEHIND THIS APPROACH:
 *   Reversing the list positions units digit at head, enabling linear carry propagation [00:02:11]. 
 *   Doubling `val + val + carry` mimics grade-school multiplication [00:03:41, 00:12:12]. 
 *   Reversing the final result restores most-significant-digit ordering [00:04:06, 00:15:38].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 1: Double Reversal - head = [1, 8, 9]):
 * Step 1: Reverse Input List:
 * Original: 1 -> 8 -> 9
 * Reversed: 9 -> 8 -> 1 (newHead) [00:02:22]
 * 
 * Step 2: Doubling Iteration:
 * - Node 9: sum = 9 + 9 + 0 = 18 -> digit = 8, carry = 1. dummy -> Node(8) [00:13:03]
 * - Node 8: sum = 8 + 8 + 1 = 17 -> digit = 7, carry = 1. dummy -> Node(8) -> Node(7)
 * - Node 1: sum = 1 + 1 + 1 = 3  -> digit = 3, carry = 0. dummy -> Node(8) -> Node(7) -> Node(3)
 * Un-reversed intermediate result = 8 -> 7 -> 3 [00:04:00].
 * 
 * Step 3: Reverse Result List:
 * Reverse 8 -> 7 -> 3 -> 3 -> 7 -> 8 [00:04:13].
 * Return [3, 7, 8] [00:01:24].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 2: Single Pass Stack LIFO Strategy - head = [1, 8, 9]):
 * Push all nodes to Stack: [1, 8, 9] (top is 9)
 * Pop & Double:
 * - Pop 9: 9*2 + 0 = 18 -> create node(8), carry = 1, head = node(8)
 * - Pop 8: 8*2 + 1 = 17 -> create node(7), node(7).next = head, carry = 1, head = node(7)
 * - Pop 1: 1*2 + 1 = 3  -> create node(3), node(3).next = head, carry = 0, head = node(3)
 * Output = 3 -> 7 -> 8.
 */
public class DoubleNumberAsLinkedList {

    // APPROACH 1: Double Reversal with Carry Accumulation (Anchor Strategy)
    public static ListNode doubleItOptimal(ListNode head) {
        if (head == null) return null;

        // Step 1: Reverse input list to start from least significant digit [00:02:11, 00:11:25]
        ListNode newHead = reverseList(head);

        // Dummy node initialization [00:11:35]
        ListNode dummy = new ListNode(-1);
        ListNode ans = dummy; // Save anchor reference [00:11:53]

        int carry = 0; // Carry tracker [00:12:29]

        // Step 2: Traverse reversed list and double each digit [00:12:00]
        while (newHead != null) {
            // Compute sum by doubling node value plus carry [00:12:12, 00:16:44]
            int sum = newHead.val + newHead.val + carry;

            int d = sum % 10;     // Extract units digit [00:12:42]
            carry = sum / 10;     // Extract tens carry [00:12:51]

            // Create new result node and append [00:13:41, 00:13:49]
            ListNode temp = new ListNode(d);
            dummy.next = temp;
            dummy = dummy.next;   // Advance construction pointer [00:13:59]

            newHead = newHead.next; // Advance input list pointer [00:14:07]
        }

        // Post-loop check: append extra leading digit if carry remains [00:14:19, 00:14:33]
        if (carry > 0) {
            ListNode temp = new ListNode(carry);
            dummy.next = temp;
        }

        // Step 3: Reverse result list back to original order [00:04:06, 00:15:38]
        ans = reverseList(ans.next);

        return ans; // Return head of doubled linked list [00:15:56]
    }

    // Helper method to reverse a singly-linked list iteratively in O(n) time [00:09:24]
    private static ListNode reverseList(ListNode head) {
        ListNode current = head; // Current node iterator [00:09:47]
        ListNode previous = null; // Previous node pointer [00:09:56]

        while (current != null) {
            ListNode temp = current.next; // Store next node reference [00:10:10]
            current.next = previous;      // Reverse direction link [00:10:19]
            previous = current;           // Advance previous pointer [00:10:25]
            current = temp;               // Advance current pointer [00:10:28]
        }

        return previous; // New head of reversed list [00:10:39]
    }

    // APPROACH 2: Single Pass Stack LIFO Strategy
    // Uses an explicit Stack to process nodes from right-to-left without modifying list pointers.
    public static ListNode doubleItStack(ListNode head) {
        if (head == null) return null;

        Stack<ListNode> stack = new Stack<>();
        ListNode curr = head;

        // Push all nodes to stack
        while (curr != null) {
            stack.push(curr);
            curr = curr.next;
        }

        int carry = 0;
        ListNode newTail = null;

        // Pop from stack and construct new list in reverse prepending order
        while (!stack.isEmpty()) {
            ListNode node = stack.pop();
            int sum = node.val * 2 + carry;
            
            ListNode newNode = new ListNode(sum % 10);
            newNode.next = newTail;
            newTail = newNode;
            
            carry = sum / 10;
        }

        // Prepend leftover carry node if present
        if (carry > 0) {
            ListNode carryNode = new ListNode(carry);
            carryNode.next = newTail;
            newTail = carryNode;
        }

        return newTail;
    }

    // Helper method to build a linked list from an array
    private static ListNode buildList(int[] values) {
        if (values == null || values.length == 0) return null;
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        for (int v : values) {
            curr.next = new ListNode(v);
            curr = curr.next;
        }
        return dummy.next;
    }

    // Helper method to convert linked list to standard List for assertions
    private static List<Integer> toList(ListNode head) {
        List<Integer> result = new ArrayList<>();
        while (head != null) {
            result.add(head.val);
            head = head.next;
        }
        return result;
    }

    // Comprehensive Verification Test Suite
    public static void main(String[] args) {
        System.out.println("================ RUNNING DSA TEST SUITE ================\n");

        // --- TEST CASE 1 (Standard Doubling 189 * 2 = 378) ---
        ListNode l1_1 = buildList(new int[]{1, 8, 9});
        ListNode l1_2 = buildList(new int[]{1, 8, 9});

        ListNode res1_1 = doubleItOptimal(l1_1);
        ListNode res1_2 = doubleItStack(l1_2);

        System.out.println("Test Case 1: head = [1, 8, 9]");
        System.out.println("Approach 1 (Double Reversal) Result: " + toList(res1_1));
        System.out.println("Approach 2 (Stack LIFO)      Result: " + toList(res1_2));
        boolean check1 = toList(res1_1).equals(Arrays.asList(3, 7, 8)) &&
                         toList(res1_2).equals(Arrays.asList(3, 7, 8));
        System.out.println("Verification: " + (check1 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (Carry Expansion 999 * 2 = 1998) ---
        ListNode l2_1 = buildList(new int[]{9, 9, 9});
        ListNode l2_2 = buildList(new int[]{9, 9, 9});

        ListNode res2_1 = doubleItOptimal(l2_1);
        ListNode res2_2 = doubleItStack(l2_2);

        System.out.println("Test Case 2: head = [9, 9, 9]");
        System.out.println("Approach 1 (Double Reversal) Result: " + toList(res2_1));
        System.out.println("Approach 2 (Stack LIFO)      Result: " + toList(res2_2));
        boolean check2 = toList(res2_1).equals(Arrays.asList(1, 9, 9, 8)) &&
                         toList(res2_2).equals(Arrays.asList(1, 9, 9, 8));
        System.out.println("Verification: " + (check2 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 3 (From Video Board Explanation 4392 * 2 = 8784) ---
        ListNode l3_1 = buildList(new int[]{4, 3, 9, 2});
        ListNode l3_2 = buildList(new int[]{4, 3, 9, 2});

        ListNode res3_1 = doubleItOptimal(l3_1);
        ListNode res3_2 = doubleItStack(l3_2);

        System.out.println("Test Case 3: head = [4, 3, 9, 2]");
        System.out.println("Approach 1 (Double Reversal) Result: " + toList(res3_1));
        System.out.println("Approach 2 (Stack LIFO)      Result: " + toList(res3_2));
        boolean check3 = toList(res3_1).equals(Arrays.asList(8, 7, 8, 4)) &&
                         toList(res3_2).equals(Arrays.asList(8, 7, 8, 4));
        System.out.println("Verification: " + (check3 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}