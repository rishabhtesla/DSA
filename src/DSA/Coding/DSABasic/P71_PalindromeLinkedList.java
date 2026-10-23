package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Determine whether a linked-list sequence is the same forwards and backwards.
 * EXAMPLE: 1->1 -> true.
 * CONSTRAINTS: 1 <= nodes <= 10^5; node values fit 32-bit integers.
 * LOGIC / ALGORITHM:
 * 1. Copy values to an array and compare symmetric positions.
 * 2. Advance or relink pointers while preserving the already-correct portion and required relative order.
 * 3. Return the resulting head or value.
 * INVARIANT: The processed list portion is correctly linked; pointer gaps or stack entries represent exactly the nodes still to process.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input 1->1 -> true.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P71_PalindromeLinkedList {
    public static boolean isPalindrome(ListNode h) {
        ArrayList<Integer> a = new ArrayList<>();
        for (; h != null; h = h.next) a.add(h.val);
        for (int i = 0, j = a.size() - 1; i < j; i++, j--) if (!a.get(i).equals(a.get(j))) return false;
        return true;
    }

    public static void main(String[] args) {
        System.out.println(isPalindrome(new ListNode(1, new ListNode(1))));
    }
}
