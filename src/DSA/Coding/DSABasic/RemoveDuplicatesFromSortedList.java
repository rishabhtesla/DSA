package DSA.Coding.DSABasic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * PROBLEM STATEMENT:
 * Given the head of a sorted linked list, delete all duplicates such that 
 * each element appears only once [00:00:16, 00:00:36]. Return the linked list sorted as well [00:00:43].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: head = [1, 1, 2]
 * - Process: Node 1 duplicate detected -> skip second Node 1 -> 1 -> 2.
 * - Result: [1, 2]
 * 
 * Example 2: head = [1, 1, 2, 3, 3] [00:00:23, 00:09:34]
 * - Initial: 1 -> 1 -> 2 -> 3 -> 3 [00:00:31]
 * - Duplicate 1 skipped -> attached unique 2 [00:01:54, 00:10:20].
 * - Attach unique 3 [00:02:29, 00:10:54].
 * - Duplicate 3 skipped [00:08:00, 00:11:15].
 * - Final tail pointer set to null to sever duplicate tail references (`dummy.next = null`) [00:08:48, 00:11:33].
 * - Result: [1, 2, 3] [00:00:43, 00:11:45]
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Dummy Anchor Pointer Rewiring In-Place):
 * • Pointer Initialization:
 *   - Base condition check: `if (head == null || head.next == null) return head` [00:03:00].
 *   - `dummy = new ListNode(head.val)` anchors unique sequence [00:03:51].
 *   - `ans = dummy` captures starting reference for return [00:04:14].
 *   - `pointer = head.next` begins scanning from second node [00:04:29].
 * • Condition Boundaries:
 *   - Main traversal loop: `while (pointer != null)` [00:04:44].
 *   - Distinct node found: `if (pointer.val != dummy.val)` -> splice `dummy.next = pointer` [00:04:59, 00:10:20].
 *   - Advance pointers: `dummy = dummy.next`, advance `pointer = pointer.next` [00:05:29, 00:05:39].
 *   - Trailing cleanup: set `dummy.next = null` after loop to sever leftover duplicate connections [00:08:48, 00:11:33].
 * • Operational Steps:
 *   1. Handle empty or single-node list edge cases [00:03:00].
 *   2. Instantiate dummy node storing `head.val` and anchor `ans = dummy` [00:03:51, 00:04:14].
 *   3. Iterate list with `pointer = head.next` [00:04:29].
 *   4. If `pointer.val != dummy.val`, link `dummy.next = pointer` and move `dummy = dummy.next` [00:05:22, 00:10:58].
 *   5. Advance `pointer = pointer.next` on every iteration [00:05:39].
 *   6. Disconnect trailing duplicate references with `dummy.next = null` [00:08:48, 00:11:33].
 *   7. Return `ans` [00:05:59].
 * • Time Complexity: O(n) - Single linear pass over n linked list nodes.
 * • Space Complexity: O(1) auxiliary space - Re-links existing pointers without creating new nodes [00:00:59, 00:08:24].
 * • LOGIC BEHIND THIS APPROACH:
 *   Because the linked list is sorted, all duplicate values are contiguous [00:00:16]. 
 *   Using a `dummy` pointer to track the last unique node allows skipping duplicate nodes by updating pointer references directly [00:01:54, 00:10:20]. 
 *   Explicitly terminating the spliced list with `dummy.next = null` prevents cycles or leftover duplicate tail nodes from persisting [00:08:48, 00:11:33].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 1: Dummy Pointer Rewiring - head = [1, 1, 2, 3, 3]):
 * Initial: dummy(1), ans = dummy, pointer = head.next (val 1) [00:09:51]
 * Iteration 1 (pointer val 1): 1 == dummy.val (1) -> duplicate! Just pointer = pointer.next (val 2) [00:10:05]
 * Iteration 2 (pointer val 2): 2 != dummy.val (1) -> unique!
 *             dummy.next = pointer(2), dummy = dummy.next (val 2) [00:10:20]
 *             pointer = pointer.next (val 3) [00:10:33]
 * Iteration 3 (pointer val 3): 3 != dummy.val (2) -> unique!
 *             dummy.next = pointer(3), dummy = dummy.next (val 3) [00:10:54]
 *             pointer = pointer.next (val 3) [00:11:09]
 * Iteration 4 (pointer val 3): 3 == dummy.val (3) -> duplicate! pointer = pointer.next (null) [00:11:15]
 * Loop ends (pointer is null).
 * Post-Loop Cleanup: dummy.next = null (severs old link from node 3 to second duplicate node 3) [00:08:48, 00:11:33].
 * Return ans -> [1, 2, 3] [00:11:45].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 2: Classic Single-Pointer Comparison - head = [1, 1, 2]):
 * Pointer curr = head (val 1)
 * Iteration 1: curr.val (1) == curr.next.val (1) -> duplicate!
 *             curr.next = curr.next.next (bypasses second 1, points to 2)
 * Iteration 2: curr.val (1) != curr.next.val (2) -> unique!
 *             curr = curr.next (curr moves to 2)
 * Iteration 3: curr.next == null -> loop terminates.
 * Return head -> [1, 2].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 3: Recursive Redundancy Elimination - head = [1, 1, 2]):
 * Frame 1: head = 1. Recurse on head.next (1 -> 2)
 * Frame 2: head = 1. Recurse on head.next (2)
 * Frame 3: head = 2. head.next == null -> return node 2
 * Unwind Frame 2: head(1).val == head.next(2).val ? (1 != 2) -> head.next = node 2 -> return node 1
 * Unwind Frame 1: head(1).val == head.next(1).val ? (1 == 1) -> return head.next (node 1 -> 2)
 * Result = [1, 2].
 */
public class RemoveDuplicatesFromSortedList {

    // APPROACH 1: Dummy Anchor Pointer Rewiring In-Place (Anchor Strategy)
    public static ListNode deleteDuplicatesOptimal(ListNode head) {
        // Base Cases: empty or single node list [00:03:00]
        if (head == null || head.next == null) {
            return head;
        }

        // Initialize dummy node with first value and save answer anchor [00:03:51, 00:04:14]
        ListNode dummy = new ListNode(head.val);
        ListNode ans = dummy;

        // Pointer starts scanning from the second node [00:04:29]
        ListNode pointer = head.next;

        // Traverse remaining nodes [00:04:44]
        while (pointer != null) {
            // Unique value encountered [00:04:59]
            if (pointer.val != dummy.val) {
                dummy.next = pointer; // Re-link dummy to current unique node [00:08:38, 00:10:20]
                dummy = dummy.next;   // Advance dummy pointer [00:05:29]
            }
            pointer = pointer.next; // Advance scanner pointer on every iteration [00:05:39]
        }

        // Sever trailing connections to old duplicate nodes [00:08:48, 00:11:33]
        dummy.next = null;

        return ans; // Return head of pruned unique list [00:05:59]
    }

    // APPROACH 2: Classic Single-Pointer Neighbor Comparison Strategy (In-Place O(1) Space)
    // Directly compares `curr.val` with `curr.next.val` and bypasses duplicate nodes 
    // by reassigning `curr.next = curr.next.next`.
    public static ListNode deleteDuplicatesClassic(ListNode head) {
        if (head == null) return null;

        ListNode curr = head;

        while (curr != null && curr.next != null) {
            if (curr.val == curr.next.val) {
                curr.next = curr.next.next; // Skip duplicate node
            } else {
                curr = curr.next; // Move to next unique node
            }
        }

        return head;
    }

    // APPROACH 3: Recursive Sub-problem Elimination Strategy
    // Solves duplicate removal recursively by resolving suffix sub-lists first and 
    // skipping current head if it matches the processed next node.
    public static ListNode deleteDuplicatesRecursive(ListNode head) {
        if (head == null || head.next == null) return head;

        head.next = deleteDuplicatesRecursive(head.next);

        return (head.val == head.next.val) ? head.next : head;
    }

    // Helper method to convert an array to a linked list
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

        // --- TEST CASE 1 (Simple Duplicates) ---
        ListNode test1_1 = buildList(new int[]{1, 1, 2});
        ListNode test1_2 = buildList(new int[]{1, 1, 2});
        ListNode test1_3 = buildList(new int[]{1, 1, 2});

        ListNode res1_1 = deleteDuplicatesOptimal(test1_1);
        ListNode res1_2 = deleteDuplicatesClassic(test1_2);
        ListNode res1_3 = deleteDuplicatesRecursive(test1_3);

        System.out.println("Test Case 1: [1, 1, 2]");
        System.out.println("Approach 1 (Dummy Rewiring) Result: " + toList(res1_1));
        System.out.println("Approach 2 (Classic Pointer)Result: " + toList(res1_2));
        System.out.println("Approach 3 (Recursive)      Result: " + toList(res1_3));
        boolean check1 = toList(res1_1).equals(Arrays.asList(1, 2)) &&
                         toList(res1_2).equals(Arrays.asList(1, 2)) &&
                         toList(res1_3).equals(Arrays.asList(1, 2));
        System.out.println("Verification: " + (check1 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (Multiple Continuous Duplicates from Video Explanation) ---
        ListNode test2_1 = buildList(new int[]{1, 1, 2, 3, 3});
        ListNode test2_2 = buildList(new int[]{1, 1, 2, 3, 3});
        ListNode test2_3 = buildList(new int[]{1, 1, 2, 3, 3});

        ListNode res2_1 = deleteDuplicatesOptimal(test2_1);
        ListNode res2_2 = deleteDuplicatesClassic(test2_2);
        ListNode res2_3 = deleteDuplicatesRecursive(test2_3);

        System.out.println("Test Case 2: [1, 1, 2, 3, 3]");
        System.out.println("Approach 1 (Dummy Rewiring) Result: " + toList(res2_1));
        System.out.println("Approach 2 (Classic Pointer)Result: " + toList(res2_2));
        System.out.println("Approach 3 (Recursive)      Result: " + toList(res2_3));
        boolean check2 = toList(res2_1).equals(Arrays.asList(1, 2, 3)) &&
                         toList(res2_2).equals(Arrays.asList(1, 2, 3)) &&
                         toList(res2_3).equals(Arrays.asList(1, 2, 3));
        System.out.println("Verification: " + (check2 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}