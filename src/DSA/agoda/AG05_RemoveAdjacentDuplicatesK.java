package DSA.agoda;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * ============================================================================
 * [AG-05 / 06] - REMOVE ADJACENT DUPLICATES II (Agoda Core Stack / LC 1209)
 * ============================================================================
 *
 * PROBLEM:
 *   You are given a string `s` consisting of lowercase English letters and an
 *   integer `k` (`k >= 2`). A "k-duplicate removal" consists of choosing `k`
 *   adjacent and equal letters from `s` and removing them, causing the left and
 *   right sides of the deleted substring to concatenate together.
 *
 *   We repeatedly perform `k`-duplicate removals on `s` until no more can be made.
 *   Return the final string after all such duplicate removals have been completed.
 *   (It is guaranteed that the answer is unique.)
 *
 * CONSTRAINTS:
 *   - 1 <= s.length() <= 10^5
 *   - 2 <= k <= 10^4
 *   - `s` contains only lowercase English letters ('a' - 'z').
 *
 * EXAMPLES:
 *   Example 1:
 *     Input:  s = "abcd", k = 2
 *     Output: "abcd"
 *     Explanation: Nothing to delete; no two adjacent characters are equal.
 *
 *   Example 2:
 *     Input:  s = "deeedbbcccbdaa", k = 3
 *     Output: "aa"
 *     Explanation:
 *       1. Delete "eee" and "ccc" -> "ddbbbdaa"
 *       2. Delete "bbb"           -> "dddaa"
 *       3. Delete "ddd"           -> "aa"
 *
 *   Example 3:
 *     Input:  s = "pbbcggttciiippooaais", k = 2
 *     Output: "ps"
 *     Explanation:
 *       "bb", "gg", "tt", "ii", "pp", "oo", "aa" cancel out in pairs, bringing
 *       "c" next to "i" -> "ci", then "ii" cancels -> "cc" cancels -> leaving "ps".
 *
 * ============================================================================
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 * ============================================================================
 *   - Naive Approach: Scan the string, delete `k` identical chars, build a new
 *     string, and repeat until no deletions occur -> `O(n^2 / k)` time.
 *   - Run-Length Encoding (RLE) Stack ("Compress & Explode"):
 *       * Instead of pushing individual characters onto the stack (where you'd
 *         have to look backwards `k` times to check counts), compress contiguous
 *         identical characters into a single `Block(char ch, int count)` node!
 *       * Because each `Block` remembers its exact streak length (`count`), when
 *         a middle block explodes (`count == k`) and gets popped, the outer blocks
 *         automatically touch each other at the top of the stack with their prior
 *         streak counts intact—ready to merge seamlessly on the next character!
 *
 * ============================================================================
 * SOLUTION LOGIC & STEP-BY-STEP ALGORITHM:
 * ============================================================================
 *   1. Define Helper Node (`Block`):
 *      - Holds `char ch` (the character) and `int count` (current contiguous streak,
 *        always strictly bounded by `1 <= count < k`).
 *
 *   2. Single-Pass Stream Reduction (Left-to-Right over `s`):
 *      - Maintain `Deque<Block> stack` representing the reduced prefix seen so far.
 *      - For each character `c` in `s`:
 *          * Case A: `!stack.isEmpty() && stack.peek().ch == c` (Matches top block)
 *              - Increment the streak in place: `stack.peek().count++`.
 *              - Explosion Check: If `stack.peek().count == k`, pop the entire
 *                `Block` (`stack.pop()`), instantly deleting all `k` characters!
 *          * Case B: Stack is empty OR `stack.peek().ch != c` (Different character)
 *              - Start a fresh streak by pushing `new Block(c, 1)`.
 *
 *   3. Chronological String Reconstruction (Bottom-to-Top):
 *      - `ArrayDeque` behaves as a LIFO stack when using `push()`/`peek()`/`pop()`
 *        (operating at the head/front).
 *      - Therefore, the oldest surviving characters sit at the TAIL (bottom) of
 *        the deque.
 *      - Call `stack.pollLast()` repeatedly to drain from bottom to top, appending
 *        `b.ch` `b.count` times into a `StringBuilder`.
 *
 * ============================================================================
 * VISUAL DRY RUN: s = "deeedbbcccbdaa", k = 3
 * ============================================================================
 *   Idx | Char | Top of Stack | Action Taken                        | Stack State (Bottom -> Top)
 *   ----+------+--------------+-------------------------------------+-----------------------------
 *    0  | 'd'  | empty        | Push (d:1)                          | [ (d:1) ]
 *    1  | 'e'  | (d:1) != 'e' | Push (e:1)                          | [ (d:1), (e:1) ]
 *    2  | 'e'  | (e:1) == 'e' | Increment -> (e:2)                  | [ (d:1), (e:2) ]
 *    3  | 'e'  | (e:2) == 'e' | Increment -> (e:3) == k -> POP!     | [ (d:1) ]
 *    4  | 'd'  | (d:1) == 'd' | Merged with earlier 'd'! -> (d:2)   | [ (d:2) ]
 *    5  | 'b'  | (d:2) != 'b' | Push (b:1)                          | [ (d:2), (b:1) ]
 *    6  | 'b'  | (b:1) == 'b' | Increment -> (b:2)                  | [ (d:2), (b:2) ]
 *    7  | 'c'  | (b:2) != 'c' | Push (c:1)                          | [ (d:2), (b:2), (c:1) ]
 *    8  | 'c'  | (c:1) == 'c' | Increment -> (c:2)                  | [ (d:2), (b:2), (c:2) ]
 *    9  | 'c'  | (c:2) == 'c' | Increment -> (c:3) == k -> POP!     | [ (d:2), (b:2) ]
 *   10  | 'b'  | (b:2) == 'b' | Merged with earlier 'b'! -> (b:3)->POP| [ (d:2) ]
 *   11  | 'd'  | (d:2) == 'd' | Merged with earlier 'd'! -> (d:3)->POP| [ ]  (empty!)
 *   12  | 'a'  | empty        | Push (a:1)                          | [ (a:1) ]
 *   13  | 'a'  | (a:1) == 'a' | Increment -> (a:2)                  | [ (a:2) ]
 *   END |  -   | Drain via pollLast(): expand (a:2) -> "aa"         | Result = "aa"
 *
 * ============================================================================
 * COMPLEXITY:
 *   - Time:  O(n) - Each character in `s` is inspected once, pushed/incremented
 *                   once, and appended to `StringBuilder` at most once.
 *   - Space: O(n) - In the worst case (no adjacent duplicates, e.g., "abcd"),
 *                   the stack stores `n` `Block` entries of count 1.
 *
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *      (Rescanning the entire string from index 0 after every `k`-length deletion -> O(n^2).)
 *   2. What invariant must remain true after every loop iteration?
 *      (No two adjacent blocks in the stack ever share the same `ch`, and every block
 *       satisfies `1 <= block.count < k`.)
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *      (Mutating `stack.peek().count` directly is safe because each `Block` instance is
 *       unique to its stack slot; reading with `pop()` instead of `pollLast()` at the end
 *       would reverse the string!)
 *   4. Which edge cases change the control flow (`k == 1` where every char deletes immediately,
 *      entire string collapsing to empty `""`, single-character string, no duplicates at all)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 * ============================================================================
 */
public class AG05_RemoveAdjacentDuplicatesK {

    // Run-Length Encoded node tracking a character and its current contiguous streak
    static class Block {
        char ch;
        int count;

        Block(char ch, int count) {
            this.ch = ch;
            this.count = count;
        }
    }

    public static String removeDuplicates(String s, int k) {
        if (s == null || s.isEmpty() || k <= 1) {
            return (k == 1) ? "" : (s == null ? "" : s);
        }

        // Stack stores compressed runs of characters; every block has 1 <= count < k
        Deque<Block> stack = new ArrayDeque<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            // CASE 1: Current char matches the active run at the top of the stack
            if (!stack.isEmpty() && stack.peek().ch == c) {
                stack.peek().count++;

                // Streak hit threshold k -> explode and remove the entire block
                if (stack.peek().count == k) {
                    stack.pop();
                }
            }
            // CASE 2: New character (or stack is empty) -> start a fresh run of length 1
            else {
                stack.push(new Block(c, 1));
            }
        }

        // Reconstruct final string from BOTTOM to TOP of the stack using pollLast()
        // (Because push/peek/pop operate at the HEAD of ArrayDeque, the oldest chars are at the TAIL)
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            Block block = stack.pollLast();
            for (int repeat = 0; repeat < block.count; repeat++) {
                sb.append(block.ch);
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("Test 1 (deeedbbcccbdaa, 3):       " + removeDuplicates("deeedbbcccbdaa", 3)       + " | Expected: \"aa\"");
        System.out.println("Test 2 (abcd, 2):                 " + removeDuplicates("abcd", 2)                 + " | Expected: \"abcd\"");
        System.out.println("Test 3 (pbbcggttciiippooaais, 2): " + removeDuplicates("pbbcggttciiippooaais", 2) + " | Expected: \"ps\"");
    }
}