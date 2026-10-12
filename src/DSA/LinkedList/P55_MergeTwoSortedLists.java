package DSA.LinkedList;

/**
 * ============================================================================
 * [55 / 65] - MERGE TWO SORTED LISTS (LeetCode 21)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Merge two sorted linked lists `list1` and `list2` into one sorted list by
 * splicing together the nodes of the first two lists. Return the head of the merged list.
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Naive / Array Collection Approach:
 *    - Extract all values from both lists into an array or list, sort them, and rebuild a new linked list.
 *    - Inefficient: takes O((m+n) log(m+n)) time and O(m + n) extra space.
 *
 * 2. Optimal Dummy Head + Splicing Approach (O(m + n) Time | O(1) Space):
 *    - **The Core Insight:** We can merge the lists **in-place** by rewiring pointers without allocating new nodes.
 *    - **Step 1:** Use a `dummy` head pointer to eliminate special-casing and conditional logic when
 *      initializing the head of the new merged list.
 *    - **Step 2:** Compare `list1.val` and `list2.val`: attach the smaller node to `curr.next`,
 *      then advance that list's pointer and `curr`.
 *    - **Step 3:** Splice Remainder: Once one list runs out, attach the remaining non-null tail in O(1) time
 *      (`curr.next = (list1 != null) ? list1 : list2`).
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(m + n) where `m` and `n` are the lengths of `list1` and `list2`, performing a linear traversal.
 * - Space: O(1) - In-place pointer manipulation modifies existing nodes without extra heap allocation.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE:
 * - `list1 = 1 -> 2 -> 4`
 * - `list2 = 1 -> 3 -> 4`
 * - Merged Result = `1 -> 1 -> 2 -> 3 -> 4 -> 4`
 *
 * VISUAL DRY RUN:
 *   - Compare heads: `1 <= 1` -> attaches `list1`'s first node (1). `list1` advances.
 *   - Compare `list1` (2) and `list2` (1) -> `1 <= 2` is false -> attaches `list2`'s node (1). `list2` advances to 3.
 *   - Compare `list1` (2) and `list2` (3) -> attaches `list1`'s node (2). `list1` advances to 4.
 *   - Process continues until `list1` is exhausted; remaining tail of `list2` is spliced in instantly.
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why is a dummy head node valuable in linked list construction problems?
 *    (It provides a stable anchor point, removing the need for `if (head == null)` checks when attaching the very first node.)
 * 2. How does tail splicing optimize the end of the merge process?
 *    (Instead of stepping through the remaining list one node at a time, we attach the entire remaining sublist
 *    in a single pointer assignment statement (`curr.next = ...`), achieving O(1) tail attachment.)
 */
public class P55_MergeTwoSortedLists {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    public static ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;

        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                curr.next = list1;
                list1 = list1.next;
            } else {
                curr.next = list2;
                list2 = list2.next;
            }
            curr = curr.next;
        }

        // Splice the remaining tail in O(1) time
        curr.next = (list1 != null) ? list1 : list2;
        return dummy.next;
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode(1);
        l1.next = new ListNode(2);
        l1.next.next = new ListNode(4);

        ListNode l2 = new ListNode(1);
        l2.next = new ListNode(3);
        l2.next.next = new ListNode(4);

        ListNode res = mergeTwoLists(l1, l2);
        System.out.print("P55 Output: ");
        while (res != null) {
            System.out.print(res.val + (res.next != null ? " -> " : ""));
            res = res.next;
        }
        System.out.println(); // Expected: 1 -> 1 -> 2 -> 3 -> 4 -> 4
    }
}