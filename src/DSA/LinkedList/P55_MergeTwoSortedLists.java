package DSA.LinkedList;

/**
 * ============================================================================
 * [55 / 65] - MERGE TWO SORTED LISTS (LeetCode 21)
 * ============================================================================
 * 
 * PROBLEM:
 *   Merge two sorted linked lists list1 and list2 into one sorted list by
 *   splicing together the nodes of the first two lists. Return head of merged list.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Dummy Head Pointer:
 *     Eliminates special casing for initializing the head of the new merged list.
 *   - Compare `list1.val` and `list2.val`: attach the smaller node to `curr.next`
 *     and advance that list's pointer.
 *   - Splice Remainder: Once one list runs out, attach the non-null tail in O(1)
 *     (`curr.next = (list1 != null) ? list1 : list2`).
 *
 * COMPLEXITY:
 *   - Time:  O(m + n) - Linear traversal across both lists.
 *   - Space: O(1) - Splicing existing nodes in-place.
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