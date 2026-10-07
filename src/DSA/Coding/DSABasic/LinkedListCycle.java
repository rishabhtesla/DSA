package DSA.Coding.DSABasic;

import java.util.HashSet;
import java.util.Set;

/**
 * Shared Definition for Singly-Linked List Node.
 */


/**
 * PROBLEM STATEMENT:
 * Given head, the head of a linked list, determine if the linked list has a cycle in it [00:00:15].
 * Return true if there is a cycle in the linked list. Otherwise, return false [00:01:04].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: head = [3, 2, 0, -4], tail connects to node index 1.
 * - 3 -> 2 -> 0 -> -4
 *        ^          |
 *        |__________|
 * - Fast/Slow Movement:
 *   - Initial: slow at 3, fast at 3.
 *   - Pass 1: slow moves to 2, fast moves 2 steps to 0.
 *   - Pass 2: slow moves to 0, fast moves 2 steps to 2.
 *   - Pass 3: slow moves to -4, fast moves 2 steps to -4 -> fast == slow!
 * - Result: true [00:03:51, 00:06:44].
 * 
 * Example 2: head = [1, 2], tail connects to node index 0.
 * - 1 -> 2
 *   ^    |
 *   |____|
 * - Fast/Slow Movement:
 *   - Initial: slow at 1, fast at 1.
 *   - Pass 1: slow moves to 2, fast moves 2 steps (2 -> 1) to 1.
 *   - Pass 2: slow moves to 1, fast moves 2 steps (1 -> 2 -> 1) to 1 -> fast == slow!
 * - Result: true.
 * 
 * Example 3: head = [1, 2, 3, 4], no cycle.
 * - 1 -> 2 -> 3 -> 4 -> null
 * - Fast moves 2 steps each iteration: reaches null in 2 passes [00:04:29].
 * - Result: false [00:06:56].
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Floyd's Tortoise and Hare Cycle Detection):
 * • Edge Cases:
 *   - Single node or empty list: `if (head == null || head.next == null) return false` [00:05:35, 00:05:54].
 * • Pointer Initialization:
 *   - `slow = head` [00:05:59].
 *   - `fast = head` [00:06:05].
 * • Condition Boundaries:
 *   - Loop while `fast != null && fast.next != null` [00:05:18, 00:06:13].
 *   - Fast advancement: `fast = fast.next.next` [00:06:21].
 *   - Slow advancement: `slow = slow.next` [00:06:33].
 *   - Meeting check: `if (fast == slow) return true` [00:06:44].
 * • Operational Steps:
 *   1. Guard against null or single node without next reference [00:05:35].
 *   2. Instantiate slow and fast pointers at `head` [00:05:59, 00:06:05].
 *   3. Traverse list while fast and fast.next are non-null [00:06:13].
 *   4. Advance fast 2 steps and slow 1 step [00:06:21, 00:06:33].
 *   5. If slow and fast point to the exact same reference, return `true` [00:06:44].
 *   6. If loop completes, fast encountered null -> return `false` [00:06:56].
 * • Time Complexity: O(N) - In non-cyclic lists, fast reaches tail in N/2 steps. In cyclic lists, fast catches slow within N iterations inside the cycle.
 * • Space Complexity: O(1) auxiliary space - Uses only two scalar node pointers [00:00:58].
 * • LOGIC BEHIND THIS APPROACH:
 *   Within a loop of length C, the distance between `fast` and `slow` decreases by 1 node per iteration because `fast` moves 1 step faster than `slow` [00:01:30]. 
 *   Therefore, `fast` is guaranteed to catch `slow` in at most C steps without triggering infinite loops [00:02:00, 00:03:51].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 1: Floyd's Algorithm - 1 -> 2 -> 3 -> 4 -> 2 [Cycle]):
 * Initial: slow = 1, fast = 1
 * Pass 1: fast moves to 3, slow moves to 2 [00:08:06]
 * Pass 2: fast moves (3 -> 4 -> 2) to 2, slow moves to 3 [00:08:28]
 * Pass 3: fast moves (2 -> 3 -> 4) to 4, slow moves to 4 -> fast(4) == slow(4)! [00:08:59]
 * Cycle detected! Return true [00:09:05].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 2: HashSet Reference Tracking Strategy - 1 -> 2 -> 3 -> 2):
 * Visited set = {}
 * Node 1: set = {Node(1)}
 * Node 2: set = {Node(1), Node(2)}
 * Node 3: set = {Node(1), Node(2), Node(3)}
 * Node 2 (via 3.next): Node(2) exists in set! -> Return true.
 */
public class LinkedListCycle {

    // APPROACH 1: Floyd's Tortoise and Hare Algorithm (Anchor Strategy - O(1) Space)
    public static boolean hasCycleOptimal(ListNode head) {
        // Base case: empty list or single node cannot form a cycle [00:05:35, 00:05:54]
        if (head == null || head.next == null) {
            return false;
        }

        ListNode slow = head; // Slow pointer moves 1 step at a time [00:05:59]
        ListNode fast = head; // Fast pointer moves 2 steps at a time [00:06:05]

        // Loop as long as fast pointer and its next node are valid [00:05:18, 00:06:13]
        while (fast != null && fast.next != null) {
            fast = fast.next.next; // Move fast 2 nodes forward [00:06:21]
            slow = slow.next;      // Move slow 1 node forward [00:06:33]

            // Collision check: fast caught up with slow in a cycle [00:06:37, 00:06:44]
            if (fast == slow) {
                return true; // Cycle detected [00:06:48]
            }
        }

        return false; // Fast reached null; no cycle exists [00:06:56]
    }

    // APPROACH 2: HashSet Reference Tracking Strategy (O(N) Auxiliary Space)
    // Tracks visited node references in a HashSet. If a node reference is re-encountered, a cycle exists.
    public static boolean hasCycleHashSet(ListNode head) {
        if (head == null || head.next == null) return false;

        Set<ListNode> visitedNodes = new HashSet<>();
        ListNode curr = head;

        while (curr != null) {
            if (visitedNodes.contains(curr)) {
                return true; // Cycle detected via set lookup
            }
            visitedNodes.add(curr);
            curr = curr.next;
        }

        return false;
    }

    // Helper method to build a linked list with a cycle for testing
    private static ListNode buildListWithCycle(int[] values, int cycleIndex) {
        if (values == null || values.length == 0) return null;
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        ListNode cycleNode = null;

        for (int i = 0; i < values.length; i++) {
            curr.next = new ListNode(values[i]);
            curr = curr.next;
            if (i == cycleIndex) {
                cycleNode = curr;
            }
        }

        if (cycleIndex >= 0) {
            curr.next = cycleNode; // Form cycle
        }

        return dummy.next;
    }

    // Comprehensive Verification Test Suite
    public static void main(String[] args) {
        System.out.println("================ RUNNING DSA TEST SUITE ================\n");

        // --- TEST CASE 1 (Cyclic List: [3, 2, 0, -4], cycle at index 1) ---
        ListNode head1 = buildListWithCycle(new int[]{3, 2, 0, -4}, 1);

        boolean res1_1 = hasCycleOptimal(head1);
        boolean res1_2 = hasCycleHashSet(head1);

        System.out.println("Test Case 1: [3, 2, 0, -4] with cycle at index 1");
        System.out.println("Approach 1 (Floyd's Fast/Slow) Result: " + res1_1);
        System.out.println("Approach 2 (HashSet Lookup)   Result: " + res1_2);
        boolean check1 = res1_1 && res1_2;
        System.out.println("Verification: " + (check1 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (Non-Cyclic List: [1, 2, 3, 4]) ---
        ListNode head2 = buildListWithCycle(new int[]{1, 2, 3, 4}, -1);

        boolean res2_1 = hasCycleOptimal(head2);
        boolean res2_2 = hasCycleHashSet(head2);

        System.out.println("Test Case 2: [1, 2, 3, 4] with no cycle");
        System.out.println("Approach 1 (Floyd's Fast/Slow) Result: " + res2_1);
        System.out.println("Approach 2 (HashSet Lookup)   Result: " + res2_2);
        boolean check2 = !res2_1 && !res2_2;
        System.out.println("Verification: " + (check2 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}