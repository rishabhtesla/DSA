package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P71: PALINDROME LINKED LIST
 *
 * Question
 * Determine whether a linked-list sequence is the same forwards and backwards.
 *
 * Example
 * 1->1 -> true.
 *
 * Constraints
 * 1 <= nodes <= 10^5; node values fit 32-bit integers.
 *
 * Approach used by edSlash
 * 1. Copy values to an array and compare symmetric positions.
 * 2. Use pointers to represent the already-processed part and the nodes that still need to be visited.
 * 3. Save the next node before changing links, then advance the pointers while preserving the list structure.
 *
 * Dry run
 * Example from main: 1->1 -> true.
 * 1. Initialize the pointers described in the approach.
 * 2. Save the next node before changing a link or moving a pointer.
 * 3. Update the links and advance until the required node or the end is reached.
 * 4. Return the resulting head or answer.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P71_PalindromeLinkedList {
    public static boolean isPalindrome(ListNode head) {
        ArrayList<Integer> a = new ArrayList<>();
        for (; head != null; head = head.next) a.add(head.val);
        for (int i = 0, j = a.size() - 1; i < j; i++, j--) if (!a.get(i).equals(a.get(j))) return false;
        return true;
    }

    public static void main(String[] args) {
        System.out.println(isPalindrome(new ListNode(1, new ListNode(1))));
    }
}
