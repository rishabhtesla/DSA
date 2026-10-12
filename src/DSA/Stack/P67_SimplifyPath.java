package DSA.Stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * ============================================================================
 * [67 / 73] - SIMPLIFY PATH (LeetCode 71)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Given an absolute path for a Unix-style file system (starting with a slash `'/'`),
 * convert it to the simplified canonical path.
 * - A single period `'.'` represents the current directory (ignore).
 * - A double period `'..'` means moving up one directory level (pop from the stack).
 * - Multiple consecutive slashes `'//'` are treated as a single slash `'/'`.
 * - The canonical path must start with a single slash, and have no trailing slashes (unless it's just root `'/'`).
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Naive / String Mutation Approach:
 *    - Search and replace relative tokens like `"/./"` or `"/../"` in-place using string operations.
 *    - Inefficient: prone to edge cases, complicated index tracking, and high string reallocation overhead.
 *
 * 2. Optimal Stack Token Parsing Approach (O(n) Time | O(n) Space):
 *    - **The Core Insight:** File system directory hierarchies follow a nested, LIFO (Last-In, First-Out) structure.
 *      We can tokenize the path by splitting it across `'/'`.
 *    - **Step 1:** Split the path string using `path.split("/")`.
 *    - **Step 2:** Iterate through each token:
 *      - If token is empty `""` (from consecutive slashes) or `"."`, ignore it.
 *      - If token is `".."`, pop the previous valid directory from the stack if the stack is non-empty.
 *      - Any other valid directory name is pushed onto the stack.
 *    - **Step 3:** Reconstruct the canonical path by draining the stack from bottom to top using `pollLast()`.
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(n) - Linear pass to split the string tokens and process them through the stack.
 * - Space: O(n) - Stack storage and string builder allocations proportional to the number of path tokens.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE:
 * - Path: `"/home/user/Documents/../Pictures"`
 * - Expected Canonical Path: `"/home/user/Pictures"`
 *
 * VISUAL DRY RUN (for `"/home/"`):
 *   - Tokens extracted: `["", "home", ""]`.
 *   - Empty tokens are skipped; `"home"` is pushed onto the stack -> Stack: `["home"]`.
 *   - Reconstructed with a leading slash -> `"/home"`.
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. How does the stack handle an excessive number of parent directory jumps (e.g., `"/../"` from root)?
 *    (The guard condition `!stack.isEmpty()` prevents popping when the stack is already empty,
 *    correctly keeping the path anchored at the root directory `"/"`.)
 * 2. Why do we traverse the stack using `pollLast()` instead of standard pops?
 *    (Because a stack pushes newer directories onto the top, draining it from bottom to top ensures
 *    we reconstruct the path in correct chronological order from root downwards.)
 */
public class P67_SimplifyPath {

    public static String simplifyPath(String path) {
        Deque<String> stack = new ArrayDeque<>();
        String[] tokens = path.split("/");

        for (String token : tokens) {
            // Ignore current directory markers and empty segments from multiple consecutive slashes
            if (token.isEmpty() || token.equals(".")) {
                continue;
            }
            // Double period moves up a directory level (pop from stack if not already at root)
            if (token.equals("..")) {
                if (!stack.isEmpty()) {
                    stack.pop();
                }
            } else {
                // Valid directory name; push onto stack
                stack.push(token);
            }
        }

        // If stack is empty, we are at the root directory
        if (stack.isEmpty()) {
            return "/";
        }

        StringBuilder sb = new StringBuilder();
        // Traverse from the bottom of the stack to the top to preserve chronological path order
        while (!stack.isEmpty()) {
            sb.append("/").append(stack.pollLast());
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("P67 Output: " + simplifyPath("/home/"));               // Expected: "/home"
        System.out.println("P67 Output: " + simplifyPath("/home//foo/"));           // Expected: "/home/foo"
        System.out.println("P67 Output: " + simplifyPath("/home/user/Documents/../Pictures")); // Expected: "/home/user/Pictures"
        System.out.println("P67 Output: " + simplifyPath("/../"));                  // Expected: "/"
    }
}