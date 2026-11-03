package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P49: DECODE STRING
 *
 * Question
 * Decode nested repetition syntax k[encoded].
 *
 * Example
 * "3[a2[c]]" -> "accaccacc".
 *
 * Constraints
 * 1 <= s.length <= 30^4; digits, letters, brackets; encoded output <= 10^5.
 *
 * Approach used by edSlash
 * 1. Use stacks for repeat counts and previous strings when entering brackets.
 * 2. Process the input from left to right and keep unresolved values in the stack until a later value resolves them.
 * 3. Remove or preserve stack entries according to the ordering or balance rule required by the problem.
 *
 * Dry run
 *   Nested-stack visual for "3[a2[c]]":
 *     read 3 -> repeat count 3; read a -> current text "a"
 *     read 2 -> nested count; read c -> nested text "c"
 *     close inner bracket -> "acc"; close outer bracket -> "accaccacc"
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P49_DecodeString {
    public static String decodeString(String s) {
        Deque<Integer> ns = new ArrayDeque<>();
        Deque<String> ss = new ArrayDeque<>();
        String cur = "";
        int n = 0;
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) n = n * 10 + c - '0';
            else if (c == '[') {
                ns.push(n);
                ss.push(cur);
                n = 0;
                cur = "";
            } else if (c == ']') {
                String x = cur;
                cur = ss.pop();
                cur += x.repeat(ns.pop());
            } else cur += c;
        }
        return cur;
    }

    public static void main(String[] args) {
        System.out.println(decodeString("3[a2[c]]"));
    }
}
