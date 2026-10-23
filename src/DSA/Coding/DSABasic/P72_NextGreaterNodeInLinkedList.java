package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: For each list node, find the next later node with a greater value.
 * EXAMPLE: 2->1->5 -> [5,5,0].
 * CONSTRAINTS: 1 <= nodes <= 10^4; values are 1..10^9.
 * LOGIC / ALGORITHM:
 * 1. Convert to indexed values and use a decreasing index stack to resolve smaller predecessors.
 * 2. Advance or relink pointers while preserving the already-correct portion and required relative order.
 * 3. Return the resulting head or value.
 * INVARIANT: The processed list portion is correctly linked; pointer gaps or stack entries represent exactly the nodes still to process.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input 2->1->5 -> [5,5,0].; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P72_NextGreaterNodeInLinkedList {
    public static int[] nextLargerNodes(ListNode h) {
        ArrayList<Integer> a = new ArrayList<>();
        for (; h != null; h = h.next) a.add(h.val);
        int[] r = new int[a.size()];
        Deque<Integer> s = new ArrayDeque<>();
        for (int i = 0; i < a.size(); i++) {
            while (!s.isEmpty() && a.get(s.peek()) < a.get(i)) r[s.pop()] = a.get(i);
            s.push(i);
        }
        return r;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(nextLargerNodes(new ListNode(2, new ListNode(1, new ListNode(5))))));
    }
}
