package DSA.agoda;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * ============================================================================
 * [AG-05 / 06] - REMOVE ADJACENT DUPLICATES II (Agoda Core Stack / LC 1209)
 * ============================================================================
 * 
 * PROBLEM:
 *   You are given a string s and an integer k. Repeatedly remove k adjacent and equal
 *   characters until no more can be removed. Return the final clean string.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - A simple stack storing only characters requires recursive backtracking or 
 *     re-scanning whenever a deletion collapses adjacent characters.
 *   - Character + Frequency Pair Stack:
 *     Instead of just storing the character, store an object `Block(char ch, int count)`.
 *     - If the current character matches `stack.peek().ch`:
 *       Increment `stack.peek().count`.
 *       If `count == k`, pop the entire block!
 *     - Else:
 *       Push a brand new `Block(ch, 1)`.
 *   - Reconstruction: Read the stack from bottom to top using `pollLast()`.
 *
 * COMPLEXITY:
 *   - Time:  O(n) - Every character is processed once.
 *   - Space: O(n) - Stack stores distinct run-length segments.
 */
public class AG05_RemoveAdjacentDuplicatesK {

    static class Block {
        char ch;
        int count;
        Block(char ch, int count) {
            this.ch = ch;
            this.count = count;
        }
    }

    public static String removeDuplicates(String s, int k) {
        Deque<Block> stack = new ArrayDeque<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (!stack.isEmpty() && stack.peek().ch == c) {
                stack.peek().count++;
                if (stack.peek().count == k) {
                    stack.pop(); // Pop block of k duplicate characters
                }
            } else {
                stack.push(new Block(c, 1));
            }
        }

        StringBuilder sb = new StringBuilder();
        // Traverse from bottom of stack to preserve chronological original order
        while (!stack.isEmpty()) {
            Block b = stack.pollLast();
            for (int i = 0; i < b.count; i++) {
                sb.append(b.ch);
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("AG05 Output (deeedbbcccbdaa, 3): " + removeDuplicates("deeedbbcccbdaa", 3)); // Expected: "aa"
        System.out.println("AG05 Output (abcd, 2):           " + removeDuplicates("abcd", 2));           // Expected: "abcd"
        System.out.println("AG05 Output (pbbcggttciiippooaest, 2): " + removeDuplicates("pbbcggttciiippooaest", 2)); // Expected: "ps"
    }
}