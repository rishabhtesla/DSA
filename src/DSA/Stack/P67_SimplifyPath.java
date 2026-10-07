package DSA.Stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * ============================================================================
 * [67 / 73] - SIMPLIFY PATH (LeetCode 71)
 * ============================================================================
 * 
 * PROBLEM:
 *   Given an absolute path for a Unix-style file system, convert it to the
 *   simplified canonical path.
 *   - Single period '.' represents current directory (ignore).
 *   - Double period '..' moves up one level (pop from stack).
 *   - Multiple consecutive slashes '//' are treated as single '/'.
 *   - Must start with '/', and have no trailing slash (unless root).
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Split by '/': Splitting leaves tokens that are either:
 *     - Empty string "" (from consecutive slashes) -> ignore.
 *     - "." -> ignore.
 *     - ".." -> pop previous valid directory if stack is non-empty.
 *     - Any other valid directory name -> push onto stack.
 *   - Join tokens using `pollLast()` / iteration from bottom to top of stack.
 *
 * COMPLEXITY:
 *   - Time:  O(n) - Splitting and processing path tokens.
 *   - Space: O(n) - Stack and string tokens.
 */
public class P67_SimplifyPath {

    public static String simplifyPath(String path) {
        Deque<String> stack = new ArrayDeque<>();
        String[] tokens = path.split("/");

        for (String token : tokens) {
            if (token.isEmpty() || token.equals(".")) {
                continue;
            }
            if (token.equals("..")) {
                if (!stack.isEmpty()) {
                    stack.pop();
                }
            } else {
                stack.push(token);
            }
        }

        if (stack.isEmpty()) {
            return "/";
        }

        StringBuilder sb = new StringBuilder();
        // Traverse from bottom of stack to top to preserve chronological order
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