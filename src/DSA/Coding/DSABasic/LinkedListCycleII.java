package DSA.Coding.DSABasic;

import java.util.HashSet;
import java.util.Set;

/**
 * Shared Definition for Singly-Linked List Node.
 */


/**
 * PROBLEM STATEMENT:
 * Given the head of a linked list, return the node where the cycle begins [00:00:34].
 * If there is no cycle, return null [00:00:38].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: head = [3, 2, 0, -4], cycle connected back to node index 1 (val 2).
 * - Phase 1: fast and slow collide at node val -4 [00:03:05].
 * - Phase 2: place entry at head (3), keep slow at collision node (-4).
 *   - Move both 1 step: entry goes to 2, slow goes to 2.
 *   - entry == slow at Node(2)! [00:04:19]
 * - Result: Node(2)
 * 
 * Example 2: head = [1, 2, 3, 4, 5, 6, 7], cycle from 7 back to node index 2 (val 3) [00:04:43].
 * - Phase 1: fast and slow collide at Node(6) [00:06:06].
 * - Phase 2: entry starts at head (1), slow starts at 6 [00:06:29].
 *   - Step 1: entry moves to 2, slow moves to 7 [00:06:45].
 *   - Step 2: entry moves to 3, slow moves to 3 -> entry == slow! [00:06:50]
 * - Result: Node(3) [00:06:54]
 * 
 * Example 3: head = [1], no cycle [00:07:25]
 * - fast reaches null -> return null [00:08:51].
 * - Result: null
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Floyd's Phase 1 Collision & Phase 2 Equidistant Entry Walk):
 * • Edge Cases:
 *   - Base condition: `if (head == null) return null` [00:07:25].
 * • Pointer Initialization:
 *   - `fast = head`, `slow = head` for Phase 1 [00:07:40, 00:07:45].
 *   - `pointer = head` for Phase 2 entry tracking [00:09:13].
 * • Condition Boundaries:
 *   - Phase 1 loop: `while (fast != null && fast.next != null)` [00:07:59].
 *   - Fast/Slow moves: `fast = fast.next.next`, `slow = slow.next` [00:08:10, 00:08:23].
 *   - Phase 2 loop: `while (pointer != slow)` [00:09:19].
 *   - Synchronized single-stepping: `pointer = pointer.next`, `slow = slow.next` [00:09:27, 00:09:30].
 * • Operational Steps:
 *   1. Check if `head == null` [00:07:25].
 *   2. Phase 1: Advance fast by 2 and slow by 1 until they collide or fast hits null [00:07:59, 00:08:35].
 *   3. If fast hits null, return `null` (no cycle exists) [00:08:51].
 *   4. Phase 2: When `slow == fast`, instantiate `pointer = head` [00:08:35, 00:09:13].
 *   5. Walk both `pointer` and `slow` forward 1 step at a time until `pointer == slow` [00:09:19, 00:09:30].
 *   6. Return `pointer` (the cycle entrance node) [00:09:42].
 * • Time Complexity: O(N) - Linear time for fast/slow meeting plus linear step-walk to entry node.
 * • Space Complexity: O(1) auxiliary space - Uses fixed pointer variables without auxiliary data structures.
 * • LOGIC BEHIND THIS APPROACH:
 *   Let distance to cycle start be A, cycle start to collision be B, and remaining cycle be C.
 *   Distance by slow = A + B. Distance by fast = A + B + k(B + C) = 2(A + B).
 *   Simplifying gives A = k(B + C) - B = (k - 1)(B + C) + C.
 *   This proves walking 1 step at a time from head and collision point meets precisely at cycle entrance [00:03:26, 00:06:21].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 1: Floyd's Two-Phase - 1 -> 2 -> 3 -> 4 -> 5 -> 3 [Cycle at 3]):
 * Phase 1 (Find Collision):
 * - Initial: fast = 1, slow = 1
 * - Pass 1: fast = 3, slow = 2
 * - Pass 2: fast = 5, slow = 3
 * - Pass 3: fast = 4, slow = 4 -> Collision at Node(4)!
 * 
 * Phase 2 (Find Entrance):
 * - Set pointer = head (1), keep slow at collision node (4).
 * - Step 1: pointer = 2, slow = 5
 * - Step 2: pointer = 3, slow = 3 -> Match!
 * Return Node(3) [00:06:54].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 2: HashSet Reference Tracking Strategy - 1 -> 2 -> 3 -> 2):
 * Visited set = {}
 * Node 1: set = {1}
 * Node 2: set = {1, 2}
 * Node 3: set = {1, 2, 3}
 * Re-visit Node 2: Already in set! -> Return Node(2).
 */
public class LinkedListCycleII {

    // APPROACH 1: Floyd's Two-Phase Collision & Equidistant Walk (Anchor Strategy - O(1) Space)
    public static ListNode detectCycleOptimal(ListNode head) {
        // Base Case: null list cannot contain a cycle [00:07:25]
        if (head == null) {
            return null;
        }

        ListNode fast = head; // Fast pointer (2 steps) [00:07:40]
        ListNode slow = head; // Slow pointer (1 step) [00:07:45]

        // Phase 1: Cycle Detection via collision [00:07:59]
        while (fast != null && fast.next != null) {
            fast = fast.next.next; // Advance 2 steps [00:08:10]
            slow = slow.next;      // Advance 1 step [00:08:23]

            // Collision detected [00:08:35]
            if (slow == fast) {
                // Phase 2: Find cycle start node [00:09:05]
                ListNode pointer = head; // Reset entry pointer to head [00:09:13]

                // Advance both entry pointer and slow pointer 1 step at a time [00:09:19]
                while (pointer != slow) {
                    pointer = pointer.next; // Advance entry pointer [00:09:27]
                    slow = slow.next;       // Advance slow pointer [00:09:30]
                }

                return pointer; // Both pointers met at cycle start node [00:09:42]
            }
        }

        return null; // Fast encountered null -> No cycle present [00:08:51]
    }

    // APPROACH 2: HashSet Reference Lookup Strategy (O(N) Auxiliary Space)
    // Stores node references in a HashSet. The first node reference encountered twice is the cycle start node.
    public static ListNode detectCycleHashSet(ListNode head) {
        if (head == null || head.next == null) return null;

        Set<ListNode> visitedNodes = new HashSet<>();
        ListNode curr = head;

        while (curr != null) {
            if (visitedNodes.contains(curr)) {
                return curr; // First duplicated node reference is cycle entry point
            }
            visitedNodes.add(curr);
            curr = curr.next;
        }

        return null; // No cycle
    }

    // Helper method to construct linked list with cycle at specific index
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
            curr.next = cycleNode; // Attach tail to cycle entry node
        }

        return dummy.next;
    }

    // Comprehensive Verification Test Suite
    public static void main(String[] args) {
        System.out.println("================ RUNNING DSA TEST SUITE ================\n");

        // --- TEST CASE 1 (Cycle starts at index 1 - Node with value 2) ---
        ListNode head1 = buildListWithCycle(new int[]{3, 2, 0, -4}, 1);

        ListNode res1_1 = detectCycleOptimal(head1);
        ListNode res1_2 = detectCycleHashSet(head1);

        System.out.println("Test Case 1: [3, 2, 0, -4] with cycle at index 1");
        System.out.println("Approach 1 (Floyd Two-Phase) Result Val: " + (res1_1 != null ? res1_1.val : "null"));
        System.out.println("Approach 2 (HashSet Lookup)  Result Val: " + (res1_2 != null ? res1_2.val : "null"));
        boolean check1 = (res1_1 != null && res1_1.val == 2) && (res1_2 != null && res1_2.val == 2);
        System.out.println("Verification: " + (check1 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (Non-Cyclic List) ---
        ListNode head2 = buildListWithCycle(new int[]{1, 2, 3, 4}, -1);

        ListNode res2_1 = detectCycleOptimal(head2);
        ListNode res2_2 = detectCycleHashSet(head2);

        System.out.println("Test Case 2: [1, 2, 3, 4] with no cycle");
        System.out.println("Approach 1 (Floyd Two-Phase) Result: " + (res2_1 != null ? res2_1.val : "null"));
        System.out.println("Approach 2 (HashSet Lookup)  Result: " + (res2_2 != null ? res2_2.val : "null"));
        boolean check2 = (res2_1 == null) && (res2_2 == null);
        System.out.println("Verification: " + (check2 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}