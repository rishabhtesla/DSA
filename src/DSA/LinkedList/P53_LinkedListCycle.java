package DSA.LinkedList;

/**
 * ============================================================================
 * [53 / 65] - LINKED LIST CYCLE (LeetCode 141)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Given `head`, the head of a linked list, determine if the linked list has a cycle in it.
 * Return `true` if there is a cycle, or `false` otherwise.
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Naive / HashSet Approach:
 *    - Use a `HashSet<ListNode>` to keep track of visited nodes. If we encounter a node
 *      already present in the set, a cycle exists.
 *    - Valid, but requires O(n) extra space.
 *
 * 2. Optimal Floyd's Tortoise and Hare Approach (O(n) Time | O(1) Space):
 *    - **The Core Insight:** Use two pointers moving at different speeds: `slow` (advances by 1 step)
 *      and `fast` (advances by 2 steps).
 *    - If there is no cycle, `fast` or `fast.next` will naturally reach `null`.
 *    - If there is a cycle, the distance between `fast` and `slow` decreases by 1 node per iteration
 *      once inside the loop, mathematically guaranteeing that they will eventually meet without infinite looping.
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(n) - Linear traversal. In the worst case, `fast` traverses the cycle at most once before meeting `slow`.
 * - Space: O(1) - Uses only two reference pointers (`slow` and `fast`), requiring constant auxiliary space.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE:
 * - List: `3 -> 2 -> 0 -> -4`, where `-4` points back to `2` -> `true`
 *
 * VISUAL DRY RUN (for the cycle test case):
 *   - Initial: `slow = 3`, `fast = 3`
 *   - Iteration 1: `slow` moves to `2`, `fast` moves to `0`
 *   - Iteration 2: `slow` moves to `0`, `fast` moves to `2`
 *   - Iteration 3: `slow` moves to `-4`, `fast` moves to `-4`
 *   - Pointers meet (`slow == fast`), returns `true`.
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why does Floyd's algorithm guarantee a meeting point inside a cycle?
 *    (Because the fast pointer closes the gap by 1 node relative to the slow pointer during every single step inside the loop.)
 * 2. What edge cases are handled upfront?
 *    (A `null` head or a list with only a single node (`head.next == null`) immediately returns `false` since a cycle cannot form.)
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