package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Design a stack supporting push, pop, top, and minimum in constant time.
 * EXAMPLE: push(2), push(1), getMin() -> 1.
 * CONSTRAINTS: Operations <= 3*10^4; values fit 32-bit integers.
 * LOGIC / ALGORITHM:
 * 1. Keep a parallel stack whose top is the minimum for every depth.
 * 2. When the current item resolves smaller pending candidates, pop them; push the current item as unresolved.
 * 3. Assign the default answer to candidates that never find a partner, then return the result.
 * INVARIANT: The stack contains exactly unresolved candidates in monotonic order; every popped item has just found its required neighbor.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input push(2), push(1), getMin() -> 1.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
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
