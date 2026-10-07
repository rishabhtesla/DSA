package DSA.Coding.DSABasic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * Shared Definition for Singly-Linked List Node.
 */


/**
 * PROBLEM STATEMENT:
 * You are given the head of a linked list with n nodes [00:00:22].
 * For each node in the list, find the value of the next strictly greater node [00:01:05].
 * Return an array of integers answer where answer[i] is the value of the next greater node [00:00:57].
 * If no greater node exists, answer[i] = 0 [00:01:18, 00:01:59].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: head = [2, 1, 5] [00:00:52]
 * - Node 2: Next strictly greater is 5.
 * - Node 1: Next strictly greater is 5.
 * - Node 5: No greater element strictly after it -> 0.
 * - Result: [5, 5, 0] [00:01:01].
 * 
 * Example 2: head = [2, 7, 4, 3, 5] [00:01:23, 00:05:30]
 * - Node 2: Next greater is 7 [00:01:43].
 * - Node 7: No element after 7 is greater -> 0 [00:01:49].
 * - Node 4: Next greater after 4 is 5 [00:01:53].
 * - Node 3: Next greater after 3 is 5 [00:01:55].
 * - Node 5: No element after 5 -> 0 [00:01:59].
 * - Result: [7, 0, 5, 5, 0] [00:02:02].
 * 
 * Example 3: head = [2, 5, 0, 3, 4] (Simulated inside video explanation [00:20:45, 00:25:40])
 * - Reversed List: 4 -> 3 -> 0 -> 5 -> 2 [00:21:27]
 * - Output Array size = 5 [00:21:12]
 * - Process Reversed Nodes:
 *   - Push 4 (tail): ans[4] = 0, Stack = [4] [00:21:47]
 *   - Node 3: stack peek 4 > 3 -> ans[3] = 4, Stack = [4, 3] [00:22:32]
 *   - Node 0: stack peek 3 > 0 -> ans[2] = 3, Stack = [4, 3, 0] [00:23:28]
 *   - Node 5: pops 0, 3, 4 (all <= 5). Stack empty -> ans[1] = 0, Stack = [5] [00:24:48]
 *   - Node 2: stack peek 5 > 2 -> ans[0] = 5, Stack = [5, 2] [00:25:28]
 * - Result: [5, 0, 3, 4, 0] [00:25:46].
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - List Reversal with Monotonic Decreasing Stack):
 * • Edge Cases:
 *   - Empty list check: `if (head == null) return new int[0]`.
 * • Helper Methods:
 *   - `sizeLL(head)`: counts total node count `N` [00:10:56, 00:11:42].
 *   - `reverseList(head)`: flips linked list in-place [00:12:24, 00:13:42].
 * • Pointer & Variable Initialization:
 *   - `size = sizeLL(head)` [00:11:48].
 *   - `ans = new int[size]` [00:11:59].
 *   - `newHead = reverseList(head)` [00:14:00].
 *   - `stack = new Stack<Integer>()` stores candidate next greater values [00:14:25].
 *   - `stack.push(newHead.val)` seeds stack with reversed head (original tail) [00:14:59].
 *   - `current = newHead.next` [00:15:18].
 *   - `pointer = size - 2` indexes output array from right to left [00:15:39, 00:16:04].
 * • Condition Boundaries:
 *   - Outer loop condition: `while (pointer >= 0)` [00:16:13].
 *   - Stack eviction loop: `while (!stack.isEmpty() && stack.peek() <= element)` [00:16:59, 00:17:20].
 *   - Array assignment: `ans[pointer] = stack.isEmpty() ? 0 : stack.peek()` [00:17:48, 00:18:06].
 * • Operational Steps:
 *   1. Calculate size $N$, allocate `ans[N]` array [00:11:48, 00:11:59].
 *   2. Reverse linked list in-place using 3 pointers [00:12:24, 00:14:00].
 *   3. Push `newHead.val` onto stack [00:14:59].
 *   4. Set `pointer = size - 2` and `current = newHead.next` [00:15:18, 00:15:39].
 *   5. For each node, pop all stack elements `<= current.val` [00:16:59].
 *   6. Record `stack.peek()` into `ans[pointer]` (or `0` if stack empty) [00:17:48].
 *   7. Push `current.val` onto stack [00:18:20].
 *   8. Advance `current = current.next` and decrement `pointer--` [00:16:35, 00:18:28].
 *   9. Return `ans` array [00:18:36].
 * • Time Complexity: O(N) - Calculating size takes N steps, reversal takes N steps, and stack operations push/pop each element at most once.
 * • Space Complexity: O(N) auxiliary space - Stack stores at most N elements and output array requires N space.
 * • LOGIC BEHIND THIS APPROACH:
 *   By reversing the list, "next greater" looking rightward becomes "previous greater" looking backward [00:03:13, 00:07:08]. 
 *   The monotonic decreasing stack strips out smaller elements that can never serve as a "next greater" candidate for subsequent leftward nodes [00:04:30, 00:08:29].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 1: Reversal + Stack - head = [2, 1, 5]):
 * Step 1: Size = 3 -> ans = [0, 0, 0] [00:01:01]
 * Step 2: Reverse List -> 5 -> 1 -> 2
 * Step 3: Push 5 to stack -> Stack = [5]. current = Node(1), pointer = 1 (size-2).
 * 
 * Pass 1 (element = 1, pointer = 1):
 * - stack.peek() (5) > 1 -> loop skips popping.
 * - ans[1] = stack.peek() (5).
 * - push 1 -> Stack = [5, 1]. current = Node(2), pointer = 0.
 * 
 * Pass 2 (element = 2, pointer = 0):
 * - stack.peek() (1) <= 2 -> pop 1! Stack = [5].
 * - stack.peek() (5) > 2 -> stop popping.
 * - ans[0] = stack.peek() (5).
 * - push 2 -> Stack = [5, 2]. pointer = -1 (loop terminates).
 * 
 * Return ans -> [5, 5, 0] [00:01:01].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 2: Forward Pass Monotonic Index Stack Strategy - head = [2, 1, 5]):
 * Array Copy: values = [2, 1, 5], ans = [0, 0, 0]
 * Stack stores indices:
 * - i=0 (val 2): stack = [0]
 * - i=1 (val 1): 1 <= 2 -> stack = [0, 1]
 * - i=2 (val 5):
 *   - 5 > values[stack.peek()=1] (1) -> pop 1, ans[1] = 5
 *   - 5 > values[stack.peek()=0] (2) -> pop 0, ans[0] = 5
 *   - stack = [2]
 * Output = [5, 5, 0].
 */
public class NextGreaterNodeInLinkedList {

    // Helper method to count nodes in linked list [00:10:56]
    public static int sizeLL(ListNode head) {
        ListNode current = head;
        int count = 0;
        while (current != null) {
            count++;
            current = current.next;
        }
        return count; // Returns list length [00:11:42]
    }

    // Helper method to reverse a singly linked list in O(n) time [00:12:24]
    public static ListNode reverseList(ListNode head) {
        ListNode current = head;
        ListNode previous = null;

        while (current != null) {
            ListNode temp = current.next; // Save next pointer [00:13:10]
            current.next = previous;      // Reverse link direction [00:13:20]
            previous = current;           // Advance previous pointer [00:13:25]
            current = temp;               // Advance current pointer [00:13:28]
        }

        return previous; // New head of reversed list [00:13:42]
    }

    // APPROACH 1: List Reversal with Monotonic Decreasing Stack (Anchor Strategy)
    public static int[] nextLargerNodesOptimal(ListNode head) {
        if (head == null) return new int[0];

        // Step 1: Compute total length and allocate answer array [00:11:48, 00:11:59]
        int size = sizeLL(head);
        int[] ans = new int[size];

        // Step 2: Reverse linked list [00:14:00]
        ListNode newHead = reverseList(head);

        // Step 3: Instantiate Monotonic Decreasing Stack [00:14:25]
        Stack<Integer> stack = new Stack<>();

        // Seed stack with reversed head value (original tail node) [00:14:59]
        stack.push(newHead.val);

        ListNode current = newHead.next; // Iterator pointer [00:15:18]
        int pointer = size - 2;          // Output array index (right-to-left) [00:15:39, 00:16:04]

        // Step 4: Traverse reversed list from right to left [00:16:13]
        while (pointer >= 0) {
            int element = current.val; // Extract node value [00:16:25]
            current = current.next;    // Advance list iterator [00:16:35]

            // Pop elements from stack that are smaller than or equal to current element [00:16:59, 00:17:20]
            while (!stack.isEmpty() && stack.peek() <= element) {
                stack.pop(); // Evict non-greater candidate [00:17:25]
            }

            // Record next greater value or 0 if stack is empty [00:17:48, 00:18:06]
            if (stack.isEmpty()) {
                ans[pointer] = 0; // No greater element exists [00:17:58]
            } else {
                ans[pointer] = stack.peek(); // Top of stack is next greater node [00:18:10]
            }

            // Push current element onto stack as potential candidate for leftward nodes [00:18:20]
            stack.push(element);

            pointer--; // Decrement array index [00:18:28]
        }

        return ans; // Return completed next greater array [00:18:36]
    }

    // APPROACH 2: Forward Pass Monotonic Index Stack Strategy (Without Reversing List)
    // Converts linked list to an ArrayList first, then uses an Index Stack to resolve next greater elements in a single forward pass.
    public static int[] nextLargerNodesForward(ListNode head) {
        List<Integer> values = new ArrayList<>();
        ListNode curr = head;
        while (curr != null) {
            values.add(curr.val);
            curr = curr.next;
        }

        int[] ans = new int[values.size()];
        Stack<Integer> stack = new Stack<>(); // Stores indices of elements searching for next greater

        for (int i = 0; i < values.size(); i++) {
            while (!stack.isEmpty() && values.get(stack.peek()) < values.get(i)) {
                int poppedIndex = stack.pop();
                ans[poppedIndex] = values.get(i);
            }
            stack.push(i);
        }

        return ans;
    }

    // Helper method to build a linked list from an array
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

        // --- TEST CASE 1 (head = [2, 1, 5]) ---
        ListNode test1_1 = buildList(new int[]{2, 1, 5});
        ListNode test1_2 = buildList(new int[]{2, 1, 5});

        int[] res1_1 = nextLargerNodesOptimal(test1_1);
        int[] res1_2 = nextLargerNodesForward(test1_2);

        System.out.println("Test Case 1: head = [2, 1, 5]");
        System.out.println("Approach 1 (Reverse + Stack) Result: " + Arrays.toString(res1_1));
        System.out.println("Approach 2 (Forward Stack)   Result: " + Arrays.toString(res1_2));
        boolean check1 = Arrays.equals(res1_1, new int[]{5, 5, 0}) && Arrays.equals(res1_2, new int[]{5, 5, 0});
        System.out.println("Verification: " + (check1 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (head = [2, 7, 4, 3, 5]) ---
        ListNode test2_1 = buildList(new int[]{2, 7, 4, 3, 5});
        ListNode test2_2 = buildList(new int[]{2, 7, 4, 3, 5});

        int[] res2_1 = nextLargerNodesOptimal(test2_1);
        int[] res2_2 = nextLargerNodesForward(test2_2);

        System.out.println("Test Case 2: head = [2, 7, 4, 3, 5]");
        System.out.println("Approach 1 (Reverse + Stack) Result: " + Arrays.toString(res2_1));
        System.out.println("Approach 2 (Forward Stack)   Result: " + Arrays.toString(res2_2));
        boolean check2 = Arrays.equals(res2_1, new int[]{7, 0, 5, 5, 0}) && Arrays.equals(res2_2, new int[]{7, 0, 5, 5, 0});
        System.out.println("Verification: " + (check2 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 3 (From Video Explanation [2, 5, 0, 3, 4]) ---
        ListNode test3_1 = buildList(new int[]{2, 5, 0, 3, 4});
        ListNode test3_2 = buildList(new int[]{2, 5, 0, 3, 4});

        int[] res3_1 = nextLargerNodesOptimal(test3_1);
        int[] res3_2 = nextLargerNodesForward(test3_2);

        System.out.println("Test Case 3: head = [2, 5, 0, 3, 4]");
        System.out.println("Approach 1 (Reverse + Stack) Result: " + Arrays.toString(res3_1));
        System.out.println("Approach 2 (Forward Stack)   Result: " + Arrays.toString(res3_2));
        boolean check3 = Arrays.equals(res3_1, new int[]{5, 0, 3, 4, 0}) && Arrays.equals(res3_2, new int[]{5, 0, 3, 4, 0});
        System.out.println("Verification: " + (check3 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}