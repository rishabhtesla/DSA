package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Implement LIFO stack operations using queues.
 * EXAMPLE: push(1), push(2), pop() -> 2.
 * CONSTRAINTS: At most 100 operations; standard queue operations only.
 * LOGIC / ALGORITHM:
 * 1. After enqueue, rotate existing elements behind the new element so the front is the stack top.
 * 2. When the current item resolves smaller pending candidates, pop them; push the current item as unresolved.
 * 3. Assign the default answer to candidates that never find a partner, then return the result.
 * INVARIANT: The stack contains exactly unresolved candidates in monotonic order; every popped item has just found its required neighbor.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input push(1), push(2), pop() -> 2.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
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
