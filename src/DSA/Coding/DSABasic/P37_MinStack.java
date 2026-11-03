package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P37: MIN STACK
 *
 * Question
 * Design a stack supporting push, pop, top, and minimum in constant time.
 *
 * Example
 * push(2), push(1), getMin() -> 1.
 *
 * Constraints
 * Operations <= 3*10^4; values fit 32-bit integers.
 *
 * Approach used by edSlash
 * 1. Keep a parallel stack whose top is the minimum for every depth.
 * 2. Process the input from left to right and keep unresolved values in the stack until a later value resolves them.
 * 3. Remove or preserve stack entries according to the ordering or balance rule required by the problem.
 *
 * Dry run
 *   MinStack state visual:
 *     push(2): values = [2], minimums = [2]
 *     push(1): values = [2,1], minimums = [2,1]
 *     getMin() reads the top of minimums -> 1
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P37_MinStack {
    private final Deque<Integer> s = new ArrayDeque<>(), mins = new ArrayDeque<>();

    public void push(int x) {
        s.push(x);
        mins.push(mins.isEmpty() ? x : Math.min(x, mins.peek()));
    }

    public void pop() {
        s.pop();
        mins.pop();
    }

    public int top() {
        return s.peek();
    }

    public int getMin() {
        return mins.peek();
    }

    public static void main(String[] args) {
        P37_MinStack x = new P37_MinStack();
        x.push(2);
        x.push(1);
        System.out.println(x.getMin());
    }
}
