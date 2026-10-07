package DSA.LinkedList;

/**
 * ============================================================================
 * [58 / 65] - REVERSE NODES IN K-GROUP (LeetCode 25)
 * ============================================================================
 * 
 * PROBLEM:
 *   Given the head of a linked list, reverse the nodes of the list k at a time,
 *   and return the modified list. If the number of nodes is not a multiple of k,
 *   leave the remaining nodes as-is.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Lookahead Check:
 *     Before reversing any group, look ahead k steps. If fewer than k nodes remain,
 *     leave them unchanged and terminate.
 *   - Reversal Splicing:
 *     Reverse the k nodes. Splice the head and tail back to the preceding and
 *     succeeding list portions, then advance pointers to the next group.
 *
 * COMPLEXITY:
 *   - Time:  O(n) - Every node is checked once and reversed once.
 *   - Space: O(1) - Iterative reversal.
 *
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 */
public class P58_ReverseNodesInKGroup {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    public static ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || k == 1) return head;

        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode groupPrev = dummy;

        while (true) {
            ListNode kth = getKth(groupPrev, k);
            if (kth == null) break;

            ListNode groupNext = kth.next;
            ListNode prev = groupNext;
            ListNode curr = groupPrev.next;

            // Reverse k elements
            while (curr != groupNext) {
                ListNode tmp = curr.next;
                curr.next = prev;
                prev = curr;
                curr = tmp;
            }

            ListNode tmp = groupPrev.next;
            groupPrev.next = kth;
            groupPrev = tmp;
        }

        return dummy.next;
    }

    private static ListNode getKth(ListNode curr, int k) {
        while (curr != null && k > 0) {
            curr = curr.next;
            k--;
        }
        return curr;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);

        ListNode res = reverseKGroup(head, 2);
        System.out.print("P58 Output: ");
        while (res != null) {
            System.out.print(res.val + (res.next != null ? " -> " : ""));
            res = res.next;
        }
        System.out.println(); // Expected: 2 -> 1 -> 4 -> 3 -> 5
    }
}