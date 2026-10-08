package DSA.LinkedList;

/**
 * ============================================================================
 * [53 / 65] - LINKED LIST CYCLE (LeetCode 141)
 * ============================================================================
 * 
 * PROBLEM:
 *   Given head, the head of a linked list, determine if the linked list has a cycle in it.
 *   Return true if there is a cycle, or false otherwise.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Floyd's Tortoise and Hare (Fast & Slow Pointers):
 *     Advance `slow` by 1 step and `fast` by 2 steps.
 *     If there is no cycle, `fast` or `fast.next` will reach null.
 *     If there is a cycle, the distance between fast and slow decreases by 1 node
 *     per iteration inside the cycle, guaranteeing they will meet without looping infinitely.
 *
 * COMPLEXITY:
 *   - Time:  O(n) - Linear traversal.
 *   - Space: O(1) - Two reference pointers.
 *
 *
 * EXAMPLE:
 *   The first main list is 3->2->0->-4 with -4.next pointing back to node 2; expected true.
 *
 * VISUAL DRY RUN:
 *   Floyd starts slow=3, fast=3; after one step slow=2, fast=0; next slow=0, fast=2;
 *   next slow=-4, fast=-4, so pointers meet inside the cycle and return true.
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 */
public class P53_LinkedListCycle {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    public static boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) return false;

        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(3);
        ListNode node2 = new ListNode(2);
        ListNode node0 = new ListNode(0);
        ListNode nodeNeg4 = new ListNode(-4);

        head.next = node2;
        node2.next = node0;
        node0.next = nodeNeg4;
        nodeNeg4.next = node2; // Creates cycle back to node2

        System.out.println("P53 Output: " + hasCycle(head)); // Expected: true
    }
}