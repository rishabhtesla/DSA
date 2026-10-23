package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Implement FIFO queue operations using two stacks.
 * EXAMPLE: push(1), push(2), peek() -> 1.
 * CONSTRAINTS: At most 100 operations; standard stack operations only.
 * LOGIC / ALGORITHM:
 * 1. Push into input; lazily transfer to output when output is empty, reversing twice into FIFO order.
 * 2. When the current item resolves smaller pending candidates, pop them; push the current item as unresolved.
 * 3. Assign the default answer to candidates that never find a partner, then return the result.
 * INVARIANT: The stack contains exactly unresolved candidates in monotonic order; every popped item has just found its required neighbor.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input push(1), push(2), peek() -> 1.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P39_ImplementQueueUsingStacks {
    private final Deque<Integer> in = new ArrayDeque<>(), out = new ArrayDeque<>();

    public void push(int x) {
        in.push(x);
    }

    private void move() {
        if (out.isEmpty()) while (!in.isEmpty()) out.push(in.pop());
    }

    public int pop() {
        move();
        return out.pop();
    }

    public int peek() {
        move();
        return out.peek();
    }

    public boolean empty() {
        return in.isEmpty() && out.isEmpty();
    }

    public static void main(String[] args) {
        P39_ImplementQueueUsingStacks x = new P39_ImplementQueueUsingStacks();
        x.push(1);
        x.push(2);
        System.out.println(x.peek());
    }
}
