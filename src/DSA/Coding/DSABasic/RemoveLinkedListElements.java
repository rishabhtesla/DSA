package DSA.Coding.DSABasic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Shared Definition for Singly-Linked List Node.
 */


/**
 * PROBLEM STATEMENT:
 * Given the head of a linked list and an integer val, remove all the nodes 
 * of the linked list that have Node.val == val, and return the new head [00:00:22, 00:00:30].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: head = [1, 2, 6, 3, 4, 5, 6], val = 6 [00:00:36]
 * - Prepend dummy node: dummy(-1) -> 1 -> 2 -> 6 -> 3 -> 4 -> 5 -> 6
 * - Process node 6 at index 2: bypass -> 2 points to 3 [00:01:14].
 * - Process trailing node 6: bypass -> 5 points to null [00:01:19].
 * - Result: [1, 2, 3, 4, 5] [00:01:00].
 * 
 * Example 2: head = [6, 3, 6, 6, 5], val = 6 (Simulated inside video explanation [00:04:29, 00:06:40])
 * - Prepend dummy: dummy(-1) -> 6 -> 3 -> 6 -> 6 -> 5
 * - current at dummy(-1): current.next.val == 6 -> bypass head -> dummy points to 3 [00:05:11].
 * - current stays at dummy(-1): current.next.val (3) != 6 -> move current to 3 [00:05:36].
 * - current at 3: current.next.val (6) == 6 -> bypass -> 3 points to second 6 [00:05:48].
 * - current STAYS at 3: current.next.val (second 6) == 6 -> bypass again -> 3 points to 5 [00:06:10].
 * - Result: [3, 5].
 * 
 * Example 3: head = [7, 7, 7, 7], val = 7
 * - All nodes bypassed from dummy -> return dummy.next (null).
 * - Result: []
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Dummy Sentinel Node Strategy):
 * • Edge Cases:
 *   - Base condition: `if (head == null) return null` [00:06:59].
 * • Pointer Initialization:
 *   - `dummy = new ListNode(-1)` [00:07:11].
 *   - Link dummy to head: `dummy.next = head` [00:07:34].
 *   - Traversal pointer: `current = dummy` [00:07:48].
 * • Condition Boundaries:
 *   - Main traversal loop: `while (current.next != null)` [00:06:47, 00:07:59].
 *   - Match check: `if (current.next.val == val)` -> bypass `current.next = current.next.next` [00:08:10, 00:08:28].
 *   - Non-match case: `else` -> advance `current = current.next` [00:08:36].
 * • Operational Steps:
 *   1. Check if `head == null` [00:06:59].
 *   2. Instantiate dummy sentinel node (-1) and link `dummy.next = head` [00:07:11, 00:07:34].
 *   3. Set `current = dummy` [00:07:48].
 *   4. While `current.next != null` [00:07:59]:
 *      - If `current.next.val == val`, skip node by linking `current.next = current.next.next` [00:08:28].
 *      - Otherwise, advance `current = current.next` [00:08:36].
 *   5. Return `dummy.next` as new head [00:08:51].
 * • Time Complexity: O(N) - Single pass through all N list nodes.
 * • Space Complexity: O(1) auxiliary space - Modifies node references in-place using a single dummy node.
 * • LOGIC BEHIND THIS APPROACH:
 *   Inspecting `current.next` allows deleting head nodes without conditional logic [00:04:51]. 
 *   Refraining from advancing `current` on node removal handles consecutive target values safely [00:02:59, 00:06:10].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 1: Dummy Sentinel - head = [3, 4, 3], val = 3):
 * Initial: dummy(-1) -> 3 -> 4 -> 3, current = dummy(-1) [00:09:44]
 * 
 * Pass 1 (current = dummy(-1)):
 * - current.next.val (3) == val (3) -> bypass! dummy.next = Node(4) [00:10:04].
 * - current STAYS at dummy(-1).
 * 
 * Pass 2 (current = dummy(-1)):
 * - current.next.val (4) != val (3) -> current moves to Node(4) [00:10:20].
 * 
 * Pass 3 (current = Node(4)):
 * - current.next.val (3) == val (3) -> bypass! Node(4).next = null [00:10:34].
 * - current STAYS at Node(4).
 * 
 * Pass 4: current.next is null -> Loop terminates [00:10:51].
 * Return dummy.next -> Node(4) [00:10:56].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 2: Recursive Sub-problem Elimination Strategy):
 * head = [3, 4, 3], val = 3
 * Frame 1: head = 3. Recurse on [4, 3] -> returns [4]
 * Frame 2: head = 4. Recurse on [3] -> returns null
 * Frame 3: head = 3. Recurse on null -> returns null. head(3) == 3 -> returns null.
 * Unwind Frame 2: head(4) != 3 -> 4.next = null -> returns [4].
 * Unwind Frame 1: head(3) == 3 -> returns 3.next ([4]).
 * Result: [4].
 */
public class RemoveLinkedListElements {

    // APPROACH 1: Dummy Sentinel Node Strategy (Anchor Strategy - O(1) Space)
    public static ListNode removeElementsOptimal(ListNode head, int val) {
        // Base case: empty list [00:06:59]
        if (head == null) {
            return null;
        }

        // Create dummy sentinel node to handle head removals seamlessly [00:07:11]
        ListNode dummy = new ListNode(-1);
        dummy.next = head; // Attach dummy to original head [00:07:34]

        ListNode current = dummy; // Iteration pointer starting at dummy [00:07:48]

        // Iterate until there are no further nodes to inspect [00:07:59]
        while (current.next != null) {
            // Target value match detected on successor node [00:08:10]
            if (current.next.val == val) {
                // Bypass target node by re-linking next pointer [00:08:28]
                current.next = current.next.next;
                // Note: current pointer does NOT advance here to inspect consecutive matches [00:02:59]
            } else {
                // Unique node: advance current pointer forward [00:08:36]
                current = current.next;
            }
        }

        return dummy.next; // Return head skipping sentinel node [00:08:51]
    }

    // APPROACH 2: Pure Recursive Sub-problem Elimination Strategy
    // Solves node removal recursively by processing the suffix sub-list first 
    // and dropping the current head node if its value matches `val`.
    public static ListNode removeElementsRecursive(ListNode head, int val) {
        if (head == null) return null;

        head.next = removeElementsRecursive(head.next, val);

        return (head.val == val) ? head.next : head;
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

        // --- TEST CASE 1 (Standard Removal [1,2,6,3,4,5,6], val=6) ---
        ListNode test1_1 = buildList(new int[]{1, 2, 6, 3, 4, 5, 6});
        ListNode test1_2 = buildList(new int[]{1, 2, 6, 3, 4, 5, 6});

        ListNode res1_1 = removeElementsOptimal(test1_1, 6);
        ListNode res1_2 = removeElementsRecursive(test1_2, 6);

        System.out.println("Test Case 1: [1, 2, 6, 3, 4, 5, 6], val = 6");
        System.out.println("Approach 1 (Dummy Sentinel) Result: " + toList(res1_1));
        System.out.println("Approach 2 (Recursive)      Result: " + toList(res1_2));
        boolean check1 = toList(res1_1).equals(Arrays.asList(1, 2, 3, 4, 5)) &&
                         toList(res1_2).equals(Arrays.asList(1, 2, 3, 4, 5));
        System.out.println("Verification: " + (check1 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (Consecutive & Head Removal [6, 3, 6, 6, 5], val=6) ---
        ListNode test2_1 = buildList(new int[]{6, 3, 6, 6, 5});
        ListNode test2_2 = buildList(new int[]{6, 3, 6, 6, 5});

        ListNode res2_1 = removeElementsOptimal(test2_1, 6);
        ListNode res2_2 = removeElementsRecursive(test2_2, 6);

        System.out.println("Test Case 2: [6, 3, 6, 6, 5], val = 6");
        System.out.println("Approach 1 (Dummy Sentinel) Result: " + toList(res2_1));
        System.out.println("Approach 2 (Recursive)      Result: " + toList(res2_2));
        boolean check2 = toList(res2_1).equals(Arrays.asList(3, 5)) &&
                         toList(res2_2).equals(Arrays.asList(3, 5));
        System.out.println("Verification: " + (check2 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 3 (All Elements Match [7, 7, 7, 7], val=7) ---
        ListNode test3_1 = buildList(new int[]{7, 7, 7, 7});
        ListNode test3_2 = buildList(new int[]{7, 7, 7, 7});

        ListNode res3_1 = removeElementsOptimal(test3_1, 7);
        ListNode res3_2 = removeElementsRecursive(test3_2, 7);

        System.out.println("Test Case 3: [7, 7, 7, 7], val = 7");
        System.out.println("Approach 1 (Dummy Sentinel) Result: " + toList(res3_1));
        System.out.println("Approach 2 (Recursive)      Result: " + toList(res3_2));
        boolean check3 = toList(res3_1).isEmpty() && toList(res3_2).isEmpty();
        System.out.println("Verification: " + (check3 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}