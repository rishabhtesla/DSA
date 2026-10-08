package DSA.LinkedList;

/**
 * ============================================================================
 * [64 / 65] - PALINDROME LINKED LIST (LeetCode 234)
 * ============================================================================
 * 
 * PROBLEM:
 *   Given the head of a singly linked list, return true if it is a palindrome or false otherwise.
 *   Constraint: Solve in O(n) time and O(1) space.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Fast & Slow Pointer + Half-List Reversal:
 *     1. Find midpoint using `slow` (1 step) and `fast` (2 steps).
 *     2. Reverse the second half of the list starting from `slow`.
 *     3. Compare values of first half and reversed second half.
 *     4. (Optional best practice) Restore list structure before returning.
 *
 * COMPLEXITY:
 *   - Time:  O(n) - Finding mid + reversing half + comparing values.
 *   - Space: O(1) - In-place reversal.
 *
 *
 * EXAMPLE:
 *   The first main list is 1->2->2->1; expected result is true.
 *
 * VISUAL DRY RUN:
 *   Slow/fast find the midpoint; reverse the second half 2->1 to 1->2. Compare first
 *   half 1,2 with reversed half 1,2: both pairs match, so return true.
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 */
public class P64_PalindromeLinkedList {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    public static boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) return true;

        // Step 1: Find middle node
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // Step 2: Reverse second half
        ListNode secondHalfHead = reverse(slow);
        ListNode p1 = head;
        ListNode p2 = secondHalfHead;

        // Step 3: Compare both halves
        boolean isPal = true;
        while (p2 != null) {
            if (p1.val != p2.val) {
                isPal = false;
                break;
            }
            p1 = p1.next;
            p2 = p2.next;
        }

        // Step 4: Restore list (optional cleanup)
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