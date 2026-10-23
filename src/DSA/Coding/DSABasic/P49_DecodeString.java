package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Decode nested repetition syntax k[encoded].
 * EXAMPLE: "3[a2[c]]" -> "accaccacc".
 * CONSTRAINTS: 1 <= s.length <= 30^4; digits, letters, brackets; encoded output <= 10^5.
 * LOGIC / ALGORITHM:
 * 1. Use stacks for repeat counts and previous strings when entering brackets.
 * 2. Scan once, updating frequencies or a stack while preserving the required character order or balance.
 * 3. Return the constructed string or final validity test.
 * INVARIANT: The processed character prefix remains valid, with counts and ordering sufficient to complete the answer.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input "3[a2[c]]" -> "accaccacc".; follow the numbered steps while maintaining the invariant, producing the value printed by main.
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
