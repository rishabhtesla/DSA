package DSA.LinkedList;

/**
 * ============================================================================
 * [57 / 65] - REVERSE LINKED LIST II (LeetCode 92)
 * ============================================================================
 * 
 * PROBLEM:
 *   Given head and two integers left and right (1-indexed) where left <= right,
 *   reverse the nodes of the list from position left to right, and return head.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Head Insertion within Sublist (One Pass):
 *     1. Position `prev` at node `left - 1` using a `dummy` node.
 *     2. Let `curr = prev.next` (this node will end up at the tail of the reversed section).
 *     3. Repeatedly take `curr.next` and move it to `prev.next` (head of the reversed section):
 *        `ListNode next = curr.next;`
 *        `curr.next = next.next;`
 *        `next.next = prev.next;`
 *        `prev.next = next;`
 *     Repeat this `(right - left)` times.
 *
 * COMPLEXITY:
 *   - Time:  O(n) - Single pass over the list.
 *   - Space: O(1) - Constant pointer manipulation.
 *
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 */
public class P57_ReverseLinkedListII {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    public static ListNode reverseBetween(ListNode head, int left, int right) {
        if (head == null || left == right) return head;

        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;

        // Reach node right before 'left'
        for (int i = 1; i < left; i++) {
            prev = prev.next;
        }

        ListNode curr = prev.next;

        // Move curr.next to the front of reversed sublist
        for (int i = 0; i < right - left; i++) {
            ListNode next = curr.next;
            curr.next = next.next;
            next.next = prev.next;
            prev.next = next;
        }

        return dummy.next;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);

        ListNode res = reverseBetween(head, 2, 4);
        System.out.print("P57 Output: ");
        while (res != null) {
            System.out.print(res.val + (res.next != null ? " -> " : ""));
            res = res.next;
        }
        System.out.println(); // Expected: 1 -> 4 -> 3 -> 2 -> 5
    }
}