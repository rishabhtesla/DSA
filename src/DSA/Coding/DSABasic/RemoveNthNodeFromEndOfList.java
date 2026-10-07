package DSA.Coding.DSABasic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Shared Definition for Singly-Linked List Node.
 */


/**
 * PROBLEM STATEMENT:
 * Given the head of a linked list, remove the nth node from the end of the list 
 * and return its head [00:00:22, 00:00:46].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: head = [1, 2, 3, 4, 5], n = 2 [00:00:35]
 * - 2nd node from end is Node(4) [00:00:42].
 * - Advance fast 2 steps -> fast at Node(3) [00:04:12].
 * - Step fast and slow together until fast.next == null:
 *   - slow stops at Node(3) (predecessor) [00:04:54].
 * - Bypass target: 3.next = 3.next.next -> Node(3) points to Node(5) [00:07:48].
 * - Result: [1, 2, 3, 5] [00:00:52]
 * 
 * Example 2: head = [1], n = 1
 * - Advance fast 1 step -> fast becomes null.
 * - Condition fast == null triggers: remove head -> return head.next (null).
 * - Result: []
 * 
 * Example 3: head = [1, 2, 3], n = 3 (Removing first head node from end [00:12:00])
 * - Advance fast 3 steps -> fast hits null [00:12:41].
 * - Target to remove is Head Node(1) [00:09:04].
 * - Return head.next -> Node(2) [00:09:28, 00:12:55].
 * - Result: [2, 3]
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Two-Pointer Fast/Slow Gap Strategy):
 * • Edge Cases:
 *   - Empty list check: `if (head == null) return null` [00:06:05].
 * • Pointer & Variable Initialization:
 *   - `fast = head` tracks leading edge [00:06:17].
 *   - `slow = head` tracks predecessor of target node [00:06:25].
 * • Condition Boundaries:
 *   - Gap creation loop: `while (n > 0)` advance `fast = fast.next`, `n--` [00:06:33, 00:06:44].
 *   - Head removal check: `if (fast == null) return head.next` [00:09:18, 00:12:49].
 *   - Simultaneous traversal loop: `while (fast.next != null)` move `fast` and `slow` [00:06:51, 00:07:04].
 * • Operational Steps:
 *   1. Initialize `fast = head` and `slow = head` [00:06:17, 00:06:25].
 *   2. Advance `fast` forward `n` times to establish an $n$-node distance gap [00:06:33].
 *   3. If `fast == null`, the node to remove is `head`; return `head.next` [00:09:18, 00:12:49].
 *   4. While `fast.next != null`, advance both `fast = fast.next` and `slow = slow.next` [00:06:51, 00:07:04].
 *   5. Bypass target node: `slow.next = slow.next.next` [00:07:48].
 *   6. Return original `head` [00:08:14].
 * • Time Complexity: O(L) - Single pass traversal over L nodes in list.
 * • Space Complexity: O(1) auxiliary space - Uses fixed pointer variables without extra memory allocations.
 * • LOGIC BEHIND THIS APPROACH:
 *   Maintaining a distance gap of $n$ nodes between `fast` and `slow` guarantees that when `fast` reaches the last node (`fast.next == null`), `slow` naturally stops at the $(n+1)^{\text{th}}$ node from the end [00:04:22, 00:05:04]. 
 *   Stopping at the predecessor node allows immediate $O(1)$ node removal [00:03:14, 00:07:48].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 1: Fast/Slow Gap - head = [1, 2, 3, 4, 5, 6], n = 3):
 * Initial: fast = 1, slow = 1, n = 3 [00:10:29]
 * Step 1: Advance fast n=3 times -> fast moves 1 -> 2 -> 3 -> 4 [00:10:57].
 * Step 2: Check fast == null (false).
 * Step 3: Traverse while fast.next != null [00:11:11]:
 *   - fast(4).next != null -> slow = 2, fast = 5
 *   - fast(5).next != null -> slow = 3, fast = 6
 *   - fast(6).next == null -> Loop terminates! [00:11:26]
 * Step 4: Bypass target node: slow(3).next = 3.next.next (5) [00:11:39].
 * Return head -> [1, 2, 3, 5, 6].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 2: Dummy Node Uniform Boundary Strategy - head = [1, 2], n = 2):
 * Create dummy node (0) pointing to head: dummy -> 1 -> 2
 * fast = dummy, slow = dummy
 * Advance fast n+1 (3) times: fast moves dummy -> 1 -> 2 -> null.
 * Step fast and slow until fast == null: fast is already null!
 * Bypass target: slow(dummy).next = dummy.next.next (Node 2).
 * Return dummy.next -> [2].
 */
public class RemoveNthNodeFromEndOfList {

    // APPROACH 1: Two-Pointer Fast/Slow Gap Strategy (Anchor Strategy)
    public static ListNode removeNthFromEndOptimal(ListNode head, int n) {
        if (head == null) return null; // Base case [00:06:05]

        ListNode fast = head; // Leading pointer [00:06:17]
        ListNode slow = head; // Trailing predecessor pointer [00:06:25]

        // Advance fast pointer n steps forward [00:06:33]
        while (n > 0) {
            fast = fast.next;
            n--;
        }

        // Edge Case: if fast is null, target node is head itself [00:09:18, 00:12:49]
        if (fast == null) {
            return head.next; // Remove first node [00:09:28]
        }

        // Advance both pointers until fast reaches the last node [00:06:51]
        while (fast.next != null) {
            fast = fast.next; // Advance fast [00:07:04]
            slow = slow.next; // Advance slow [00:07:16]
        }

        // Bypass target node by re-linking predecessor's next pointer [00:07:48]
        slow.next = slow.next.next;

        return head; // Return modified list head [00:08:14]
    }

    // APPROACH 2: Dummy Node Sentinel Strategy (Uniform Head Removal Logic)
    // Uses a dummy node preceding head to handle head removal uniformly without special branching.
    public static ListNode removeNthFromEndDummy(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode fast = dummy;
        ListNode slow = dummy;

        // Advance fast n + 1 steps to create gap
        for (int i = 0; i <= n; i++) {
            if (fast == null) return head;
            fast = fast.next;
        }

        // Move fast to end
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }

        // Delete Nth node from end
        slow.next = slow.next.next;

        return dummy.next;
    }

    // APPROACH 3: Two-Pass Size Calculation Strategy (Baseline Comparison)
    // Measures list length in Pass 1, calculates skip target, and removes node in Pass 2.
    public static ListNode removeNthFromEndTwoPass(ListNode head, int n) {
        if (head == null) return null;

        // Pass 1: Count length
        int length = 0;
        ListNode curr = head;
        while (curr != null) {
            length++;
            curr = curr.next;
        }

        // Edge Case: Remove head node
        if (length == n) {
            return head.next;
        }

        // Pass 2: Advance to (length - n - 1) position
        curr = head;
        for (int i = 1; i < length - n; i++) {
            curr = curr.next;
        }

        // Delete node
        curr.next = curr.next.next;

        return head;
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

        // --- TEST CASE 1 (Standard Middle Removal [1,2,3,4,5], n=2) ---
        ListNode test1_1 = buildList(new int[]{1, 2, 3, 4, 5});
        ListNode test1_2 = buildList(new int[]{1, 2, 3, 4, 5});
        ListNode test1_3 = buildList(new int[]{1, 2, 3, 4, 5});

        ListNode res1_1 = removeNthFromEndOptimal(test1_1, 2);
        ListNode res1_2 = removeNthFromEndDummy(test1_2, 2);
        ListNode res1_3 = removeNthFromEndTwoPass(test1_3, 2);

        System.out.println("Test Case 1: [1, 2, 3, 4, 5], n = 2");
        System.out.println("Approach 1 (Fast/Slow Gap) Result: " + toList(res1_1));
        System.out.println("Approach 2 (Dummy Sentinel) Result: " + toList(res1_2));
        System.out.println("Approach 3 (Two-Pass Size)  Result: " + toList(res1_3));
        boolean check1 = toList(res1_1).equals(Arrays.asList(1, 2, 3, 5)) &&
                         toList(res1_2).equals(Arrays.asList(1, 2, 3, 5)) &&
                         toList(res1_3).equals(Arrays.asList(1, 2, 3, 5));
        System.out.println("Verification: " + (check1 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (Head Removal Edge Case [1, 2, 3], n=3) ---
        ListNode test2_1 = buildList(new int[]{1, 2, 3});
        ListNode test2_2 = buildList(new int[]{1, 2, 3});
        ListNode test2_3 = buildList(new int[]{1, 2, 3});

        ListNode res2_1 = removeNthFromEndOptimal(test2_1, 3);
        ListNode res2_2 = removeNthFromEndDummy(test2_2, 3);
        ListNode res2_3 = removeNthFromEndTwoPass(test2_3, 3);

        System.out.println("Test Case 2 (Head Removal): [1, 2, 3], n = 3");
        System.out.println("Approach 1 (Fast/Slow Gap) Result: " + toList(res2_1));
        System.out.println("Approach 2 (Dummy Sentinel) Result: " + toList(res2_2));
        System.out.println("Approach 3 (Two-Pass Size)  Result: " + toList(res2_3));
        boolean check2 = toList(res2_1).equals(Arrays.asList(2, 3)) &&
                         toList(res2_2).equals(Arrays.asList(2, 3)) &&
                         toList(res2_3).equals(Arrays.asList(2, 3));
        System.out.println("Verification: " + (check2 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}