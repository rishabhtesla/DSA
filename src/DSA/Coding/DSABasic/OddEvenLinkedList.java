package DSA.Coding.DSABasic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Shared Definition for Singly-Linked List Node.
 */


/**
 * PROBLEM STATEMENT:
 * Given the head of a singly linked list, group all the nodes with odd indices together 
 * followed by the nodes with even indices, and return the reordered list [00:00:22, 00:00:36].
 * The relative order inside both groups should remain as it was in the input [00:00:53].
 * Solve in O(1) extra space and O(n) time complexity [00:00:58].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: head = [1, 2, 3, 4, 5, 6] [00:01:10]
 * - Odd-indexed nodes: Node 1 (1), Node 3 (3), Node 5 (5) [00:01:30]
 * - Even-indexed nodes: Node 2 (2), Node 4 (4), Node 6 (6) [00:01:35]
 * - Grouped result: 1 -> 3 -> 5 -> 2 -> 4 -> 6 [00:01:51]
 * 
 * Example 2: head = [2, 1, 3, 5, 6, 4, 7]
 * - Odd-indexed nodes: Node 1 (2), Node 3 (3), Node 5 (6), Node 7 (7)
 * - Even-indexed nodes: Node 2 (1), Node 4 (5), Node 6 (4)
 * - Grouped result: 2 -> 3 -> 6 -> 7 -> 1 -> 5 -> 4
 * 
 * Example 3: head = [2, 3, 4, 5, 6] (Simulated inside video explanation [00:16:51, 00:19:26])
 * - Initial pointers: oddHead = Node(2), evenHead = Node(3), evenStart = Node(3) [00:17:07, 00:17:14]
 * - Pass 1:
 *   - oddHead.next = 2.next.next -> Node(4) [00:17:33]
 *   - evenHead.next = 3.next.next -> Node(5) [00:17:46]
 *   - oddHead moves to 4, evenHead moves to 5 [00:18:03]
 * - Pass 2:
 *   - oddHead.next = 4.next.next -> Node(6) [00:18:25]
 *   - evenHead.next = 5.next.next -> null [00:18:33]
 *   - oddHead moves to 6, evenHead moves to null [00:18:52]
 * - Loop terminates because evenHead is null [00:18:52].
 * - Attach lists: oddHead.next (Node 6) = evenStart (Node 3) [00:18:57].
 * - Result: 2 -> 4 -> 6 -> 3 -> 5 [00:19:20].
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Interleaved Two-Pointer In-Place Rearrangement):
 * • Edge Cases:
 *   - If `head == null || head.next == null || head.next.next == null`, list length <= 2 requires no changes [00:15:29, 00:15:50].
 * • Pointer Initialization:
 *   - `oddHead = head` tracks current odd node [00:10:49].
 *   - `evenHead = head.next` tracks current even node [00:10:55].
 *   - `evenStart = evenHead` saves starting point of even list [00:11:18].
 * • Condition Boundaries:
 *   - Traversal loop condition: `while (evenHead != null && evenHead.next != null)` [00:12:41, 00:14:06].
 *   - Leapfrog linkage for odd nodes: `oddHead.next = oddHead.next.next` [00:11:36].
 *   - Leapfrog linkage for even nodes: `evenHead.next = evenHead.next.next` [00:11:49].
 * • Operational Steps:
 *   1. Check base edge cases (`head == null` or length <= 2) and return `head` [00:15:29].
 *   2. Set pointers `oddHead = head`, `evenHead = head.next`, and save `evenStart = evenHead` [00:10:49, 00:11:18].
 *   3. Loop while `evenHead != null && evenHead.next != null` [00:12:41, 00:14:06].
 *   4. Link current `oddHead` to next odd node (`oddHead.next.next`) [00:11:36].
 *   5. Link current `evenHead` to next even node (`evenHead.next.next`) [00:11:49].
 *   6. Advance `oddHead = oddHead.next` and `evenHead = evenHead.next` [00:12:08, 00:12:17].
 *   7. After loop ends, join end of odd sub-list to start of even sub-list: `oddHead.next = evenStart` [00:14:26].
 *   8. Return original `head` [00:14:34].
 * • Time Complexity: O(n) - Single pass through all n nodes [00:00:58].
 * • Space Complexity: O(1) auxiliary space - Re-links existing pointers in-place without generating new nodes [00:00:58].
 * • LOGIC BEHIND THIS APPROACH:
 *   Since `evenHead` always stays ahead of `oddHead`, checking `evenHead != null && evenHead.next != null` safely prevents `NullPointerException` errors on lists with either odd or even lengths [00:10:12, 00:14:06]. 
 *   Leapfrogging pointers directly severs and reconstructs forward links in $O(1)$ space [00:06:55, 00:07:10].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 1: Interleaved Two-Pointer - head = [1, 2, 3, 4, 5]):
 * Initial: oddHead = 1, evenHead = 2, evenStart = 2
 * Loop 1:
 * - oddHead.next = 1.next.next (3) -> 1 points to 3 [00:11:36]
 * - evenHead.next = 2.next.next (4) -> 2 points to 4 [00:11:49]
 * - oddHead = 3, evenHead = 4 [00:12:08]
 * Loop 2:
 * - oddHead.next = 3.next.next (5) -> 3 points to 5
 * - evenHead.next = 4.next.next (null) -> 4 points to null
 * - oddHead = 5, evenHead = null
 * Loop Ends (evenHead is null).
 * Attach: oddHead.next (5.next) = evenStart (2) -> 5 points to 2 [00:14:26].
 * Return head -> [1, 3, 5, 2, 4].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 2: Array Buffer Strategy - head = [1, 2, 3, 4]):
 * Collect all nodes into ArrayList: [node1, node2, node3, node4]
 * Filter odd indices: [node1, node3]
 * Filter even indices: [node2, node4]
 * Re-link sequential nodes: node1 -> node3 -> node2 -> node4 -> null.
 * Return node1.
 */
public class OddEvenLinkedList {

    // APPROACH 1: Interleaved Two-Pointer In-Place Rearrangement (Anchor Strategy)
    public static ListNode oddEvenListOptimal(ListNode head) {
        // Handle edge cases where list length is 0, 1, or 2 [00:15:29, 00:15:50]
        if (head == null || head.next == null || head.next.next == null) {
            return head;
        }

        ListNode oddHead = head;            // Pointer for odd list [00:10:49]
        ListNode evenHead = head.next;      // Pointer for even list [00:10:55]
        ListNode evenStart = evenHead;      // Anchor reference to join even sub-list later [00:11:18]

        // Loop as long as even head and its next node exist [00:12:41, 00:14:06]
        while (evenHead != null && evenHead.next != null) {
            // Re-link odd pointer to next odd node [00:11:36]
            oddHead.next = oddHead.next.next;
            
            // Re-link even pointer to next even node [00:11:49]
            evenHead.next = evenHead.next.next;

            // Advance both pointers forward [00:12:08, 00:12:17]
            oddHead = oddHead.next;
            evenHead = evenHead.next;
        }

        // Attach head of even list to tail of odd list [00:14:26]
        oddHead.next = evenStart;

        return head; // Return original head [00:14:34]
    }

    // APPROACH 2: Auxiliary List Buffer Strategy (O(n) Space Comparison Strategy)
    // Extracts node references into an intermediate buffer list before reconstructing links.
    public static ListNode oddEvenListBuffer(ListNode head) {
        if (head == null || head.next == null) return head;

        List<ListNode> oddNodes = new ArrayList<>();
        List<ListNode> evenNodes = new ArrayList<>();

        ListNode curr = head;
        int index = 1;

        while (curr != null) {
            if (index % 2 != 0) {
                oddNodes.add(curr);
            } else {
                evenNodes.add(curr);
            }
            curr = curr.next;
            index++;
        }

        // Re-link odd nodes
        for (int i = 0; i < oddNodes.size() - 1; i++) {
            oddNodes.get(i).next = oddNodes.get(i + 1);
        }

        // Re-link even nodes
        for (int i = 0; i < evenNodes.size() - 1; i++) {
            evenNodes.get(i).next = evenNodes.get(i + 1);
        }

        // Connect tail of odd list to head of even list
        if (!oddNodes.isEmpty()) {
            oddNodes.get(oddNodes.size() - 1).next = evenNodes.isEmpty() ? null : evenNodes.get(0);
        }
        if (!evenNodes.isEmpty()) {
            evenNodes.get(evenNodes.size() - 1).next = null;
        }

        return oddNodes.get(0);
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

        // --- TEST CASE 1 (Even Length List [1,2,3,4,5,6]) ---
        ListNode test1_1 = buildList(new int[]{1, 2, 3, 4, 5, 6});
        ListNode test1_2 = buildList(new int[]{1, 2, 3, 4, 5, 6});

        ListNode res1_1 = oddEvenListOptimal(test1_1);
        ListNode res1_2 = oddEvenListBuffer(test1_2);

        System.out.println("Test Case 1: [1, 2, 3, 4, 5, 6]");
        System.out.println("Approach 1 (Two-Pointer In-Place) Result: " + toList(res1_1));
        System.out.println("Approach 2 (Buffer Strategy)      Result: " + toList(res1_2));
        boolean check1 = toList(res1_1).equals(Arrays.asList(1, 3, 5, 2, 4, 6)) &&
                         toList(res1_2).equals(Arrays.asList(1, 3, 5, 2, 4, 6));
        System.out.println("Verification: " + (check1 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (Odd Length List from Video Explanation [2,3,4,5,6]) ---
        ListNode test2_1 = buildList(new int[]{2, 3, 4, 5, 6});
        ListNode test2_2 = buildList(new int[]{2, 3, 4, 5, 6});

        ListNode res2_1 = oddEvenListOptimal(test2_1);
        ListNode res2_2 = oddEvenListBuffer(test2_2);

        System.out.println("Test Case 2: [2, 3, 4, 5, 6]");
        System.out.println("Approach 1 (Two-Pointer In-Place) Result: " + toList(res2_1));
        System.out.println("Approach 2 (Buffer Strategy)      Result: " + toList(res2_2));
        boolean check2 = toList(res2_1).equals(Arrays.asList(2, 4, 6, 3, 5)) &&
                         toList(res2_2).equals(Arrays.asList(2, 4, 6, 3, 5));
        System.out.println("Verification: " + (check2 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}