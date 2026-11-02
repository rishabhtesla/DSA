package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P38: IMPLEMENT STACK USING QUEUES
 *
 * Question
 * Implement LIFO stack operations using queues.
 *
 * Example
 * push(1), push(2), pop() -> 2.
 *
 * Constraints
 * At most 100 operations; standard queue operations only.
 *
 * Approach used by edSlash
 * 1. After enqueue, rotate existing elements behind the new element so the front is the stack top.
 * 2. Process the input from left to right and keep unresolved values in the stack until a later value resolves them.
 * 3. Remove or preserve stack entries according to the ordering or balance rule required by the problem.
 *
 * Dry run
 * Example from main: push(1), push(2), pop() -> 2.
 * 1. Read each character or value from left to right.
 * 2. Push unresolved values or state onto the stack.
 * 3. When the current value resolves the top entry, pop and update the answer.
 * 4. Process any remaining stack entries before returning.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P38_ImplementStackUsingQueues {
    private final Queue<Integer> q = new ArrayDeque<>();

    public void push(int x) {
        q.add(x);
        for (int i = 1; i < q.size(); i++) q.add(q.remove());
    }

    public int pop() {
        return q.remove();
    }

    public int top() {
        return q.element();
    }

    public boolean empty() {
        return q.isEmpty();
    }

    public static void main(String[] args) {
        P38_ImplementStackUsingQueues x = new P38_ImplementStackUsingQueues();
        x.push(1);
        x.push(2);
        System.out.println(x.pop());
    }
}
