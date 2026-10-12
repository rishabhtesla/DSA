package DSA.LinkedList;

/**
 * ============================================================================
 * [62 / 65] - PARTITION LIST (LeetCode 86)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Given the `head` of a linked list and a value `x`, partition it such that all nodes
 * less than `x` come before nodes greater than or equal to `x`, while preserving
 * the original relative order of the nodes in each of the two partitions.
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Naive / Array Partitioning Approach:
 *    - Collect all node values into an array or two separate lists, partition them by sorting or
 *      bucket separation, and reconstruct a new linked list.
 *    - Valid, but requires extra space allocation.
 *
 * 2. Optimal Two-Chain Dummy Splice Approach (O(n) Time | O(1) Space):
 *    - **The Core Insight:** Instead of rearranging nodes in-place within a single list (which is prone to pointer corruption),
 *      split the input list into **two separate chains**: one for elements `< x` and one for elements `>= x`.
 *    - **Step 1:** Use two dummy nodes, `lessDummy` and `greaterDummy`, acting as anchors for the two chains.
 *    - **Step 2:** Iterate through the original list. If `head.val < x`, append the node to the `less` chain;
 *      otherwise, append it to the `greater` chain.
 *    - **Step 3:** Terminate the `greater` chain (`greater.next = null`) to **prevent cyclic pointer bugs**,
 *      then connect the tail of the `less` chain to the head of the `greater` chain (`less.next = greaterDummy.next`).
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(n) - Single linear pass through the input linked list.
 * - Space: O(1) - Constant auxiliary memory by re-linking existing nodes in-place.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE:
 * - List: `1 -> 4 -> 3 -> 2 -> 5 -> 2`, `x = 3`
 * - Result: `1 -> 2 -> 2 -> 4 -> 3 -> 5`
 *
 * VISUAL DRY RUN (for `x = 3`):
 *   - Appends nodes `< 3` to `less` chain: `1 -> 2 -> 2`.
 *   - Appends nodes `>= 3` to `greater` chain: `4 -> 3 -> 5`.
 *   - Sets `greater.next = null` to terminate.
 *   - Links `less` tail to `greater` head: `1 -> 2 -> 2 -> 4 -> 3 -> 5`. Returns `lessDummy.next`.
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why is setting `greater.next = null` mandatory at the end of the algorithm?
 *    (Because the original last node of the greater chain might still point to a node from the less chain
 *    or a later node, which would introduce a cycle and cause an infinite loop during printing or traversal.)
 * 2. Does this approach preserve the original relative order of elements?
 *    (Yes! By appending nodes sequentially as they appear in the original list into the respective chains,
 *    the stable relative order within both partitions is strictly maintained.)
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

        // Prevent infinite cycles by terminating the greater chain
        greater.next = null;
        // Splice the two chains together
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