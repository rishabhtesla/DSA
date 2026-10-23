package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Replace nodes from index a through b in list1 with list2.
 * EXAMPLE: [0,1,2], a=1,b=1, [9] -> [0,9,2].
 * CONSTRAINTS: 3 <= list1 length <= 10^4; 0 <= a <= b < length-1.
 * LOGIC / ALGORITHM:
 * 1. Find predecessor at a-1 and successor at b+1, splice list2 between them.
 * 2. Advance or relink pointers while preserving the already-correct portion and required relative order.
 * 3. Return the resulting head or value.
 * INVARIANT: The processed list portion is correctly linked; pointer gaps or stack entries represent exactly the nodes still to process.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input [0,1,2], a=1,b=1, [9] -> [0,9,2].; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P75_MergeInBetweenLinkedLists {
    public static ListNode mergeInBetween(ListNode a, int x, int y, ListNode b) {
        ListNode p = a;
        for (int i = 1; i < x; i++) p = p.next;
        ListNode q = p;
        for (int i = x; i <= y; i++) q = q.next;
        p.next = b;
        while (b.next != null) b = b.next;
        b.next = q.next;
        return a;
    }

    public static void main(String[] args) {
        System.out.println(mergeInBetween(new ListNode(0, new ListNode(1, new ListNode(2))), 1, 1, new ListNode(9)).next.val);
    }
}
