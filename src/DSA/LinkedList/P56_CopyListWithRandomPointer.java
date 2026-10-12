package DSA.LinkedList;

/**
 * ============================================================================
 * [56 / 65] - COPY LIST WITH RANDOM POINTER (LeetCode 138)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Construct a deep copy of a linked list where each node contains an additional
 * `random` pointer that could point to any node in the list or `null`.
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Hash Map Approach (O(n) Time | O(n) Space):
 *    - Use a `HashMap<Node, Node>` to map each original node to its cloned counterpart.
 *    - Requires two passes and uses O(n) auxiliary heap space.
 *
 * 2. Optimal O(1) Auxiliary Space Approach via Interweaving Nodes (O(n) Time | O(1) Space):
 *    - **The Core Insight:** We can establish a direct spatial relationship between original nodes
 *      and their clones without a hash table by interweaving them together.
 *    - **Step 1 (Interleave):** Clone each node and insert it immediately after the original node:
 *      `A -> A' -> B -> B' -> C -> C'`.
 *    - **Step 2 (Assign Random):** Set random pointers for the cloned nodes. Because every clone is right
 *      after its original, the clone's random pointer can be set in O(1) via:
 *      `curr.next.random = (curr.random != null) ? curr.random.next : null`.
 *    - **Step 3 (Unweave):** Restore the original list and separate out the cloned list pointers.
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(n) - Three linear passes through the linked list (interweave, assign random, unweave).
 * - Space: O(1) - Constant extra space as all pointer manipulations are done strictly in-place.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE:
 * - List: `7 -> 13 -> 11`, where `13.random = 7` and `11.random = 13`.
 * - Cloned Node 2's random value correctly evaluates to `7`.
 *
 * VISUAL DRY RUN:
 *   - Step 1: Interleaves to `7 -> 7' -> 13 -> 13' -> 11 -> 11'`.
 *   - Step 2: `7'` gets `null`, `13'` gets `7'`, `11'` gets `13'`.
 *   - Step 3: Unweaves pointers to return independent clone list `7' -> 13' -> 11'` while restoring original list.
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why does the interweaving technique eliminate the need for a Hash Map?
 *    (It embeds the mapping lookup directly into the linked list structure using the adjacent `.next` pointer,
 *    reducing space complexity from O(n) to O(1).)
 * 2. What edge cases are handled automatically?
 *    (A `null` head check immediately short-circuits to return `null`, and nodes with `null` random pointers
 *    are safely skipped by the ternary check during Step 2.)
 */
public class P56_CopyListWithRandomPointer {

    static class Node {
        int val;
        Node next;
        Node random;
        Node(int val) { this.val = val; }
    }

    public static Node copyRandomList(Node head) {
        if (head == null) return null;

        // Step 1: Duplicate nodes and interleave them: A -> A' -> B -> B'
        Node curr = head;
        while (curr != null) {
            Node copy = new Node(curr.val);
            copy.next = curr.next;
            curr.next = copy;
            curr = copy.next;
        }

        // Step 2: Assign random pointers for cloned nodes
        curr = head;
        while (curr != null) {
            if (curr.random != null) {
                curr.next.random = curr.random.next;
            }
            curr = curr.next.next;
        }

        // Step 3: Unweave the combined list back into original and copied lists
        curr = head;
        Node copyHead = head.next;
        Node copyCurr = copyHead;

        while (curr != null) {
            curr.next = curr.next.next;
            curr = curr.next;

            if (copyCurr.next != null) {
                copyCurr.next = copyCurr.next.next;
                copyCurr = copyCurr.next;
            }
        }

        return copyHead;
    }

    public static void main(String[] args) {
        Node n1 = new Node(7);
        Node n2 = new Node(13);
        Node n3 = new Node(11);
        n1.next = n2; n2.next = n3;
        n2.random = n1;
        n3.random = n2;

        Node cloned = copyRandomList(n1);
        System.out.println("P56 Cloned Node 1 Val: " + cloned.val);
        System.out.println("P56 Cloned Node 2 Random Val: " + cloned.next.random.val); // Expected: 7
    }
}