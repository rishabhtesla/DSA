package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Determine whether a linked list contains a cycle.
 * EXAMPLE: 1->2->1 cycle -> true.
 * CONSTRAINTS: 0 <= nodes <= 10^4; next may point to any node or null.
 * LOGIC / ALGORITHM:
 * 1. Floyd slow/fast pointers meet exactly when a cycle exists.
 * 2. Advance or relink pointers while preserving the already-correct portion and required relative order.
 * 3. Return the resulting head or value.
 * INVARIANT: The processed list portion is correctly linked; pointer gaps or stack entries represent exactly the nodes still to process.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input 1->2->1 cycle -> true.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P66_LinkedListCycle {
    public static boolean hasCycle(ListNode h) {
        ListNode s = h, f = h;
        while (f != null && f.next != null) {
            s = s.next;
            f = f.next.next;
            if (s == f) return true;
        }
        return false;
    }

    public static void main(String[] args) {
        ListNode x = new ListNode(1);
        x.next = x;
        System.out.println(hasCycle(x));
    }
}
