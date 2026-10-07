package DSA.Coding.DSABasic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Shared Definition for Singly-Linked List Node.


/**
 * PROBLEM STATEMENT:
 * You are given two non-empty linked lists representing two non-negative integers [00:00:23].
 * The digits are stored in reverse order, and each of their nodes contains a single digit [00:00:27].
 * Add the two numbers and return the sum as a linked list [00:00:54].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: l1 = [2, 4, 3], l2 = [5, 6, 4] [00:00:34, 00:01:09]
 * - Digit Addition:
 *   - ones: 2 + 5 = 7 (carry = 0) [00:01:00]
 *   - tens: 4 + 6 = 10 -> digit = 0, carry = 1 [00:01:03]
 *   - hundreds: 3 + 4 + 1(carry) = 8 (carry = 0) [00:01:07]
 * - Result: [7, 0, 8] (represents 342 + 465 = 807 in reverse order) [00:01:13].
 * 
 * Example 2: l1 = [4, 7, 3, 9], l2 = [1, 6, 5, 7] (Simulated inside video explanation [00:16:58, 00:21:48])
 * - Digits processing:
 *   - 4 + 1 + 0 = 5 -> append 5 (carry = 0) [00:17:58]
 *   - 7 + 6 + 0 = 13 -> append 3 (carry = 1) [00:18:52]
 *   - 3 + 5 + 1 = 9 -> append 9 (carry = 0) [00:19:44]
 *   - 9 + 7 + 0 = 16 -> append 6 (carry = 1) [00:20:30]
 *   - Loop ends, leftover carry = 1 -> append trailing node 1 [00:21:12].
 * - Result: [5, 3, 9, 6, 1] [00:21:33]
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Dummy Anchor with Dynamic Carry Propagation):
 * • Pointer & Variable Initialization:
 *   - Early null checks: if `l1 == null` return `l2`; if `l2 == null` return `l1` [00:07:59, 00:08:19].
 *   - `dummy = new ListNode(-1)` serves as construction anchor [00:02:46, 00:08:40].
 *   - `ans = dummy` captures starting reference for final return [00:06:05, 00:09:15].
 *   - Iterators `pointer1 = l1`, `pointer2 = l2`, and tracking scalar `carry = 0` [00:09:29, 00:11:42].
 * • Condition Boundaries:
 *   - Main traversal loop condition: `while (pointer1 != null || pointer2 != null)` [00:09:48, 00:10:10].
 *   - Safe ternary value extraction: if pointer is non-null take `val`; else assume `0` [00:10:37, 00:11:11].
 *   - Carry update: `carry = sum / 10` [00:12:12].
 *   - Digit extraction: `d = sum % 10` [00:11:59].
 *   - Safe pointer advancement: if `pointer != null`, move to `pointer.next`; else keep `null` [00:13:04, 00:13:28].
 *   - Post-loop leftover carry: `if (carry > 0)` append trailing node storing `carry` value [00:14:02, 00:14:18].
 * • Operational Steps:
 *   1. Create `dummy` (-1) node and `ans = dummy` reference [00:08:40, 00:09:15].
 *   2. Iterate as long as at least one list has remaining nodes (`pointer1 != null || pointer2 != null`) [00:09:48].
 *   3. Extract `v1` and `v2` safely using ternary null checks [00:10:37, 00:11:11].
 *   4. Compute total `sum = v1 + v2 + carry` [00:11:34, 00:11:47].
 *   5. Calculate `d = sum % 10` and `carry = sum / 10` [00:11:59, 00:12:12].
 *   6. Create new node with digit `d`, attach to `dummy.next`, advance `dummy = dummy.next` [00:12:18, 00:12:40].
 *   7. Safely advance list iterators [00:13:04, 00:13:28].
 *   8. Append trailing node if `carry > 0` after loop ends [00:14:02, 00:14:18].
 *   9. Return `ans.next` [00:14:25].
 * • Time Complexity: O(max(N, M)) - Linear pass bounded by the longer of the two input lists.
 * • Space Complexity: O(max(N, M)) - Allocated for the new result linked list.
 * • LOGIC BEHIND THIS APPROACH:
 *   Lists represent numbers in reverse order [00:00:27]. 
 *   The logical OR (`||`) loop condition handles lists of unequal lengths without early termination [00:10:10]. 
 *   Defaulting missing nodes to `0` prevents `NullPointerException` errors [00:07:29, 00:10:57]. 
 *   Extracting carry via integer division (`/ 10`) and node digits via modulo (`% 10`) handles high-order numeric overflow seamlessly [00:03:51, 00:12:12].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 1: Dummy Anchor - l1 = [2, 4], l2 = [5, 6, 4]):
 * Initial: dummy(-1), ans = dummy, carry = 0, p1 = 2, p2 = 5 [00:17:28]
 * 
 * Iteration 1:
 * - v1 = 2, v2 = 5
 * - sum = 2 + 5 + 0 = 7 -> d = 7, carry = 0 [00:17:58]
 * - dummy.next = Node(7), dummy = Node(7)
 * - p1 = 4, p2 = 6
 * 
 * Iteration 2:
 * - v1 = 4, v2 = 6
 * - sum = 4 + 6 + 0 = 10 -> d = 0, carry = 1 [00:18:52]
 * - dummy.next = Node(0), dummy = Node(0)
 * - p1 = null, p2 = 4
 * 
 * Iteration 3:
 * - p1 is null -> v1 = 0 (defaulted safely) [00:10:57]
 * - v2 = 4
 * - sum = 0 + 4 + 1 = 5 -> d = 5, carry = 0
 * - dummy.next = Node(5), dummy = Node(5)
 * - p1 = null, p2 = null
 * 
 * Loop terminates. Leftover carry = 0.
 * Return ans.next -> [7, 0, 5].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 2: Recursive Addition Strategy - l1 = [2, 4], l2 = [5, 6]):
 * addHelper(l1, l2, carry = 0):
 * - Frame 1: sum = 2 + 5 + 0 = 7 -> node(7), next = addHelper(4, 6, 0)
 * - Frame 2: sum = 4 + 6 + 0 = 10 -> node(0), next = addHelper(null, null, 1)
 * - Frame 3: l1=null, l2=null, carry=1 -> returns node(1)
 * Unwinds to form [7 -> 0 -> 1].
 */
public class AddTwoNumbers {

    // APPROACH 1: Dummy Anchor with Dynamic Carry Propagation (Anchor Strategy)
    public static ListNode addTwoNumbersOptimal(ListNode l1, ListNode l2) {
        // Base Cases: if either list is null, return the non-null list directly [00:07:59, 00:08:19]
        if (l1 == null) return l2;
        if (l2 == null) return l1;

        // Dummy node initialization [00:08:40]
        ListNode dummy = new ListNode(-1);
        ListNode ans = dummy; // Save anchor reference [00:09:15]

        ListNode pointer1 = l1; // List 1 iterator [00:09:29]
        ListNode pointer2 = l2; // List 2 iterator [00:09:40]

        int carry = 0; // Carry tracker [00:11:42]

        // Loop as long as at least one list contains unprocessed nodes [00:09:48, 00:10:10]
        while (pointer1 != null || pointer2 != null) {
            
            // Extract digit value from l1 safely; default to 0 if null [00:10:37, 00:10:57]
            int val1 = (pointer1 != null) ? pointer1.val : 0;
            
            // Extract digit value from l2 safely; default to 0 if null [00:11:11, 00:11:19]
            int val2 = (pointer2 != null) ? pointer2.val : 0;

            // Compute running digit sum [00:11:34, 00:11:47]
            int sum = val1 + val2 + carry;

            int d = sum % 10;     // Extract single digit for current node [00:11:59]
            carry = sum / 10;     // Extract carry for next position [00:12:12]

            // Create new result node and link to dummy chain [00:12:18, 00:12:40]
            ListNode temp = new ListNode(d);
            dummy.next = temp;
            dummy = dummy.next; // Advance dummy construction pointer [00:12:48]

            // Safely advance list 1 iterator [00:13:04, 00:13:23]
            pointer1 = (pointer1 != null) ? pointer1.next : null;
            
            // Safely advance list 2 iterator [00:13:28, 00:13:39]
            pointer2 = (pointer2 != null) ? pointer2.next : null;
        }

        // Post-loop check: if a leftover carry exists, append an extra digit node [00:14:02, 00:14:18]
        if (carry > 0) {
            ListNode temp = new ListNode(carry);
            dummy.next = temp;
        }

        return ans.next; // Return head skipping placeholder dummy [00:14:25]
    }

    // APPROACH 2: Pure Recursive Addition Strategy
    // Solves digit addition recursively frame-by-frame, passing carry down the call stack.
    public static ListNode addTwoNumbersRecursive(ListNode l1, ListNode l2) {
        return addRecursiveHelper(l1, l2, 0);
    }

    private static ListNode addRecursiveHelper(ListNode l1, ListNode l2, int carry) {
        if (l1 == null && l2 == null && carry == 0) {
            return null;
        }

        int sum = carry;
        if (l1 != null) {
            sum += l1.val;
            l1 = l1.next;
        }
        if (l2 != null) {
            sum += l2.val;
            l2 = l2.next;
        }

        ListNode resultNode = new ListNode(sum % 10);
        resultNode.next = addRecursiveHelper(l1, l2, sum / 10);

        return resultNode;
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

        // --- TEST CASE 1 (Standard Addition 342 + 465 = 807) ---
        ListNode l1_1 = buildList(new int[]{2, 4, 3});
        ListNode l1_2 = buildList(new int[]{5, 6, 4});

        ListNode res1_1 = addTwoNumbersOptimal(l1_1, l1_2);
        ListNode res1_2 = addTwoNumbersRecursive(buildList(new int[]{2, 4, 3}), buildList(new int[]{5, 6, 4}));

        System.out.println("Test Case 1: l1 = [2,4,3], l2 = [5,6,4]");
        System.out.println("Approach 1 (Optimal Loop) Result: " + toList(res1_1));
        System.out.println("Approach 2 (Recursive)    Result: " + toList(res1_2));
        boolean check1 = toList(res1_1).equals(Arrays.asList(7, 0, 8)) &&
                         toList(res1_2).equals(Arrays.asList(7, 0, 8));
        System.out.println("Verification: " + (check1 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (From Video Board Explanation - Carry Overflow) ---
        ListNode l2_1 = buildList(new int[]{4, 7, 3, 9});
        ListNode l2_2 = buildList(new int[]{1, 6, 5, 7});

        ListNode res2_1 = addTwoNumbersOptimal(l2_1, l2_2);
        ListNode res2_2 = addTwoNumbersRecursive(buildList(new int[]{4, 7, 3, 9}), buildList(new int[]{1, 6, 5, 7}));

        System.out.println("Test Case 2: l1 = [4,7,3,9], l2 = [1,6,5,7]");
        System.out.println("Approach 1 (Optimal Loop) Result: " + toList(res2_1));
        System.out.println("Approach 2 (Recursive)    Result: " + toList(res2_2));
        boolean check2 = toList(res2_1).equals(Arrays.asList(5, 3, 9, 6, 1)) &&
                         toList(res2_2).equals(Arrays.asList(5, 3, 9, 6, 1));
        System.out.println("Verification: " + (check2 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 3 (Leftover Carry Final Node 99 + 1 = 100) ---
        ListNode l3_1 = buildList(new int[]{9, 9});
        ListNode l3_2 = buildList(new int[]{1});

        ListNode res3_1 = addTwoNumbersOptimal(l3_1, l3_2);

        System.out.println("Test Case 3: l1 = [9,9], l2 = [1]");
        System.out.println("Approach 1 (Optimal Loop) Result: " + toList(res3_1));
        System.out.println("Verification: " + (toList(res3_1).equals(Arrays.asList(0, 0, 1)) ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}