package DSA.LinkedList;

/**
 * ============================================================================
 * [62 / 65] - PARTITION LIST (LeetCode 86)
 * ============================================================================
 * 
 * PROBLEM:
 *   Given head and a value x, partition it such that all nodes less than x come
 *   before nodes greater than or equal to x while preserving relative order.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Two Separate Chains:
 *     Use two dummy nodes: `lessDummy` and `greaterDummy`.
 *     Traverse input list:
 *     - If `curr.val < x`: link to `less` chain.
 *     - If `curr.val >= x`: link to `greater` chain.
 *     Splice the two chains: `less.next = greaterDummy.next`, and ensure `greater.next = null`.
 *
 * COMPLEXITY:
 *   - Time:  O(n) - Single pass through list.
 *   - Space: O(1) - Re-linking existing nodes.
 */
public class P62_PartitionList {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    public static ListNode partition(ListNode head, int x) {
        ListNode lessDummy = new ListNode(0);
        ListNode greaterDummy = new ListNode(0);
        ListNode less = lessDummy;
        ListNode greater = greaterDummy;

        while (head != null) {
            if (head.val < x) {
                less.next = head;
                less = less.next;
            } else {
                greater.next = head;
                greater = greater.next;
            }
            head = head.next;
        }

        greater.next = null; // Important: prevent cycles
        less.next = greaterDummy.next;

        return lessDummy.next;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(4);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(2);
        head.next.next.next.next = new ListNode(5);
        head.next.next.next.next.next = new ListNode(2);

        ListNode res = partition(head, 3);
        System.out.print("P62 Output: ");
        while (res != null) {
            System.out.print(res.val + (res.next != null ? " -> " : ""));
            res = res.next;
        }
        System.out.println(); // Expected: 1 -> 2 -> 2 -> 4 -> 3 -> 5
    }
}