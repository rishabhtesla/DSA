package DSA.Coding.DSABasic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Shared Definition for Singly-Linked List Node.
 */


/**
 * PROBLEM STATEMENT:
 * You are given an array of k linked-lists 'lists', each linked-list is sorted in ascending order [00:00:21].
 * Merge all the linked-lists into one sorted linked-list and return it [00:00:49].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: lists = [[1,4,5], [1,3,4], [2,6]] [00:01:24, 00:01:30]
 * - Min-Heap polls nodes in ascending order:
 *   - Polls 1, 1, 2, 3, 4, 4, 5, 6 [00:06:52, 00:07:47]
 * - Grouped & merged result: 1 -> 1 -> 2 -> 3 -> 4 -> 4 -> 5 -> 6 [00:02:01]
 * 
 * Example 2: lists = []
 * - Base edge case: lists.length == 0 -> return null [00:08:39, 00:08:46].
 * - Result: []
 * 
 * Example 3: lists = [[1, 3], [2, 4], [1, 2, 3]] (Simulated inside video explanation [00:17:28, 00:20:52])
 * - Pass 1: Offer all nodes into PriorityQueue [00:18:17, 00:18:49].
 * - Pass 2: Reconstruct using dummy node:
 *   - Poll 1, 1 -> dummy points 1 -> 1 [00:19:35, 00:19:48]
 *   - Poll 2, 2 -> dummy points 1 -> 1 -> 2 -> 2 [00:20:01, 00:20:08]
 *   - Poll 3, 3 -> dummy points 1 -> 1 -> 2 -> 2 -> 3 -> 3 [00:20:13]
 *   - Poll 4    -> dummy points 1 -> 1 -> 2 -> 2 -> 3 -> 3 -> 4 [00:20:25]
 * - Post-Loop: dummy.next = null (severs leftover tail pointer on Node 4) [00:20:38].
 * - Result: [1, 1, 2, 2, 3, 3, 4] [00:20:52]
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Min-Heap PriorityQueue Node Reconstruction):
 * • Edge Cases:
 *   - If `lists.length == 0`, return `null` [00:08:39].
 *   - If `lists.length == 1`, return `lists[0]` directly [00:08:55, 00:09:08].
 * • PriorityQueue Initialization:
 *   - `PriorityQueue<ListNode> pq = new PriorityQueue<>((a, b) -> a.val - b.val)` [00:09:19, 00:09:39].
 * • Condition Boundaries:
 *   - Outer loop traversing heads: `for (int i = 0; i < lists.length; i++)` [00:10:58].
 *   - Inner list node collection loop: `while (tempHead != null)` [00:11:35].
 *   - Node extraction loop: `while (pq.size() > 0)` [00:13:29].
 * • Operational Steps:
 *   1. Filter empty array edge cases [00:08:39].
 *   2. Instantiate Min-Heap PriorityQueue with custom `a.val - b.val` comparator [00:09:19, 00:09:39].
 *   3. Populate Min-Heap by offering all nodes from all lists [00:11:35, 00:11:59].
 *   4. Create `dummy = new ListNode(-1)` sentinel and save `ans = dummy` anchor [00:13:11, 00:14:47].
 *   5. Poll nodes from heap one by one, attach `dummy.next = node`, and move `dummy = dummy.next` [00:13:42, 00:14:03].
 *   6. Disconnect potential circular or trailing links by setting `dummy.next = null` [00:16:28, 00:20:38].
 *   7. Return `ans.next` [00:16:37].
 * • Time Complexity: O(N log N) - Inserting all N total nodes across k lists into a PriorityQueue takes log N per node.
 * • Space Complexity: O(N) auxiliary space - PriorityQueue stores all N node references simultaneously.
 * • LOGIC BEHIND THIS APPROACH:
 *   Min-Heap automatically guarantees that nodes are popped in strictly ascending sorted order [00:06:41, 00:19:23]. 
 *   Inserting existing `ListNode` objects directly avoids creating new nodes, and explicitly setting `dummy.next = null` at the end prevents stale tail pointers from creating infinite cycles [00:07:10, 00:16:28].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 1: PriorityQueue Min-Heap - lists = [[1, 4], [2, 3]]):
 * Step 1: Offer all nodes into PriorityQueue -> PQ holds nodes: [1, 2, 3, 4]
 * Initial: dummy(-1), ans = dummy
 * 
 * Step 2: Poll and re-link:
 * - Poll Node(1): dummy.next = Node(1), dummy = Node(1)
 * - Poll Node(2): dummy.next = Node(2), dummy = Node(2)
 * - Poll Node(3): dummy.next = Node(3), dummy = Node(3)
 * - Poll Node(4): dummy.next = Node(4), dummy = Node(4)
 * 
 * Step 3: Post-loop cleanup:
 * - dummy is on Node(4) -> dummy.next = null (severs stale links) [00:16:28, 00:20:38].
 * Return ans.next -> [1, 2, 3, 4] [00:16:37].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 2: Pairwise Sequential Merge Strategy):
 * lists = [L1, L2, L3, L4]
 * Step 1: merge(L1, L2) -> L12 [00:03:32]
 * Step 2: merge(L12, L3) -> L123 [00:03:38]
 * Step 3: merge(L123, L4) -> L1234 [00:03:56]
 * Returns combined sorted list.
 */
public class MergeKSortedLists {

    // APPROACH 1: Min-Heap PriorityQueue Node Reconstruction (Anchor Strategy)
    public static ListNode mergeKListsOptimal(ListNode[] lists) {
        // Base edge cases [00:08:39, 00:08:55]
        if (lists == null || lists.length == 0) {
            return null; // Return null if array is empty [00:08:46]
        }
        if (lists.length == 1) {
            return lists[0]; // Return single list directly [00:09:08]
        }

        // Instantiate Min-Heap PriorityQueue sorted by node value ascending [00:09:19, 00:09:39]
        PriorityQueue<ListNode> pq = new PriorityQueue<>((a, b) -> a.val - b.val);

        // Step 1: Push all nodes from all lists into the PriorityQueue [00:10:58]
        for (int i = 0; i < lists.length; i++) {
            ListNode tempHead = lists[i]; // Head of current list [00:11:11]
            while (tempHead != null) {
                pq.add(tempHead);          // Insert node reference [00:11:59]
                tempHead = tempHead.next; // Advance iterator [00:12:08]
            }
        }

        // Step 2: Reconstruct sorted linked list using dummy node anchor [00:13:11]
        ListNode dummy = new ListNode(-1);
        ListNode ans = dummy; // Store anchor reference [00:14:47]

        // Poll nodes from Min-Heap in sorted order [00:13:29]
        while (pq.size() > 0) {
            ListNode node = pq.remove(); // Extract smallest node [00:13:52]
            dummy.next = node;           // Link node to merged chain [00:13:57]
            dummy = dummy.next;          // Advance construction pointer [00:14:03]
        }

        // Step 3: Sever stale forward connection on final node to avoid cycles [00:16:28, 00:20:38]
        dummy.next = null;

        return ans.next; // Return head skipping dummy sentinel [00:16:37]
    }

    // APPROACH 2: Pairwise Sequential Merge Strategy (Reusing LeetCode 21 Logic)
    // Merges lists sequentially one by one using a two-list merge helper [00:03:14, 00:05:08].
    public static ListNode mergeKListsPairwise(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;
        if (lists.length == 1) return lists[0];

        ListNode ans = mergeTwoLists(lists[0], lists[1]);

        for (int i = 2; i < lists.length; i++) {
            ans = mergeTwoLists(ans, lists[i]);
        }

        return ans;
    }

    private static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(-1);
        ListNode curr = dummy;

        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                curr.next = l1;
                l1 = l1.next;
            } else {
                curr.next = l2;
                l2 = l2.next;
            }
            curr = curr.next;
        }

        curr.next = (l1 == null) ? l2 : l1;
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

        // --- TEST CASE 1 (Standard 3 Sorted Lists [[1,4,5], [1,3,4], [2,6]]) ---
        ListNode[] lists1_1 = new ListNode[]{
            buildList(new int[]{1, 4, 5}),
            buildList(new int[]{1, 3, 4}),
            buildList(new int[]{2, 6})
        };
        ListNode[] lists1_2 = new ListNode[]{
            buildList(new int[]{1, 4, 5}),
            buildList(new int[]{1, 3, 4}),
            buildList(new int[]{2, 6})
        };

        ListNode res1_1 = mergeKListsOptimal(lists1_1);
        ListNode res1_2 = mergeKListsPairwise(lists1_2);

        System.out.println("Test Case 1: [[1,4,5], [1,3,4], [2,6]]");
        System.out.println("Approach 1 (Min-Heap PQ) Result: " + toList(res1_1));
        System.out.println("Approach 2 (Pairwise)    Result: " + toList(res1_2));
        boolean check1 = toList(res1_1).equals(Arrays.asList(1, 1, 2, 3, 4, 4, 5, 6)) &&
                         toList(res1_2).equals(Arrays.asList(1, 1, 2, 3, 4, 4, 5, 6));
        System.out.println("Verification: " + (check1 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (From Video Board Explanation [[1,3], [2,4], [1,2,3]]) ---
        ListNode[] lists2_1 = new ListNode[]{
            buildList(new int[]{1, 3}),
            buildList(new int[]{2, 4}),
            buildList(new int[]{1, 2, 3})
        };

        ListNode res2_1 = mergeKListsOptimal(lists2_1);

        System.out.println("Test Case 2: [[1,3], [2,4], [1,2,3]]");
        System.out.println("Approach 1 (Min-Heap PQ) Result: " + toList(res2_1));
        boolean check2 = toList(res2_1).equals(Arrays.asList(1, 1, 2, 2, 3, 3, 4));
        System.out.println("Verification: " + (check2 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}