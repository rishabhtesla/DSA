package DSA.LinkedList;

/**
 * ============================================================================
 * [61 / 65] - ROTATE LIST (LeetCode 61)
 * ============================================================================
 * 
 * PROBLEM:
 *   Given the head of a linked list, rotate the list to the right by k places.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Close into a Ring:
 *     1. Count length `n` and find the tail node.
 *     2. Connect tail to head: `tail.next = head` (forms a circle).
 *     3. Effective rotation: `k = k % n`.
 *     4. The new tail is located at step `(n - k)` from the old head.
 *     5. Traverse to new tail, set `newHead = newTail.next`, and break loop (`newTail.next = null`).
 *
 * COMPLEXITY:
 *   - Time:  O(n) - Traverse to tail, then traverse to break point.
 *   - Space: O(1) - In-place rotation.
 *
 *
 * EXAMPLE:
 *   The first main list is 1->2->3->4->5 with k=2; expected 4->5->1->2->3.
 *
 * VISUAL DRY RUN:
 *   Length=5, so k%=5=2. Join tail to head, then walk newTail to node 3; break after it:
 *   newHead=4 and the cycle becomes 4->5->1->2->3. Return newHead.
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 */
public class P61_RotateList {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    public static ListNode rotateRight(ListNode head, int k) {
        if (head == null || head.next == null || k == 0) return head;

        // Step 1: Find length and tail node
        int n = 1;
        ListNode tail = head;
        while (tail.next != null) {
            tail = tail.next;
            n++;
        }

        // Step 2: Form a circular ring
        tail.next = head;

        // Step 3: Find new tail: (n - (k % n)) steps from original head
        k %= n;
        int stepsToNewTail = n - k;
        ListNode newTail = tail;
        while (stepsToNewTail > 0) {
            newTail = newTail.next;
            stepsToNewTail--;
        }

        // Step 4: Break the circle
        ListNode newHead = newTail.next;
        newTail.next = null;

        return newHead;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);

        ListNode res = rotateRight(head, 2);
        System.out.print("P61 Output: ");
        while (res != null) {
            System.out.print(res.val + (res.next != null ? " -> " : ""));
            res = res.next;
        }
        System.out.println(); // Expected: 4 -> 5 -> 1 -> 2 -> 3
    }
}