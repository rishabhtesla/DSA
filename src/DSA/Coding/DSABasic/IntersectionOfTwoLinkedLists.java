package DSA.Coding.DSABasic;

import java.util.HashSet;
import java.util.Set;

/**
 * Shared Definition for Singly-Linked List Node.
 */



/**
 * PROBLEM STATEMENT:
 * Given the heads of two singly linked lists headA and headB, return the node at which 
 * the two lists intersect [00:00:34, 00:00:44]. If no intersection exists, return null [00:00:44, 00:01:34].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: headA = [4, 1, 8, 4, 5], headB = [5, 6, 1, 8, 4, 5], intersecting at node with val 8.
 * - Length(A) = 5, Length(B) = 6 [00:03:29, 00:03:35].
 * - Difference = 5 - 6 = -1 (List B is longer by 1 node) [00:03:49, 00:04:36].
 * - Advance pointerB 1 step forward (skips 5, lands on node 6) [00:03:57, 00:13:46].
 * - Traverse both together:
 *   - pA at 4, pB at 6 (not equal) -> advance both [00:14:08]
 *   - pA at 1, pB at 1 (not equal - different node references) -> advance both [00:14:14]
 *   - pA at 8, pB at 8 (SAME reference match c1!) -> return reference [00:03:06, 00:14:19].
 * - Result: Node(8)
 * 
 * Example 2: headA = [1, 9, 1, 2, 4], headB = [3, 2, 4], intersecting at node with val 2.
 * - Length(A) = 5, Length(B) = 3 -> Length(A) > Length(B) by 2.
 * - Advance pointerA by 2 steps.
 * - Synchronized step-by-step match occurs at Node(2).
 * - Result: Node(2)
 * 
 * Example 3: headA = [2, 6, 4], headB = [1, 5], no intersection.
 * - Length(A) = 3, Length(B) = 2.
 * - Advance pointerA by 1 step.
 * - Step together until both hit null simultaneously.
 * - Result: null
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Length Alignment Pointer Traversal):
 * • Edge Cases:
 *   - If `headA == null || headB == null`, return `null` immediately [00:07:33, 00:07:53].
 * • Helper Function:
 *   - `sizeLL(ListNode head)`: Counts total nodes in a list using iterative while loop [00:05:04, 00:05:51].
 * • Step-by-Step Logic:
 *   1. Calculate list lengths: `size1 = sizeLL(headA)`, `size2 = sizeLL(headB)` [00:08:06, 00:08:23].
 *   2. Compute signed difference `diff = size1 - size2` [00:08:36].
 *   3. If `diff > 0` (List A is longer), advance `pointer1` by `diff` steps [00:09:20, 00:10:06].
 *   4. If `diff < 0` (List B is longer), advance `pointer2` by `|diff|` steps (`diff++` until 0) [00:10:29, 00:10:42].
 *   5. Traverse both pointers simultaneously while `pointer1 != pointer2` [00:11:07].
 *   6. Return `pointer1` (returns intersection node or `null` if both reach tail) [00:11:29, 00:11:43].
 * • Time Complexity: O(N + M) - Two linear passes to measure list sizes, plus one linear pass to locate intersection.
 * • Space Complexity: O(1) auxiliary space - Modifies no data structures, only uses scalar pointer coordinates [00:00:58].
 * • LOGIC BEHIND THIS APPROACH:
 *   After skipping the initial prefix length difference, both pointers are equidistant from the tail of their respective lists [00:03:57, 00:14:00]. 
 *   Stepping together ensures that if an intersection node exists, both pointers land on the same memory reference simultaneously [00:03:06, 00:14:19].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 1: Length Alignment - sizeA = 5, sizeB = 6, diff = -1):
 * Initial: pA = headA (4), pB = headB (5), diff = -1 [00:13:25]
 * Aligning: diff < 0 -> move pB to pB.next (6), diff++ (0) [00:13:46]
 * Alignment Complete: Both pointers have 5 remaining nodes to tail [00:14:00].
 * 
 * Synchronized Pass:
 * - pA(4) != pB(6) -> pA = pA.next (1), pB = pB.next (1) [00:14:08]
 * - pA(1) != pB(1) [different memory instances] -> pA = pA.next (8), pB = pB.next (8) [00:14:14]
 * - pA(8) == pB(8) [identical memory instance c1] -> Loop terminates! [00:14:19]
 * Return pA -> Node(8).
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 2: Two-Pointer Two-Pass Swap Strategy):
 * Pointer A starts at headA, Pointer B starts at headB.
 * - When pA hits null, redirect pA = headB.
 * - When pB hits null, redirect pB = headA.
 * Both pointers traverse total distance (LengthA + LengthB) and meet at intersection node or null in pass 2.
 */
public class IntersectionOfTwoLinkedLists {

    // Helper function to calculate total node length of a linked list [00:05:04]
    public static int sizeLL(ListNode head) {
        int count = 0;              // Node counter [00:05:25]
        ListNode pointer = head;    // Iteration pointer [00:05:32]

        while (pointer != null) {
            count++;                // Increment count [00:05:43]
            pointer = pointer.next; // Advance to next node [00:05:48]
        }

        return count; // Return total size [00:05:51]
    }

    // APPROACH 1: Length Alignment Pointer Traversal (Anchor Strategy)
    public static ListNode getIntersectionNodeOptimal(ListNode headA, ListNode headB) {
        // Base Case: return null if either list is empty [00:07:33, 00:07:53]
        if (headA == null || headB == null) {
            return null;
        }

        // Calculate sizes of both lists [00:08:06, 00:08:23]
        int size1 = sizeLL(headA);
        int size2 = sizeLL(headB);

        int diff = size1 - size2; // Calculate size difference offset [00:08:36]

        ListNode pointer1 = headA; // Pointer for List A [00:09:25]
        ListNode pointer2 = headB; // Pointer for List B [00:09:38]

        // Case 1: List A is longer -> advance pointer1 forward by diff steps [00:08:57, 00:10:06]
        if (diff > 0) {
            while (diff > 0) {
                pointer1 = pointer1.next;
                diff--;
            }
        } 
        // Case 2: List B is longer -> advance pointer2 forward by |diff| steps [00:09:45, 00:10:29]
        else {
            while (diff < 0) {
                pointer2 = pointer2.next;
                diff++; // Bring negative offset towards zero [00:10:42]
            }
        }

        // Both pointers are now equidistant from the tail; step forward until they meet [00:11:07]
        while (pointer1 != pointer2) {
            pointer1 = pointer1.next; // Advance pointer 1 [00:11:12]
            pointer2 = pointer2.next; // Advance pointer 2 [00:11:24]
        }

        return pointer1; // Return intersecting node or null [00:11:29]
    }

    // APPROACH 2: Two-Pointer Two-Pass Redirect Strategy (O(1) Auxiliary Space Alternative)
    // Elegant approach that swaps list heads upon reaching null.
    // Each pointer traverses (LengthA + LengthB) steps, automatically neutralizing length offset.
    public static ListNode getIntersectionNodeTwoPass(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) return null;

        ListNode pA = headA;
        ListNode pB = headB;

        while (pA != pB) {
            pA = (pA == null) ? headB : pA.next;
            pB = (pB == null) ? headA : pB.next;
        }

        return pA; // Meets at intersection node or null
    }

    // APPROACH 3: HashSet Memory Reference Lookup Strategy (O(N) Auxiliary Space)
    // Stores all node references of List A in a HashSet, then scans List B for the first matching reference.
    public static ListNode getIntersectionNodeHashSet(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) return null;

        Set<ListNode> visitedNodes = new HashSet<>();
        ListNode currA = headA;

        while (currA != null) {
            visitedNodes.add(currA);
            currA = currA.next;
        }

        ListNode currB = headB;
        while (currB != null) {
            if (visitedNodes.contains(currB)) {
                return currB; // Found matching memory reference
            }
            currB = currB.next;
        }

        return null;
    }

    // Comprehensive Verification Test Suite
    public static void main(String[] args) {
        System.out.println("================ RUNNING DSA TEST SUITE ================\n");

        // --- TEST CASE 1 (Intersecting Lists) ---
        // Common tail: 8 -> 4 -> 5
        ListNode common = new ListNode(8, new ListNode(4, new ListNode(5)));
        
        // List A: 4 -> 1 -> [common]
        ListNode headA1 = new ListNode(4, new ListNode(1, common));
        
        // List B: 5 -> 6 -> 1 -> [common]
        ListNode headB1 = new ListNode(5, new ListNode(6, new ListNode(1, common)));

        ListNode res1_1 = getIntersectionNodeOptimal(headA1, headB1);
        ListNode res1_2 = getIntersectionNodeTwoPass(headA1, headB1);
        ListNode res1_3 = getIntersectionNodeHashSet(headA1, headB1);

        System.out.println("Test Case 1: Intersecting Lists at node val 8");
        System.out.println("Approach 1 (Length Align) Result Val: " + (res1_1 != null ? res1_1.val : "null"));
        System.out.println("Approach 2 (Two-Pass Redirect) Result: " + (res1_2 != null ? res1_2.val : "null"));
        System.out.println("Approach 3 (HashSet)        Result Val: " + (res1_3 != null ? res1_3.val : "null"));
        boolean check1 = (res1_1 == common) && (res1_2 == common) && (res1_3 == common);
        System.out.println("Verification: " + (check1 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (Non-Intersecting Lists) ---
        ListNode headA2 = new ListNode(2, new ListNode(6, new ListNode(4)));
        ListNode headB2 = new ListNode(1, new ListNode(5));

        ListNode res2_1 = getIntersectionNodeOptimal(headA2, headB2);
        ListNode res2_2 = getIntersectionNodeTwoPass(headA2, headB2);
        ListNode res2_3 = getIntersectionNodeHashSet(headA2, headB2);

        System.out.println("Test Case 2: Non-Intersecting Lists");
        System.out.println("Approach 1 (Length Align) Result: " + (res2_1 != null ? res2_1.val : "null"));
        System.out.println("Approach 2 (Two-Pass Redirect) Result: " + (res2_2 != null ? res2_2.val : "null"));
        System.out.println("Approach 3 (HashSet)        Result: " + (res2_3 != null ? res2_3.val : "null"));
        boolean check2 = (res2_1 == null) && (res2_2 == null) && (res2_3 == null);
        System.out.println("Verification: " + (check2 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}