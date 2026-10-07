package DSA.Coding.DSABasic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Shared Definition for Singly-Linked List Node.
 */


/**
 * PROBLEM STATEMENT:
 * You are given the head of a linked list containing a series of integers separated by 0s [00:00:35].
 * The beginning and end of the linked list will have Node.val == 0 [00:01:38].
 * For every two consecutive 0s, merge all the nodes lying between them into a single node 
 * whose value is the sum of all the merged nodes [00:00:56, 00:01:06].
 * Return the head of the modified linked list [00:00:23].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: head = [0, 3, 1, 0, 4, 5, 2, 0] [00:00:35]
 * - Segment 1 between 0s: (3 + 1) = 4 -> append Node(4) [00:01:00].
 * - Segment 2 between 0s: (4 + 5 + 2) = 11 -> append Node(11) [00:01:06].
 * - Result: [4, 11]
 * 
 * Example 2: head = [0, 1, 0, 3, 0, 2, 2, 0]
 * - Segment 1: (1) = 1 -> Node(1)
 * - Segment 2: (3) = 3 -> Node(3)
 * - Segment 3: (2 + 2) = 4 -> Node(4)
 * - Result: [1, 3, 4]
 * 
 * Example 3: head = [0, 4, 5, 0, 3, 0] (Simulated inside video explanation [00:07:45, 00:10:10])
 * - Initial: dummy(-1), ans = dummy, sum = 0, current = head.next (val 4) [00:08:00, 00:08:15]
 * - Pass 1: current val 4 != 0 -> sum = 0 + 4 = 4 [00:08:25]
 * - Pass 2: current val 5 != 0 -> sum = 4 + 5 = 9 [00:08:46]
 * - Pass 3: current val 0 == 0 -> create Node(9), dummy.next = Node(9), dummy = Node(9), reset sum = 0 [00:08:59, 00:09:14]
 * - Pass 4: current val 3 != 0 -> sum = 0 + 3 = 3 [00:09:37]
 * - Pass 5: current val 0 == 0 -> create Node(3), dummy.next = Node(3), dummy = Node(3), reset sum = 0 [00:09:42, 00:09:48]
 * - Pass 6: current reaches null -> loop terminates [00:10:04].
 * - Return ans.next -> [9, 3] [00:10:10].
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Dummy Sentinel Node with Block Accumulation):
 * • Pointer & Variable Initialization:
 *   - `dummy = new ListNode(-1)` serves as sentinel anchor [00:04:42].
 *   - `ans = dummy` preserves return head [00:06:42].
 *   - `current = head.next` skips initial 0 node [00:05:09].
 *   - Accumulator `sum = 0` tracks running total between 0 boundaries [00:05:14].
 * • Condition Boundaries:
 *   - Traversal loop: `while (current != null)` [00:05:21].
 *   - Non-zero accumulation: `if (current.val != 0) sum += current.val` [00:05:29, 00:05:36].
 *   - Zero boundary check: `else` -> create Node(sum), attach `dummy.next = temp`, advance `dummy = dummy.next`, reset `sum = 0` [00:05:43, 00:06:19].
 *   - Iterator advance: `current = current.next` [00:07:11].
 * • Operational Steps:
 *   1. Instantiate `dummy` sentinel node and save `ans = dummy` reference [00:04:42, 00:06:42].
 *   2. Set `current = head.next` and `sum = 0` [00:05:09, 00:05:14].
 *   3. Traverse list while `current != null` [00:05:21].
 *   4. If `current.val != 0`, add value to `sum` [00:05:36].
 *   5. If `current.val == 0`, append new node storing `sum` to `dummy.next`, advance `dummy`, reset `sum = 0` [00:05:43, 00:06:19].
 *   6. Advance `current = current.next` [00:07:11].
 *   7. Return `ans.next` [00:06:52].
 * • Time Complexity: O(N) - Single linear pass over N linked list nodes.
 * • Space Complexity: O(N) auxiliary space - New linked list created for merged output nodes.
 * • LOGIC BEHIND THIS APPROACH:
 *   Skipping the initial `head` (which is always 0) aligns the iterator directly on the first data block [00:02:14, 00:05:09]. 
 *   Accumulating non-zero values until hitting subsequent `0` delimiters seamlessly partitions and aggregates contiguous segments [00:01:00, 00:02:59].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 1: Dummy Block Accumulation - head = [0, 2, 3, 0, 6, 0]):
 * Initial: dummy(-1), ans = dummy, current = Node(2), sum = 0
 * - Node 2: sum = 0 + 2 = 2. current = Node(3).
 * - Node 3: sum = 2 + 3 = 5. current = Node(0).
 * - Node 0: create Node(5), dummy.next = Node(5), dummy = Node(5), sum = 0. current = Node(6).
 * - Node 6: sum = 0 + 6 = 6. current = Node(0).
 * - Node 0: create Node(6), dummy.next = Node(6), dummy = Node(6), sum = 0. current = null.
 * Loop terminates.
 * Return ans.next -> [5, 6].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 2: In-Place Node Value Modification - head = [0, 2, 3, 0, 6, 0]):
 * Pointers: modify = head.next (Node 2), current = modify.next (Node 3)
 * - Node 3: modify.val += 3 (5). current = Node(0).
 * - Node 0: modify.next = current.next (Node 6), modify = Node(6), current = Node(6).next.
 * - Node 0: modify.next = null.
 * Return head.next -> [5, 6].
 */
public class MergeNodesInBetweenZeros {

    // APPROACH 1: Dummy Sentinel Node with Block Accumulation (Anchor Strategy)
    public static ListNode mergeNodesOptimal(ListNode head) {
        // Create dummy node with placeholder value [00:04:42]
        ListNode dummy = new ListNode(-1);
        ListNode ans = dummy; // Save dummy head anchor reference [00:06:42]

        // Start scanning from second node (skipping initial 0) [00:05:09]
        ListNode current = head.next;
        int sum = 0; // Accumulator for values between zero boundaries [00:05:14]

        // Traverse all remaining nodes in the list [00:05:21]
        while (current != null) {
            if (current.val != 0) {
                sum += current.val; // Accumulate non-zero value [00:05:36]
            } else {
                // Zero boundary reached: create merged node with total sum [00:05:43, 00:05:59]
                ListNode temp = new ListNode(sum);
                dummy.next = temp;   // Connect merged node to output list [00:06:05]
                dummy = dummy.next; // Advance output construction pointer [00:06:12]

                sum = 0; // Reset accumulator for next segment [00:06:19]
            }
            current = current.next; // Advance input list iterator [00:07:11]
        }

        return ans.next; // Return head skipping placeholder dummy [00:06:52]
    }

    // APPROACH 2: In-Place Node Value Overwrite Strategy (O(1) Auxiliary Space)
    // Modifies existing node values directly in-place without allocating new memory nodes.
    public static ListNode mergeNodesInPlace(ListNode head) {
        if (head == null || head.next == null) return null;

        ListNode modify = head.next; // Pointer tracking position where sum is written
        ListNode current = modify.next; // Reader pointer

        int sum = modify.val;

        while (current != null) {
            if (current.val != 0) {
                sum += current.val;
            } else {
                modify.val = sum; // Overwrite node value in-place
                if (current.next != null) {
                    modify.next = current.next;
                    modify = modify.next;
                    sum = modify.val;
                } else {
                    modify.next = null; // Sever remaining tail nodes
                }
            }
            current = current.next;
        }

        return head.next;
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

        // --- TEST CASE 1 (head = [0, 3, 1, 0, 4, 5, 2, 0]) ---
        ListNode test1_1 = buildList(new int[]{0, 3, 1, 0, 4, 5, 2, 0});
        ListNode test1_2 = buildList(new int[]{0, 3, 1, 0, 4, 5, 2, 0});

        ListNode res1_1 = mergeNodesOptimal(test1_1);
        ListNode res1_2 = mergeNodesInPlace(test1_2);

        System.out.println("Test Case 1: [0, 3, 1, 0, 4, 5, 2, 0]");
        System.out.println("Approach 1 (Dummy Accumulation) Result: " + toList(res1_1));
        System.out.println("Approach 2 (In-Place Overwrite) Result: " + toList(res1_2));
        boolean check1 = toList(res1_1).equals(Arrays.asList(4, 11)) &&
                         toList(res1_2).equals(Arrays.asList(4, 11));
        System.out.println("Verification: " + (check1 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (From Video Board Explanation [0, 4, 5, 0, 3, 0]) ---
        ListNode test2_1 = buildList(new int[]{0, 4, 5, 0, 3, 0});
        ListNode test2_2 = buildList(new int[]{0, 4, 5, 0, 3, 0});

        ListNode res2_1 = mergeNodesOptimal(test2_1);
        ListNode res2_2 = mergeNodesInPlace(test2_2);

        System.out.println("Test Case 2: [0, 4, 5, 0, 3, 0]");
        System.out.println("Approach 1 (Dummy Accumulation) Result: " + toList(res2_1));
        System.out.println("Approach 2 (In-Place Overwrite) Result: " + toList(res2_2));
        boolean check2 = toList(res2_1).equals(Arrays.asList(9, 3)) &&
                         toList(res2_2).equals(Arrays.asList(9, 3));
        System.out.println("Verification: " + (check2 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}