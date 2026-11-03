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
 *   Stack-using-queue visual:
 *     push(1) -> queue [1]
 *     push(2) -> add 2, then rotate old values -> queue [2,1]
 *     pop() removes the front value -> 2
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
