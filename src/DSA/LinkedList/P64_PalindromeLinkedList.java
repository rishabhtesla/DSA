package DSA.LinkedList;

/**
 * ============================================================================
 * [64 / 65] - PALINDROME LINKED LIST (LeetCode 234)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Given the `head` of a singly linked list, return `true` if it is a palindrome
 * or `false` otherwise.
 * **Constraint:** Solve in `O(n)` time and `O(1)` extra space.
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Naive / Array or Stack Approach:
 *    - Copy all node values into an ArrayList or push them onto a Stack, then check for symmetry.
 *    - Valid, but requires `O(n)` auxiliary space.
 *
 * 2. Optimal Fast & Slow Pointer + Half-List Reversal Approach (O(n) Time | O(1) Space):
 *    - **The Core Insight:** We can check symmetry in-place without extra storage by splitting the list
 *      in half, reversing the second half, and comparing corresponding elements.
 *    - **Step 1:** Find the midpoint of the linked list using `slow` (advances by 1 step) and `fast` (advances by 2 steps).
 *    - **Step 2:** Reverse the second half of the list starting from `slow`.
 *    - **Step 3:** Initialize two pointers (`p1` at `head` and `p2` at `secondHalfHead`) to compare values pairwise.
 *    - **Step 4 (Best Practice):** Restore the list structure by reversing the second half back before returning the final result.
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(n) - Linear traversal to find the middle, reverse the second half, compare elements, and restore the list.
 * - Space: O(1) - In-place pointer modifications require constant auxiliary space.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE:
 * - List: `1 -> 2 -> 2 -> 1`
 * - Result: `true`
 *
 * VISUAL DRY RUN (for `1 -> 2 -> 2 -> 1`):
 *   - Fast and slow pointers find the midpoint.
 *   - Reverses the second half (`2 -> 1` becomes `1 -> 2`).
 *   - Compares first half (`1, 2`) with reversed second half (`1, 2`). Both pairs match (`1 == 1`, `2 == 2`).
 *   - Restores the list structure and returns `true`.
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why is restoring the list structure considered a professional best practice?
 *    (If the data structure is shared or expected to remain unmodified by client code,
 *    leaving the list mutated/reversed can introduce subtle side-effects elsewhere in the application.)
 * 2. How does the midpoint detection handle both odd and even length lists?
 *    (The fast/slow pointer setup naturally anchors `slow` at the start of the second half for both cases,
 *    making the reversal and comparison logic uniform.)
 */
public class P64_PalindromeLinkedList {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    public static boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) return true;

        // Step 1: Find the middle node using slow and fast pointers
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // Step 2: Reverse the second half of the list
        ListNode secondHalfHead = reverse(slow);
        ListNode p1 = head;
        ListNode p2 = secondHalfHead;

        // Step 3: Compare both halves for symmetry
        boolean isPal = true;
        while (p2 != null) {
            if (p1.val != p2.val) {
                isPal = false;
                break;
            }
            p1 = p1.next;
            p2 = p2.next;
        }

        // Step 4: Restore the list structure (cleanup)
        reverse(secondHalfHead);

        return isPal;
    }

    private static ListNode reverse(ListNode head) {
        ListNode prev = null;
        while (head != null) {
            ListNode next = head.next;
            head.next = prev;
            prev = head;
            head = next;
        }
        return prev;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(2);
        head.next.next.next = new ListNode(1);

        System.out.println("P64 Output: " + isPalindrome(head)); // Expected: true
    }
}