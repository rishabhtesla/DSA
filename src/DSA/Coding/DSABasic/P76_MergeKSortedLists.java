package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P76: MERGE K SORTED LISTS
 *
 * Question
 * Merge k sorted linked lists into one sorted list.
 *
 * Example
 * [1] and [2] -> 1->2.
 *
 * Constraints
 * 0 <= k <= 10^4; total nodes <= 10^4; each list sorted.
 *
 * Approach used by edSlash
 * 1. Put each head in a min-heap and repeatedly extract the smallest, adding its successor.
 * 2. Use pointers to represent the already-processed part and the nodes that still need to be visited.
 * 3. Save the next node before changing links, then advance the pointers while preserving the list structure.
 *
 * Dry run
 * Example from main: [1] and [2] -> 1->2.
 * 1. Initialize the pointers described in the approach.
 * 2. Save the next node before changing a link or moving a pointer.
 * 3. Update the links and advance until the required node or the end is reached.
 * 4. Return the resulting head or answer.
 *
 * Complexity
 * O(n log n) time and O(n) auxiliary space.
 */
public class P76_MergeKSortedLists {
    public static ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<ListNode> q = new PriorityQueue<>((x, y) -> x.val - y.val);
        for (ListNode n : lists) if (n != null) q.add(n);
        ListNode d = new ListNode(), p = d;
        while (!q.isEmpty()) {
            ListNode n = q.poll();
            p.next = n;
            p = n;
            if (n.next != null) q.add(n.next);
        }
        return d.next;
    }

    public static void main(String[] args) {
        System.out.println(mergeKLists(new ListNode[]{new ListNode(1), new ListNode(2)}).val);
    }
}
