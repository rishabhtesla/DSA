package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Merge k sorted linked lists into one sorted list.
 * EXAMPLE: [1] and [2] -> 1->2.
 * CONSTRAINTS: 0 <= k <= 10^4; total nodes <= 10^4; each list sorted.
 * LOGIC / ALGORITHM:
 * 1. Put each head in a min-heap and repeatedly extract the smallest, adding its successor.
 * 2. Advance or relink pointers while preserving the already-correct portion and required relative order.
 * 3. Return the resulting head or value.
 * INVARIANT: The processed list portion is correctly linked; pointer gaps or stack entries represent exactly the nodes still to process.
 * <p>
 * COMPLEXITY: O(n log n) time and O(n) auxiliary space.
 * DRY RUN: Main input [1] and [2] -> 1->2.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P76_MergeKSortedLists {
    public static ListNode mergeKLists(ListNode[] a) {
        PriorityQueue<ListNode> q = new PriorityQueue<>((x, y) -> x.val - y.val);
        for (ListNode n : a) if (n != null) q.add(n);
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
