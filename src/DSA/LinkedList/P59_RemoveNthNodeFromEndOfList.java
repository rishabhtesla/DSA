package DSA.LinkedList;

/**
 * ============================================================================
 * [59 / 65] - REMOVE NTH NODE FROM END OF LIST (LeetCode 19)
 * ============================================================================
 * 
 * PROBLEM:
 *   Given head, remove the nth node from the end of the list and return its head.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Two-Pointer Gap Strategy (Single Pass):
 *     Use a `dummy` node pointing to head to handle edge cases like removing head.
 *     1. Advance pointer `fast` forward by `n + 1` steps from `dummy`.
 *     2. Advance both `slow` and `fast` until `fast == null`.
 *     3. `slow` will now point exactly to the node BEFORE the target node!
 *     4. Delete target: `slow.next = slow.next.next`.
 *
 * COMPLEXITY:
 *   - Time:  O(n) - Single pass.
 *   - Space: O(1) - Pointers only.
 *
 *
 * EXAMPLE:
 *   The first main list is 1->2->3->4->5 with n=2; expected output is 1->2->3->5.
 *
 * VISUAL DRY RUN:
 *   Advance fast two nodes from dummy, then move fast/slow together until fast reaches the
 *   tail: slow is at node 3, so slow.next (node 4, the 2nd from end) is bypassed. Return 1->2->3->5.
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 */
public class P59_RemoveNthNodeFromEndOfList {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    public static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode fast = dummy;
        ListNode slow = dummy;

        for (int i = 0; i <= n; i++) {
            fast = fast.next;
        }

        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }

        slow.next = slow.next.next;
        return dummy.next;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);

        ListNode res = removeNthFromEnd(head, 2);
        System.out.print("P59 Output: ");
        while (res != null) {
            System.out.print(res.val + (res.next != null ? " -> " : ""));
            res = res.next;
        }
        System.out.println(); // Expected: 1 -> 2 -> 3 -> 5
    }
}