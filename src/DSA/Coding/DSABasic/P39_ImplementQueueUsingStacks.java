package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P39: IMPLEMENT QUEUE USING STACKS
 *
 * Question
 * Implement FIFO queue operations using two stacks.
 *
 * Example
 * push(1), push(2), peek() -> 1.
 *
 * Constraints
 * At most 100 operations; standard stack operations only.
 *
 * Approach used by edSlash
 * 1. Push into input; lazily transfer to output when output is empty, reversing twice into FIFO order.
 * 2. Process the input from left to right and keep unresolved values in the stack until a later value resolves them.
 * 3. Remove or preserve stack entries according to the ordering or balance rule required by the problem.
 *
 * Dry run
 *   Queue-using-stacks visual:
 *     push(1), push(2) -> input stack [1,2]
 *     move values to output stack -> output top is 1
 *     peek() reads 1 without removing it
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
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
