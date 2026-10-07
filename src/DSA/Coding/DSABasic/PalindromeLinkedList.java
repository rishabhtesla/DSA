package DSA.Coding.DSABasic;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * PROBLEM STATEMENT:
 * Given the head of a singly linked list, return true if it is a palindrome or false otherwise [00:00:17, 00:02:05].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: head = [1, 2, 2, 1] [00:00:37, 00:02:27]
 * - Forward traversal: 1 -> 2 -> 2 -> 1 [00:00:52]
 * - Reverse traversal: 1 -> 2 -> 2 -> 1 [00:01:07]
 * - Both sequences match identically -> Palindrome [00:01:17].
 * - Result: true
 * 
 * Example 2: head = [1, 2, 3] [00:01:31]
 * - Forward traversal: 1 -> 2 -> 3 [00:01:40]
 * - Reverse traversal: 3 -> 2 -> 1 [00:01:48]
 * - Mismatch (1 != 3) -> Not a palindrome [00:01:54].
 * - Result: false
 * 
 * Example 3: head = [1, 2, 3, 2, 1] (Simulated inside video explanation [00:11:19, 00:14:14])
 * - Push All to Stack: Stack = [1, 2, 3, 2, 1] (top is 1) [00:11:58]
 * - Re-traverse and pop:
 *   - Node 1 (val 1) == Stack.pop() (1) -> match! [00:12:42]
 *   - Node 2 (val 2) == Stack.pop() (2) -> match! [00:13:06]
 *   - Node 3 (val 3) == Stack.pop() (3) -> match! [00:13:28]
 *   - Node 4 (val 2) == Stack.pop() (2) -> match! [00:13:42]
 *   - Node 5 (val 1) == Stack.pop() (1) -> match! [00:13:58]
 * - All nodes verified successfully -> Return true [00:14:09].
 * - Result: true
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Stack LIFO Value Comparison):
 * • Pointer Initialization:
 *   - Traversal pointer `ptr = head` for populating the stack [00:07:23].
 *   - Second pointer `ptr2 = head` for reading and verifying list values [00:08:29].
 * • Condition Boundaries:
 *   - Population loop: `while (ptr != null)` [00:07:33].
 *   - Comparison loop: `while (ptr2 != null)` [00:08:33].
 *   - Mismatch break: `if (val1 != val2) return false` [00:09:08].
 * • Operational Steps:
 *   1. Instantiate integer stack `Stack<Integer> stack = new Stack<>()` [00:07:06].
 *   2. Traverse linked list using `ptr`, pushing `ptr.val` onto `stack` and advancing `ptr = ptr.next` [00:07:43, 00:07:59].
 *   3. Reset iteration using second pointer `ptr2 = head` [00:08:29].
 *   4. Traverse list again using `ptr2`: extract `val1 = ptr2.val` and `val2 = stack.pop()` [00:08:48, 00:08:57].
 *   5. If `val1 != val2`, return `false` immediately [00:09:08].
 *   6. Advance `ptr2 = ptr2.next` [00:09:19].
 *   7. If loop finishes without mismatch, return `true` [00:09:39].
 * • Time Complexity: O(n) - Two sequential passes over the n nodes of the linked list [00:10:14].
 * • Space Complexity: O(n) auxiliary space - Stack stores n integer node values [00:10:21].
 * • LOGIC BEHIND THIS APPROACH:
 *   Stack follows Last-In, First-Out (LIFO) order [00:04:00]. 
 *   Pushing values in natural order and popping them during a second forward traversal compares each 
 *   front node with its corresponding rear node (e.g., node 0 vs node n-1, node 1 vs node n-2) [00:04:24, 00:06:12].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 1: Stack LIFO - head = [1, 2, 3, 1]):
 * Step 1: Push nodes to Stack:
 * ptr at 1 -> stack = [1]
 * ptr at 2 -> stack = [1, 2]
 * ptr at 3 -> stack = [1, 2, 3]
 * ptr at 1 -> stack = [1, 2, 3, 1] (top is 1) [00:05:40]
 * 
 * Step 2: Compare nodes while popping:
 * ptr2 at Node 0 (val 1): pop stack -> 1. Match (1 == 1). ptr2 = ptr2.next [00:06:12].
 * ptr2 at Node 1 (val 2): pop stack -> 3. Mismatch (2 != 3)! [00:06:19].
 * Return false immediately [00:06:24].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 2: O(1) Space In-Place Reversal - head = [1, 2, 2, 1]):
 * Step 1: Find middle using Slow/Fast pointers: slow stops at second 2.
 * Step 2: Reverse second half starting at slow (2 -> 1 becomes 1 -> 2).
 * Step 3: Compare first half (1 -> 2) with reversed second half (1 -> 2):
 *   - 1 == 1, 2 == 2 -> Matches!
 * Step 4: Restore original list structure and return true.
 * Output = true.
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 3: ArrayList Two-Pointer Strategy - head = [1, 2, 1]):
 * Step 1: Copy values to ArrayList -> list = [1, 2, 1]
 * Step 2: Two-pointer check: left = 0 (1), right = 2 (1) -> 1 == 1.
 *         left++ (1), right-- (1) -> left == right (loop ends).
 * Output = true.
 */
public class PalindromeLinkedList {

    // APPROACH 1: Stack LIFO Value Comparison (Anchor Strategy)
    public static boolean isPalindromeOptimal(ListNode head) {
        if (head == null || head.next == null) return true;

        Stack<Integer> stack = new Stack<>(); // Stack to hold node values in LIFO order [00:07:06]

        // Pass 1: Push all linked list values onto the stack [00:05:29, 00:07:33]
        ListNode pointer = head;
        while (pointer != null) {
            stack.push(pointer.val); // Store node value [00:07:43]
            pointer = pointer.next;  // Advance to next node [00:07:59]
        }

        // Pass 2: Re-traverse list from head and compare with popped values [00:05:59, 00:08:33]
        ListNode pointer2 = head;
        while (pointer2 != null) {
            int val1 = pointer2.val;  // Current list node value [00:08:48]
            int val2 = stack.pop();    // Value from end of list (LIFO) [00:08:57]

            // Early exit on value mismatch [00:09:08]
            if (val1 != val2) {
                return false; // Not a palindrome [00:09:15]
            }

            pointer2 = pointer2.next; // Advance to next node [00:09:19]
        }

        return true; // All paired elements matched [00:09:39]
    }

    // APPROACH 2: Fast & Slow Pointer with In-Place Half-List Reversal (O(1) Auxiliary Space)
    // Achieves O(1) space complexity by reversing the second half of the linked list in-place, 
    // comparing both halves, and then restoring the original list structure.
    public static boolean isPalindromeInPlace(ListNode head) {
        if (head == null || head.next == null) return true;

        // Step 1: Find middle using fast and slow pointers
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // Step 2: Reverse second half of linked list
        ListNode secondHalfHead = reverseList(slow);
        ListNode firstHalfHead = head;

        // Step 3: Compare first half and reversed second half
        ListNode p1 = firstHalfHead;
        ListNode p2 = secondHalfHead;
        boolean result = true;

        while (p2 != null) {
            if (p1.val != p2.val) {
                result = false;
                break;
            }
            p1 = p1.next;
            p2 = p2.next;
        }

        // Step 4: Restore second half back to original order
        reverseList(secondHalfHead);

        return result;
    }

    private static ListNode reverseList(ListNode node) {
        ListNode prev = null;
        ListNode curr = node;
        while (curr != null) {
            ListNode nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }
        return prev;
    }

    // APPROACH 3: ArrayList Copy & Two-Pointer Verification Strategy
    // Copies linked list node values into an ArrayList buffer and uses standard array 
    // two-pointer comparison from both ends toward the center.
    public static boolean isPalindromeArrayList(ListNode head) {
        if (head == null || head.next == null) return true;

        List<Integer> list = new ArrayList<>();
        ListNode curr = head;

        while (curr != null) {
            list.add(curr.val);
            curr = curr.next;
        }

        int start = 0;
        int end = list.size() - 1;

        while (start < end) {
            if (!list.get(start).equals(list.get(end))) {
                return false;
            }
            start++;
            end--;
        }

        return true;
    }

    // Helper method to convert an array to a linked list for testing
    private static ListNode buildList(int[] values) {
        if (values == null || values.length == 0) return null;
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        for (int v : values) {
            curr.next = new ListNode(v);
            curr = curr.next;
        }
        return dummy.next;
    }

    // Comprehensive Verification Test Suite
    public static void main(String[] args) {
        System.out.println("================ RUNNING DSA TEST SUITE ================\n");

        // --- TEST CASE 1 (Valid Even Length Palindrome) ---
        ListNode test1_1 = buildList(new int[]{1, 2, 2, 1});
        ListNode test1_2 = buildList(new int[]{1, 2, 2, 1});
        ListNode test1_3 = buildList(new int[]{1, 2, 2, 1});

        boolean res1_1 = isPalindromeOptimal(test1_1);
        boolean res1_2 = isPalindromeInPlace(test1_2);
        boolean res1_3 = isPalindromeArrayList(test1_3);

        System.out.println("Test Case 1: 1 -> 2 -> 2 -> 1");
        System.out.println("Approach 1 (Stack LIFO)    Result: " + res1_1);
        System.out.println("Approach 2 (In-Place O(1)) Result: " + res1_2);
        System.out.println("Approach 3 (ArrayList)     Result: " + res1_3);
        System.out.println("Verification: " + (res1_1 && res1_2 && res1_3 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (Invalid List) ---
        ListNode test2_1 = buildList(new int[]{1, 2, 3});
        ListNode test2_2 = buildList(new int[]{1, 2, 3});
        ListNode test2_3 = buildList(new int[]{1, 2, 3});

        boolean res2_1 = isPalindromeOptimal(test2_1);
        boolean res2_2 = isPalindromeInPlace(test2_2);
        boolean res2_3 = isPalindromeArrayList(test2_3);

        System.out.println("Test Case 2: 1 -> 2 -> 3");
        System.out.println("Approach 1 (Stack LIFO)    Result: " + res2_1);
        System.out.println("Approach 2 (In-Place O(1)) Result: " + res2_2);
        System.out.println("Approach 3 (ArrayList)     Result: " + res2_3);
        System.out.println("Verification: " + (!res2_1 && !res2_2 && !res2_3 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 3 (From Video Board Explanation - Odd Palindrome) ---
        ListNode test3_1 = buildList(new int[]{1, 2, 3, 2, 1});
        ListNode test3_2 = buildList(new int[]{1, 2, 3, 2, 1});
        ListNode test3_3 = buildList(new int[]{1, 2, 3, 2, 1});

        boolean res3_1 = isPalindromeOptimal(test3_1);
        boolean res3_2 = isPalindromeInPlace(test3_2);
        boolean res3_3 = isPalindromeArrayList(test3_3);

        System.out.println("Test Case 3: 1 -> 2 -> 3 -> 2 -> 1");
        System.out.println("Approach 1 (Stack LIFO)    Result: " + res3_1);
        System.out.println("Approach 2 (In-Place O(1)) Result: " + res3_2);
        System.out.println("Approach 3 (ArrayList)     Result: " + res3_3);
        System.out.println("Verification: " + (res3_1 && res3_2 && res3_3 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}