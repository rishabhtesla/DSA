package DSA.Coding.DSABasic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Shared Definition for Singly-Linked List Node.
 */

/**
 * PROBLEM STATEMENT:
 * You are given the head of a linked list. Delete the middle node, and return the head 
 * of the modified linked list [00:00:36, 00:00:40].
 * The middle node is the floor(N / 2)-th node from start using 0-based indexing [00:00:50].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: head = [1, 3, 4, 7, 1, 2, 6] (N = 7)
 * - Index floor(7 / 2) = 3 -> Node(7) is deleted.
 * - Result: [1, 3, 4, 1, 2, 6]
 * 
 * Example 2: head = [1, 2, 3, 4, 5, 6] (N = 6, Even length from video explanation [00:05:29])
 * - Index floor(6 / 2) = 3 -> Node(4) is deleted [00:01:25, 00:06:44].
 * - Initial: fast = 1, slow = 1, prev = 1 [00:05:50]
 * - Pass 1: prev = 1, slow = 2, fast = 3
 * - Pass 2: prev = 2, slow = 3, fast = 5 [00:06:18]
 * - Pass 3: prev = 3, slow = 4, fast = null [00:06:29, 00:06:44]
 * - Deletion: prev(3).next = slow(4).next (5) -> Node 4 bypassed! [00:06:50]
 * - Result: [1, 2, 3, 5, 6]
 * 
 * Example 3: head = [1] (Single node edge case [00:07:20])
 * - Single node is middle -> deleting middle leaves empty list [00:07:25].
 * - Condition head.next == null triggers -> return null [00:07:29, 00:07:36].
 * - Result: []
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Fast/Slow Pointers with Lagging Predecessor):
 * • Edge Cases:
 *   - Single node list: `if (head.next == null) return null` [00:07:20, 00:07:36].
 * • Pointer Initialization:
 *   - `fast = head` (2 steps per turn) [00:07:59].
 *   - `slow = head` (1 step per turn) [00:08:08].
 *   - `prev = slow` (tracks node prior to slow) [00:08:13].
 * • Condition Boundaries:
 *   - Main traversal loop condition: `while (fast != null && fast.next != null)` [00:08:19, 00:08:30].
 *   - Advance `prev`: `prev = slow` before moving `slow` [00:08:36].
 *   - Advance `slow`: `slow = slow.next` [00:08:42].
 *   - Advance `fast`: `fast = fast.next.next` [00:08:51].
 * • Operational Steps:
 *   1. Handle single-node edge case (`head.next == null`) by returning `null` [00:07:29].
 *   2. Instantiate `fast`, `slow`, and `prev` pointers at `head` [00:07:59, 00:08:13].
 *   3. While `fast != null && fast.next != null` [00:08:19]:
 *      - Assign `prev = slow` [00:08:36].
 *      - Advance `slow = slow.next` [00:08:42].
 *      - Advance `fast = fast.next.next` [00:08:51].
 *   4. Bypass middle node: `prev.next = prev.next.next` (or `prev.next = slow.next`) [00:09:08].
 *   5. Return `head` [00:09:13].
 * • Time Complexity: O(N) - Single pass traversal over N/2 steps.
 * • Space Complexity: O(1) auxiliary space - Performs modification directly in-place.
 * • LOGIC BEHIND THIS APPROACH:
 *   `fast` moving twice as fast as `slow` guarantees `slow` lands on the middle node when `fast` reaches tail [00:03:13, 00:03:56]. 
 *   Tracking `prev` right behind `slow` allows instantaneous $O(1)$ node bypassing [00:05:08, 00:09:08].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 1: Fast/Slow with Prev - head = [1, 2, 3, 4, 5]):
 * Initial: fast = 1, slow = 1, prev = 1 [00:10:00]
 * 
 * Iteration 1:
 * - prev = slow (1)
 * - slow = 2, fast = 3 [00:10:18]
 * 
 * Iteration 2:
 * - prev = slow (2)
 * - slow = 3, fast = 5 [00:10:35, 00:10:43]
 * 
 * Iteration 3:
 * - fast.next is null -> Loop terminates [00:10:49].
 * - slow is on Node(3) [middle], prev is on Node(2) [predecessor] [00:10:54].
 * - prev.next = prev.next.next (Node(4)) -> 2 points to 4 [00:10:59].
 * Return head -> [1, 2, 4, 5] [00:11:05].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 2: Dummy Node Fast/Slow Strategy - head = [1, 2, 3, 4, 5]):
 * Create dummy node (0) -> 0 -> 1 -> 2 -> 3 -> 4 -> 5
 * slow = dummy, fast = head (1)
 * Iteration 1: slow = 1, fast = 3
 * Iteration 2: slow = 2, fast = 5
 * Loop terminates (fast.next is null).
 * slow lands on Node(2) (predecessor directly!).
 * slow.next = slow.next.next (4) -> Node(3) deleted!
 * Return dummy.next -> [1, 2, 4, 5].
 */
public class DeleteMiddleNodeOfLinkedList {

    // APPROACH 1: Fast & Slow Pointers with Lagging Predecessor (Anchor Strategy)
    public static ListNode deleteMiddleOptimal(ListNode head) {
        // Base Case: single node list -> deleting middle yields empty list [00:07:20, 00:07:36]
        if (head == null || head.next == null) {
            return null;
        }

        ListNode fast = head; // Fast pointer (2 steps) [00:07:59]
        ListNode slow = head; // Slow pointer (1 step) [00:08:08]
        ListNode prev = slow; // Predecessor pointer tracking node before slow [00:08:13]

        // Loop while fast and fast.next are valid [00:08:19, 00:08:30]
        while (fast != null && fast.next != null) {
            prev = slow;            // Store current slow as predecessor [00:08:36]
            slow = slow.next;      // Advance slow 1 step [00:08:42]
            fast = fast.next.next; // Advance fast 2 steps [00:08:51]
        }

        // Bypass middle node (slow) by connecting predecessor to slow's successor [00:09:08]
        prev.next = prev.next.next;

        return head; // Return modified list head [00:09:13]
    }

    // APPROACH 2: Dummy Sentinel Offset Strategy (Direct Predecessor Landing)
    // Starts `slow` pointer at a `dummy` node before `head`. 
    // Automatically causes `slow` to land on the predecessor node without needing a 3rd `prev` pointer.
    public static ListNode deleteMiddleDummy(ListNode head) {
        if (head == null || head.next == null) return null;

        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode slow = dummy;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // slow lands directly on node before middle
        slow.next = slow.next.next;

        return dummy.next;
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

        // --- TEST CASE 1 (Odd Length [1,2,3,4,5] -> Middle 3 Deleted) ---
        ListNode test1_1 = buildList(new int[]{1, 2, 3, 4, 5});
        ListNode test1_2 = buildList(new int[]{1, 2, 3, 4, 5});

        ListNode res1_1 = deleteMiddleOptimal(test1_1);
        ListNode res1_2 = deleteMiddleDummy(test1_2);

        System.out.println("Test Case 1: [1, 2, 3, 4, 5]");
        System.out.println("Approach 1 (Fast/Slow Prev) Result: " + toList(res1_1));
        System.out.println("Approach 2 (Dummy Offset)   Result: " + toList(res1_2));
        boolean check1 = toList(res1_1).equals(Arrays.asList(1, 2, 4, 5)) &&
                         toList(res1_2).equals(Arrays.asList(1, 2, 4, 5));
        System.out.println("Verification: " + (check1 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (Even Length [1,2,3,4,5,6] -> Middle 4 Deleted) ---
        ListNode test2_1 = buildList(new int[]{1, 2, 3, 4, 5, 6});
        ListNode test2_2 = buildList(new int[]{1, 2, 3, 4, 5, 6});

        ListNode res2_1 = deleteMiddleOptimal(test2_1);
        ListNode res2_2 = deleteMiddleDummy(test2_2);

        System.out.println("Test Case 2: [1, 2, 3, 4, 5, 6]");
        System.out.println("Approach 1 (Fast/Slow Prev) Result: " + toList(res2_1));
        System.out.println("Approach 2 (Dummy Offset)   Result: " + toList(res2_2));
        boolean check2 = toList(res2_1).equals(Arrays.asList(1, 2, 3, 5, 6)) &&
                         toList(res2_2).equals(Arrays.asList(1, 2, 3, 5, 6));
        System.out.println("Verification: " + (check2 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 3 (Single Node Edge Case [1]) ---
        ListNode test3_1 = buildList(new int[]{1});
        ListNode test3_2 = buildList(new int[]{1});

        ListNode res3_1 = deleteMiddleOptimal(test3_1);
        ListNode res3_2 = deleteMiddleDummy(test3_2);

        System.out.println("Test Case 3: Single node [1]");
        System.out.println("Approach 1 (Fast/Slow Prev) Result: " + toList(res3_1));
        System.out.println("Approach 2 (Dummy Offset)   Result: " + toList(res3_2));
        boolean check3 = toList(res3_1).isEmpty() && toList(res3_2).isEmpty();
        System.out.println("Verification: " + (check3 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}