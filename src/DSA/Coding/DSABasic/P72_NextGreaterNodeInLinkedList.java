package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P72: NEXT GREATER NODE IN LINKED LIST
 *
 * Question
 * For each list node, find the next later node with a greater value.
 *
 * Example
 * 2->1->5 -> [5,5,0].
 *
 * Constraints
 * 1 <= nodes <= 10^4; values are 1..10^9.
 *
 * Approach used by edSlash
 * 1. Convert to indexed values and use a decreasing index stack to resolve smaller predecessors.
 * 2. Process the input from left to right and keep unresolved values in the stack until a later value resolves them.
 * 3. Remove or preserve stack entries according to the ordering or balance rule required by the problem.
 *
 * Dry run
 * Example from main: 2->1->5 -> [5,5,0].
 * 1. Read each character or value from left to right.
 * 2. Push unresolved values or state onto the stack.
 * 3. When the current value resolves the top entry, pop and update the answer.
 * 4. Process any remaining stack entries before returning.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P72_NextGreaterNodeInLinkedList {
    public static int[] nextLargerNodes(ListNode head) {
        ArrayList<Integer> a = new ArrayList<>();
        for (; head != null; head = head.next) a.add(head.val);
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
