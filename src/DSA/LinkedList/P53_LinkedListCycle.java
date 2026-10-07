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