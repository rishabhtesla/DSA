package DSA.Coding.DSABasic.mergelists;

import DSA.Coding.DSABasic.ListNode;

import java.util.*;

/**
 * PROBLEM STATEMENT:
 * You are given the heads of two sorted linked lists 'list1' and 'list2' [00:00:21].
 * Merge the two lists into one sorted list. The list should be made by splicing together 
 * the nodes of the first two lists [00:00:25, 00:00:35].
 * Return the head of the merged linked list.
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: list1 = [1, 2, 4], list2 = [1, 3, 4]
 * - Compare heads: list1 val 1 <= list2 val 1 -> attach list1 (1), advance list1.
 * - Compare list1 (2) and list2 (1) -> attach list2 (1), advance list2.
 * - Compare list1 (2) and list2 (3) -> attach list1 (2), advance list1.
 * - Compare list1 (4) and list2 (3) -> attach list2 (3), advance list2.
 * - Compare list1 (4) and list2 (4) -> attach list1 (4), advance list1.
 * - list1 becomes null -> append remaining list2 (4).
 * - Result: [1, 1, 2, 3, 4, 4]
 * 
 * Example 2: list1 = [1, 5, 7], list2 = [2, 4, 6, 8, 9] (Simulated inside video explanation [00:10:44, 00:15:02])
 * - Create dummy node (val -1) and pointer `ans = dummy` [00:05:40, 00:09:35].
 * - Traversal comparisons:
 *   - p1(1) < p2(2) -> dummy.next = p1(1), advance p1 & dummy [00:07:04, 00:12:15].
 *   - p2(2) < p1(5) -> dummy.next = p2(2), advance p2 & dummy [00:07:42, 00:12:58].
 *   - p2(4) < p1(5) -> dummy.next = p2(4), advance p2 & dummy [00:13:28].
 *   - p1(5) < p2(6) -> dummy.next = p1(5), advance p1 & dummy [00:13:58].
 *   - p2(6) < p1(7) -> dummy.next = p2(6), advance p2 & dummy [00:14:12].
 *   - p1(7) < p2(8) -> dummy.next = p1(7), advance p1 & dummy [00:14:22].
 * - p1 reaches null: attach remaining tail `dummy.next = p2` (attaches 8 -> 9 directly) [00:08:29, 00:14:39].
 * - Return `ans.next` (skips dummy anchor node) [00:09:56, 00:14:58].
 * - Result: [1, 2, 4, 5, 6, 7, 8, 9]
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Dummy Anchor Pointer with Two-Way Comparison Loop):
 * • Pointer Initialization:
 *   - `dummy = new ListNode(-1)` serves as fixed anchor [00:05:40].
 *   - `ans = dummy` stores original head reference for final return [00:09:35].
 *   - `p1 = list1`, `p2 = list2` iterate source linked lists [00:06:00, 00:06:08].
 * • Condition Boundaries:
 *   - Interleaving loop: `while (p1 != null && p2 != null)` [00:06:19].
 *   - Node choice: `if (p1.val < p2.val)` attach p1 [00:06:43]; `else` attach p2 [00:07:42].
 *   - Post-loop cleanup: attach non-null remaining list using `dummy.next = (p1 == null) ? p2 : p1` [00:08:26, 00:08:44].
 * • Operational Steps:
 *   1. Instantiate `dummy` node with arbitrary placeholder value `-1` [00:05:40, 00:05:50].
 *   2. Save head anchor `ans = dummy` [00:09:35].
 *   3. Run while loop as long as both pointers `p1` and `p2` are non-null [00:06:19].
 *   4. Append smaller node to `dummy.next`, advance corresponding list pointer (`p1 = p1.next` or `p2 = p2.next`) [00:07:04, 00:07:51].
 *   5. Advance construction pointer `dummy = dummy.next` [00:08:10].
 *   6. After loop, splice any remaining nodes directly to `dummy.next` [00:08:29, 00:08:44].
 *   7. Return `ans.next` [00:09:56].
 * • Time Complexity: O(n + m) - Where n and m are lengths of list1 and list2.
 * • Space Complexity: O(1) auxiliary space - Splices existing node pointers in-place without generating new nodes [00:00:35].
 * • LOGIC BEHIND THIS APPROACH:
 *   Using a `dummy` node simplifies list construction by eliminating special conditional branches for setting the head node [00:02:14]. 
 *   Splicing pointer references directly rewires existing nodes in-place, satisfying $O(1)$ auxiliary space restrictions [00:00:35, 00:13:14]. 
 *   When one list exhausts, the non-empty list remains sorted, so linking its head directly attaches all remaining elements in $O(1)$ step [00:04:59, 00:08:29].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 1: Dummy Anchor - list1 = [1, 5], list2 = [2, 4]):
 * Initial: dummy(-1), ans = dummy, p1 = 1, p2 = 2 [00:11:16]
 * Step 1: p1.val (1) < p2.val (2) -> dummy.next = p1(1). p1 = p1.next (5), dummy = dummy.next (1) [00:12:15]
 * Step 2: p2.val (2) < p1.val (5) -> dummy.next = p2(2). p2 = p2.next (4), dummy = dummy.next (2) [00:12:58]
 * Step 3: p2.val (4) < p1.val (5) -> dummy.next = p2(4). p2 = p2.next (null), dummy = dummy.next (4) [00:13:28]
 * Loop ends (p2 is null).
 * Cleanup: p1 == null (false) -> dummy.next = p1(5) [00:14:35].
 * Return ans.next -> [1, 2, 4, 5] [00:14:58].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 2: Recursive Divide & Conquer - list1 = [1, 3], list2 = [2]):
 * Frame 1: merge(1, 2) -> 1 < 2 -> 1.next = merge(3, 2)
 * Frame 2: merge(3, 2) -> 3 > 2 -> 2.next = merge(3, null)
 * Frame 3: merge(3, null) -> returns 3 (base case)
 * Unwind: 2.next = 3 -> returns 2 -> 1.next = 2 -> returns 1
 * Output = 1 -> 2 -> 3.
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 3: PriorityQueue Min-Heap Strategy - list1 = [1, 4], list2 = [2, 3]):
 * MinHeap insert heads: Heap = [1 (list1), 2 (list2)]
 * Step 1: Poll min 1 -> append to result list. Offer next node 4 -> Heap = [2, 4]
 * Step 2: Poll min 2 -> append. Offer 3 -> Heap = [3, 4]
 * Step 3: Poll min 3 -> append. Offer null -> Heap = [4]
 * Step 4: Poll min 4 -> append -> Result = 1 -> 2 -> 3 -> 4.
 */
public class MergeTwoSortedLists {

    // APPROACH 1: Dummy Anchor Pointer with Two-Way Comparison Loop (Anchor Strategy)
    public static ListNode mergeTwoListsOptimal(ListNode list1, ListNode list2) {
        // Create dummy node with placeholder value [00:05:40]
        ListNode dummy = new ListNode(-1);
        ListNode ans = dummy; // Save dummy head reference for final return [00:09:35]

        ListNode p1 = list1; // Traversal pointer for list 1 [00:06:00]
        ListNode p2 = list2; // Traversal pointer for list 2 [00:06:08]

        // Loop while both lists have remaining unmerged nodes [00:06:19]
        while (p1 != null && p2 != null) {
            if (p1.val < p2.val) {
                dummy.next = p1; // Attach smaller node from list 1 [00:07:04]
                p1 = p1.next;    // Advance pointer in list 1 [00:07:16]
            } else {
                dummy.next = p2; // Attach smaller node from list 2 [00:07:42]
                p2 = p2.next;    // Advance pointer in list 2 [00:07:51]
            }
            dummy = dummy.next;  // Advance construction pointer [00:08:10]
        }

        // Attach remaining tail nodes from non-empty list [00:08:26, 00:08:44]
        if (p1 == null) {
            dummy.next = p2; // Attach remaining list2 nodes [00:08:29]
        } else {
            dummy.next = p1; // Attach remaining list1 nodes [00:08:44]
        }

        return ans.next; // Return head skipping placeholder dummy [00:09:56]
    }

    // APPROACH 2: Pure Recursive Divide & Conquer Strategy
    // Solves merging by recursively choosing the smaller node and linking it 
    // to the result of merging the remaining sub-lists.
    public static ListNode mergeTwoListsRecursive(ListNode list1, ListNode list2) {
        if (list1 == null) return list2;
        if (list2 == null) return list1;

        if (list1.val <= list2.val) {
            list1.next = mergeTwoListsRecursive(list1.next, list2);
            return list1;
        } else {
            list2.next = mergeTwoListsRecursive(list1, list2.next);
            return list2;
        }
    }

    // APPROACH 3: Min-Heap PriorityQueue Strategy
    // Uses a Min-Heap to poll the smallest node dynamically. 
    // Useful strategy when scaling up to merging K sorted lists (LeetCode 23).
    public static ListNode mergeTwoListsMinHeap(ListNode list1, ListNode list2) {
        if (list1 == null) return list2;
        if (list2 == null) return list1;

        PriorityQueue<ListNode> minHeap = new PriorityQueue<>(Comparator.comparingInt(node -> node.val));

        if (list1 != null) minHeap.add(list1);
        if (list2 != null) minHeap.add(list2);

        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;

        while (!minHeap.isEmpty()) {
            ListNode smallest = minHeap.poll();
            curr.next = smallest;
            curr = curr.next;

            if (smallest.next != null) {
                minHeap.add(smallest.next);
            }
        }

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

    // Helper method to convert linked list to list for verification display
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

        // --- TEST CASE 1 (Standard Merging) ---
        ListNode l1_1 = buildList(new int[]{1, 2, 4});
        ListNode l1_2 = buildList(new int[]{1, 3, 4});

        ListNode res1_1 = mergeTwoListsOptimal(l1_1, l1_2);

        System.out.println("Test Case 1: list1 = [1,2,4], list2 = [1,3,4]");
        System.out.println("Approach 1 (Dummy Anchor) Result: " + toList(res1_1));
        System.out.println("Verification: " + (toList(res1_1).equals(Arrays.asList(1, 1, 2, 3, 4, 4)) ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (Unequal Lengths from Video Explanation) ---
        ListNode l2_1 = buildList(new int[]{1, 5, 7});
        ListNode l2_2 = buildList(new int[]{2, 4, 6, 8, 9});

        ListNode res2_1 = mergeTwoListsOptimal(l2_1, l2_2);

        System.out.println("Test Case 2: list1 = [1,5,7], list2 = [2,4,6,8,9]");
        System.out.println("Approach 1 (Dummy Anchor) Result: " + toList(res2_1));
        System.out.println("Verification: " + (toList(res2_1).equals(Arrays.asList(1, 2, 4, 5, 6, 7, 8, 9)) ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 3 (Recursive Verification) ---
        ListNode l3_1 = buildList(new int[]{1, 2, 4});
        ListNode l3_2 = buildList(new int[]{1, 3, 4});

        ListNode res3_2 = mergeTwoListsRecursive(l3_1, l3_2);

        System.out.println("Test Case 3 (Recursive): list1 = [1,2,4], list2 = [1,3,4]");
        System.out.println("Approach 2 (Recursive)    Result: " + toList(res3_2));
        System.out.println("Verification: " + (toList(res3_2).equals(Arrays.asList(1, 1, 2, 3, 4, 4)) ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}