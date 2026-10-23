package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Return the node where a linked-list cycle begins.
 * EXAMPLE: 0->1->2->1 -> node 1.
 * CONSTRAINTS: 0 <= nodes <= 10^4; cycle may be absent.
 * LOGIC / ALGORITHM:
 * 1. After Floyd pointers meet, reset one to head; equal remaining distances make their next meeting the entry.
 * 2. Advance or relink pointers while preserving the already-correct portion and required relative order.
 * 3. Return the resulting head or value.
 * INVARIANT: The processed list portion is correctly linked; pointer gaps or stack entries represent exactly the nodes still to process.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input 0->1->2->1 -> node 1.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P67_LinkedListCycleII {
    public static ListNode detectCycle(ListNode h) {
        ListNode s = h, f = h;
        while (f != null && f.next != null) {
            s = s.next;
            f = f.next.next;
            if (s == f) {
                s = h;
                while (s != f) {
                    s = s.next;
                    f = f.next;
                }
                return s;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(detectCycle(null));
    }
}
